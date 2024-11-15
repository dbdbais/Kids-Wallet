plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    //TODO: 1. append fcm plugin(app)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.ssafy.kidswallet"
    compileSdk = 34

    signingConfigs {
        //create("release") {
        //    storeFile = file("C:\\Users\\SSAFY\\Desktop\\jayul\\S11P31E201\\Frontend\\client\\KidsWallet\\my-release-key.jks")
        //    storePassword = "kidswallet"
        //    keyAlias = "my-key-alias"
        //    keyPassword = "kidswallet"
        //}

        // 배포서버
        create("release") {
            storeFile = file("/kidswallet-release-key.jks")
            storePassword = "kidswallet"
            keyAlias = "kidswallet-key-alias"
            keyPassword = "kidswallet"
        }
    }

    defaultConfig {
        applicationId = "com.ssafy.kidswallet"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        debug {
            buildConfigField("String", "BASE_URL", "\"https://k11e201.p.ssafy.io/api/v1/\"")
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "BASE_URL", "\"https://k11e201.p.ssafy.io/api/v1/\"")
            signingConfig = signingConfigs.getByName("release")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        buildConfig = true
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation("androidx.privacysandbox.tools:tools-core:1.0.0-alpha10")
    implementation("com.google.firebase:firebase-messaging-ktx:24.0.3")
    val nav_version = "2.7.4"

    implementation("androidx.navigation:navigation-compose:$nav_version")

    implementation("androidx.core:core-ktx:1.13.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.7")
    implementation("androidx.activity:activity-compose:1.9.3")
    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")

    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("io.coil-kt:coil-compose:2.4.0")
    implementation("androidx.navigation:navigation-compose:2.7.4")
    implementation ("androidx.datastore:datastore-preferences:1.0.0")
    implementation ("com.google.code.gson:gson:2.8.8")
    //runBlocking
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.0")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    //TODO: 2. append fcm dependency(app)

    // Import the Firebase BoM
    implementation(platform("com.google.firebase:firebase-bom:33.5.1"))
    // When using the BoM, don't specify versions in Firebase dependencies
    implementation("com.google.firebase:firebase-analytics")
    implementation ("androidx.work:work-runtime-ktx:2.8.0")
    //runBlocking
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.0")
}
