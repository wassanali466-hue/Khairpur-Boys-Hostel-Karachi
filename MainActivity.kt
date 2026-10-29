package com.khairpurboyshostel.karachi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.khairpurboyshostel.karachi.security.SecurityManager

class MainActivity : ComponentActivity() {

    private lateinit var securityManager: SecurityManager
    private val isAppLocked = mutableStateOf(true)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val app = application as KhairpurHostelApp
        securityManager = app.securityManager

        setContent {
            MaterialTheme(
                colorScheme = darkColorScheme(
                    primary = Color(0xFF2563EB),
                    background = Color(0xFF020617),
                    surface = Color(0xFF0F172A)
                )
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    if (isAppLocked.value) {
                        ComposeLockScreen(
                            securityManager = securityManager,
                            onUnlocked = { isAppLocked.value = false }
                        )
                    } else {
                        ComposeDashboard(
                            onLockApp = { isAppLocked.value = true }
                        )
                    }
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        // Secure App Lock: Lock immediately when app goes to background
        isAppLocked.value = true
    }
}

@Composable
fun ComposeLockScreen(
    securityManager: SecurityManager,
    onUnlocked: () -> Unit
) {
    var pin by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Khairpur Boys Hostel Karachi",
            style = MaterialTheme.typography.headlineSmall,
            color = Color.White
        )
        Text(
            text = "Superco Road, Sachal Goth, Karachi",
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Enter 6-Digit PIN",
            style = MaterialTheme.typography.titleMedium,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = pin,
            onValueChange = {
                if (it.length <= 6) {
                    pin = it
                    if (it.length == 6) {
                        if (!securityManager.isPinConfigured()) {
                            securityManager.setupPin(it)
                            onUnlocked()
                        } else if (securityManager.verifyPin(it)) {
                            onUnlocked()
                        } else {
                            error = "Incorrect PIN. Try again."
                            pin = ""
                        }
                    }
                }
            },
            label = { Text("6-Digit PIN") },
            isError = error.isNotEmpty(),
            singleLine = true
        )

        if (error.isNotEmpty()) {
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "Hostel Manager: Nadeem Ali (03022631429)",
            style = MaterialTheme.typography.labelSmall,
            color = Color.Gray
        )
    }
}

@Composable
fun ComposeDashboard(onLockApp: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Khairpur Boys Hostel",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White
                )
                Text(
                    text = "Manager: Nadeem Ali (03022631429)",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF10B981)
                )
            }
            Button(
                onClick = onLockApp,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF991B1B))
            ) {
                Text("Lock")
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Active Residents & Room Management", color = Color.White)
                Text("Default monthly fee: PKR 14,000", color = Color.Gray)
                Text("Facilities: 2 Meals + 2 Tea servings daily", color = Color.Gray)
            }
        }
    }
}
