package com.khairpurboyshostel.karachi.security

import android.content.Context
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import java.security.SecureRandom
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec

class SecurityManager(private val context: Context) {

    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val securePrefs = EncryptedSharedPreferences.create(
        context,
        "kbh_secure_store",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    fun isPinConfigured(): Boolean {
        return securePrefs.contains("pin_hash")
    }

    fun setupPin(pin: String) {
        val salt = ByteArray(16)
        SecureRandom().nextBytes(salt)
        val hash = hashWithPBKDF2(pin, salt)

        securePrefs.edit()
            .putString("pin_hash", hash)
            .putString("pin_salt", salt.toHexString())
            .putInt("failed_attempts", 0)
            .putLong("lockout_until", 0L)
            .apply()
    }

    fun verifyPin(pin: String): Boolean {
        val now = System.currentTimeMillis()
        val lockoutUntil = securePrefs.getLong("lockout_until", 0L)
        if (now < lockoutUntil) {
            return false // Lockout active
        }

        val storedHash = securePrefs.getString("pin_hash", null) ?: return false
        val storedSaltHex = securePrefs.getString("pin_salt", null) ?: return false
        val salt = storedSaltHex.hexStringToByteArray()

        val inputHash = hashWithPBKDF2(pin, salt)
        if (inputHash == storedHash) {
            // Success: Reset failed attempts
            securePrefs.edit()
                .putInt("failed_attempts", 0)
                .putLong("lockout_until", 0L)
                .apply()
            return true
        } else {
            // Failure: Increment failed attempts
            val failed = securePrefs.getInt("failed_attempts", 0) + 1
            var lockout = 0L
            if (failed >= 5) {
                // Exponential backoff: 30s, 60s, 120s...
                val seconds = 30 * (1 shl (failed - 5).coerceAtMost(3))
                lockout = now + (seconds * 1000L)
            }
            securePrefs.edit()
                .putInt("failed_attempts", failed)
                .putLong("lockout_until", lockout)
                .apply()
            return false
        }
    }

    private fun hashWithPBKDF2(pin: String, salt: ByteArray): String {
        val spec = PBEKeySpec(pin.toCharArray(), salt, 100000, 256)
        val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
        return factory.generateSecret(spec).encoded.toHexString()
    }

    private fun ByteArray.toHexString(): String = joinToString("") { "%02x".format(it) }
    private fun String.hexStringToByteArray(): ByteArray {
        val len = length
        val data = ByteArray(len / 2)
        var i = 0
        while (i < len) {
            data[i / 2] = ((Character.digit(this[i], 16) shl 4) + Character.digit(this[i + 1], 16)).toByte()
            i += 2
        }
        return data
    }
}
