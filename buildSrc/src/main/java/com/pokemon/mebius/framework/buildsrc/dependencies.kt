package com.pokemon.mebius.framework.buildsrc

object Repos {
    const val maven_repo = "http://localhost:8081/repository/maven-releases/"
}

object Dependencies {
    const val mebius_publish_notify_plugin = "com.pokemon.hikari:upload-plugin:0.0.5"
    const val okhttp3 = "com.squareup.okhttp3:okhttp:${Versions.okhttp}"
    const val okhttp3_logging = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp}"
    const val retrofit_adapter_rx = "com.squareup.retrofit2:adapter-rxjava2:2.9.0"
    const val rx_android = "io.reactivex.rxjava2:rxandroid:2.1.1"
    const val retrofit = "com.squareup.retrofit2:retrofit:2.9.0"
    const val mebius_log = "com.github.same4869:MebiusLog:0.0.1"
    const val mebius_commlib = "com.github.same4869:MebiusCommlib:0.0.1"
}

object SoraRestful {
    const val debug_mode = false
    const val core = ""
}

object Versions {
    const val compileSdkVersion = 30
    const val minSdkVersion = 23
    const val targetSdkVersion = 30
    const val kotlin = "1.5.21"
    const val agp = "7.0.3"
    const val okhttp = "4.8.0"
}
