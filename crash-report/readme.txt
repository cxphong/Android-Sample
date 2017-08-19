1. Create project on Firebase console

2. Add the dependency for Crash Reporting to your app-level build.gradle file:
    compile 'com.google.firebase:firebase-crash:10.2.0'

    ...
    apply plugin: 'com.google.gms.google-services'

3. Add classpath 'com.google.gms:google-services:3.0.0'

buildscript {
    repositories {
        jcenter()
    }
    dependencies {
        classpath 'com.android.tools.build:gradle:2.3.0'
        classpath 'com.google.gms:google-services:3.0.0'

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

4. Put google-services.json to project

5. push crash reposrt:
	FirebaseCrash.report(new Exception("My first Android non-fatal error"));
