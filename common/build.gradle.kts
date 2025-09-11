plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
}

android {
    namespace = "com.bimabk.common"
    compileSdk = 35

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    api(libs.androidx.core.ktx)
    api(libs.androidx.appcompat)
    api(libs.material)
    api(libs.gson)
    api(libs.retrofit)
    api(libs.retrofit.gson)
    api(libs.retrofit.scalars)
    api(libs.okhttp)
    api(libs.okhttp.logging)
    api(libs.lifecycle.view.model)
    api(platform(libs.koin.bom))
    api(libs.koin.android)
    api(libs.room.runtime)
    api(libs.room.ktx)
    //noinspection KaptUsageInsteadOfKsp
    kapt(libs.room.compiler)
    api(libs.room.paging)
    api(libs.paging.runtime.ktx)
    api(libs.androidx.swiperefreshlayout)
    api(libs.kotlinx.serialization.json)
}