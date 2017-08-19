# Firebase
1. https://console.firebase.google.com/?pli=1
2. Create project

3. Add into build.gradle 

buildscript {
    // ...
    dependencies {
        // ...
        classpath 'com.google.gms:google-services:3.0.0'
    }
}

4. Add into build.gradle 

apply plugin: 'com.android.application'

android {
  // ...
}

dependencies {
  // ...
  compile 'com.google.firebase:firebase-core:10.2.0'
  
  // Getting a "Could not find" error? Make sure you have
  // the latest Google Repository in the Android SDK manager
}

// ADD THIS AT THE BOTTOM
apply plugin: 'com.google.gms.google-services'

5. Copy google-services.json to app/
6. Update sdk, android support repository, google play services, google repost
7. Edit manifest, add 2 file services, manifest permission
