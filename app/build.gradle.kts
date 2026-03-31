plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.kotlin.parcelize)
}

android {
    namespace = "com.kmpstudios.universalAppJc"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.kmpstudios.universalAppJc"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "com.kmpstudios.universalAppJc.HiltTestRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "BASE_URL", "\"https://universal-app.com/api/v1/\"")
        }
        debug {
            buildConfigField("String", "BASE_URL", "\"http://192.168.1.7:3000/api/v1/\"")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.paging)

    //Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    testImplementation(libs.io.mockk)
    testImplementation(libs.kotlinx.coroutines.test)
    androidTestImplementation(libs.io.mockk.android)
    androidTestImplementation(libs.dagger.hilt.android.testing)
    kspAndroidTest(libs.hilt.compiler)
    testImplementation(libs.androidx.paging.testing)
    androidTestImplementation(libs.androidx.paging.testing)
    testImplementation(libs.okhttp.mockwebserver)
    androidTestImplementation(libs.okhttp.mockwebserver)
    testImplementation(libs.androidx.room.testing)

    //Lifecycle
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)
    ksp(libs.androidx.lifecycle.compiler)

    //Navigation3
    implementation(libs.androidx.navigation3.ui)
    implementation(libs.androidx.navigation3.runtime)
    implementation(libs.androidx.lifecycle.viewmodel.navigation3)
    implementation(libs.androidx.material3.adaptive.navigation3)

    //Kotlinx Serialization
    implementation(libs.kotlinx.serialization.core)
    implementation(libs.kotlinx.serialization.json)

    //Dagger Hilt
    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation)
    implementation(libs.hilt.work)
    ksp(libs.hilt.compiler)
    ksp(libs.hilt.android.compiler)
    ksp(libs.androidx.hilt.compiler)

    //Material icons
    implementation(libs.material.icons)

    //Networking and Retrofit
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.converter)
    implementation(libs.okhttp.logging)

    //Coroutines
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    //Coil
    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)

    //Paging
    implementation(libs.androidx.paging.common)
    implementation(libs.androidx.paging.runtime.ktx)

    //Media
    implementation(libs.androidx.media3.exoplayer)
    implementation(libs.androidx.media3.ui)

    //Recycler view
    implementation(libs.androidx.recyclerview)

    //Room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.paging)
    ksp(libs.androidx.room.compiler)

    //Work manager
    implementation(libs.work.runtime.ktx)

    //Location
    implementation(libs.play.services.location)

    //Security
    implementation(libs.security.crypto)

    //Splash
    implementation(libs.core.splashcreen)
}