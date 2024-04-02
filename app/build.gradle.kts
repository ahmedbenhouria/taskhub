plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id ("dagger.hilt.android.plugin")
    id ("kotlin-parcelize")
}

android {
    namespace = "com.task.hub"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.task.hub"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation ("androidx.compose.runtime:runtime:1.6.4")
    implementation ("androidx.compose.runtime:runtime-livedata:1.6.4")
    implementation ("androidx.compose.runtime:runtime-rxjava2:1.6.4")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation(platform("androidx.compose:compose-bom:2024.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3:1.2.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2024.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    // Compose Destinations
    implementation ("io.github.raamcosta.compose-destinations:core:1.10.2")
    ksp ("io.github.raamcosta.compose-destinations:ksp:1.10.2")

    // Compose Lifecycles
    val lifecycleVersion = "2.7.0"
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycleVersion")

    // Feather Icons Pack
    implementation("br.com.devsrsouza.compose.icons:feather:1.1.0")

    // System UI controller
    implementation ("com.google.accompanist:accompanist-systemuicontroller:0.32.0")

    // Compose Calendar library
    implementation ("com.kizitonwose.calendar:compose:2.4.0")

    // Compose Date Picker Dialog Library
    implementation ("com.maxkeppeler.sheets-compose-dialogs:core:1.2.0")
    implementation ("com.maxkeppeler.sheets-compose-dialogs:calendar:1.2.0")

    // Compose Number Selector Dialog Library
    implementation ("com.github.MFlisar.ComposeDialogs:core:1.0.4")
    implementation ("com.github.MFlisar.ComposeDialogs:dialog-number:1.0.4")

    // Custom Toast Library
    implementation ("com.github.GrenderG:Toasty:1.5.2")

    // Dagger Hilt
    val daggerHiltVersion = "2.48.1"
    implementation ("com.google.dagger:hilt-android:$daggerHiltVersion")
    ksp ("com.google.dagger:hilt-android-compiler:$daggerHiltVersion")
    implementation ("androidx.hilt:hilt-navigation-compose:1.1.0")

    // Room components
    val roomVersion = "2.6.0"
    implementation ("androidx.room:room-runtime:$roomVersion")
    ksp ("androidx.room:room-compiler:$roomVersion")
    implementation ("androidx.room:room-ktx:$roomVersion")
    androidTestImplementation ("androidx.room:room-testing:$roomVersion")

    // GSON
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.google.code.gson:gson:2.10.1")

//    implementation ("com.github.SimformSolutionsPvtLtd:SSJetpackComposeSwipeableView:1.0.1")

}