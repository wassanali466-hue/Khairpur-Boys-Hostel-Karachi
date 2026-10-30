# Khairpur Boys Hostel Karachi — Native Android Application

Official hostel management app for Khairpur Boys Hostel Karachi.
- Address: Superco Road, Sachal Goth, Karachi, Sindh, Pakistan
- Manager: Nadeem Ali (03022631429)
- Architecture: Kotlin, Jetpack Compose, Material 3, Room SQLite Database, Android Keystore

## How to Build the APK in Android Studio:
1. Open Android Studio (Ladybug / Koala or newer).
2. Choose "Open" and select this folder (KhairpurBoysHostelKarachi).
3. Wait for Gradle sync to complete.
4. Click on the top menu: **Build -> Build Bundle(s) / APK(s) -> Build APK(s)**.
5. Once built, click "locate" in the notification popup to get your **app-debug.apk**.
6. Transfer this .apk to your Android phone (via WhatsApp, cable, or Google Drive) and tap to install!

## How to Build via Command Line (Terminal):
```bash
chmod +x gradlew
./gradlew assembleDebug
```
The APK will be generated at: `app/build/outputs/apk/debug/app-debug.apk`.
