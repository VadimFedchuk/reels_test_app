import java.util.Properties
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.instagramtestapp"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.example.instagramtestapp"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        val storytellerApiKey = providers.gradleProperty("STORYTELLER_API_KEY")
            .orElse(providers.localProperties("STORYTELLER_API_KEY"))
            .get()

        val storytellerBaseUrl = providers.gradleProperty("STORYTELLER_BASE_URL")
            .orElse(providers.localProperties("STORYTELLER_BASE_URL"))
            .get()

        buildConfigField("String", "STORYTELLER_API_KEY", "\"$storytellerApiKey\"")
        buildConfigField("String", "STORYTELLER_BASE_URL", "\"$storytellerBaseUrl\"")
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
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.moshi)

    implementation(libs.moshi)
    implementation(libs.moshi.kotlin)
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging)

    implementation(libs.koin.android)
    implementation(libs.koin.androidx.compose)
    implementation(libs.koin.androidx.navigation)

    implementation(libs.media3.exoplayer)
    implementation(libs.media3.ui)
    implementation(libs.media3.ui.compose)
    implementation(libs.media3.datasource)
}

fun ProviderFactory.localProperties(key: String) =
    provider {
        val props = Properties()
        val file = rootProject.file("local.properties")
        if (file.exists()) {
            file.inputStream().use { props.load(it) }
        }
        props.getProperty(key) ?: ""
    }