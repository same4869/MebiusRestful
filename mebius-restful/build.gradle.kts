import com.pokemon.mebius.framework.buildsrc.Versions

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
//    id("com.pokemon.hikari.upload.plugin")
}

android {
    namespace = "com.pokemon.mebius.restful"
    compileSdk = Versions.compileSdkVersion

    defaultConfig {
        minSdk = Versions.minSdkVersion
        targetSdk = Versions.targetSdkVersion

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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation(com.pokemon.mebius.framework.buildsrc.Dependencies.okhttp3)
    implementation(com.pokemon.mebius.framework.buildsrc.Dependencies.okhttp3_logging)
    implementation(com.pokemon.mebius.framework.buildsrc.Dependencies.retrofit_adapter_rx)
    implementation(com.pokemon.mebius.framework.buildsrc.Dependencies.rx_android)
    api(com.pokemon.mebius.framework.buildsrc.Dependencies.retrofit)
//    implementation(com.pokemon.mebius.framework.buildsrc.Dependencies.sora_log)
//    implementation(com.pokemon.mebius.framework.buildsrc.Dependencies.sora_commlib)
    api("com.github.same4869:MebiusCommlib:0.0.1")
    api("com.github.same4869:MebiusLog:0.0.2")
}


