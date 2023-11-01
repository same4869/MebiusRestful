import com.pokemon.mebius.framework.buildsrc.Versions

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
//    id("com.pokemon.hikari.upload.plugin")
    id("maven-publish")
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    //发布jitpack用的，私有仓库用自己的那个插件
    afterEvaluate {
        publishing {
            publications {
                create<MavenPublication>("release") {
                    from(components["release"])
                    groupId = "com.pokemon.mebius"
                    artifactId = "restful"
                    version = "0.0.1"
                }
            }
        }
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
    api("com.github.same4869:MebiusCommlib:0.0.2")
    api("com.github.same4869:MebiusLog:0.0.4")
}


