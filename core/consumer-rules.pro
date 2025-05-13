-keep,includedescriptorclasses class net.sqlcipher.** { *; }
-keep,includedescriptorclasses interface net.sqlcipher.** { *; }

##---------------Begin: proguard configuration for Gson ----------
# Gson uses generic type information stored in a class file when working with fields. Proguard
# removes such information by default, so configure it to keep all of it.
-keepattributes Signature

# For using GSON @Expose annotation
-keepattributes *Annotation*

# Gson specific classes
-dontwarn sun.misc.**
#-keep class com.google.gson.stream.** { *; }

# Application classes that will be serialized/deserialized over Gson
-keep class com.google.gson.examples.android.model.** { <fields>; }

# Prevent proguard from stripping interface information from TypeAdapter, TypeAdapterFactory,
# JsonSerializer, JsonDeserializer instances (so they can be used in @JsonAdapter)
-keep class * extends com.google.gson.TypeAdapter
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer

# Prevent R8 from leaving Data object members always null
-keepclassmembers,allowobfuscation class * {
@com.google.gson.annotations.SerializedName <fields>;
}


##---------------Begin: proguard configuration for Retrofit ----------
# Retrofit does reflection on generic parameters. InnerClasses is required to use Signature and
# EnclosingMethod is required to use InnerClasses.
-keepattributes Signature, InnerClasses, EnclosingMethod

# Retrofit does reflection on method and parameter annotations.
-keepattributes RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations

# Retain service method parameters when optimizing.
-keepclassmembers,allowshrinking,allowobfuscation interface * {
@retrofit2.http.* <methods>;
}

# Ignore annotation used for build tooling.
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement

# Ignore JSR 305 annotations for embedding nullability information.
-dontwarn javax.annotation.**

# Guarded by a NoClassDefFoundError try/catch and only used when on the classpath.
-dontwarn kotlin.Unit

# Top-level functions that can only be used by Kotlin.
-dontwarn retrofit2.KotlinExtensions
-dontwarn retrofit2.KotlinExtensions$*

# With R8 full mode, it sees no subtypes of Retrofit interfaces since they are created with a Proxy
# and replaces all potential values with null. Explicitly keeping the interfaces prevents this.
-if interface * { @retrofit2.http.* <methods>; }
-keep,allowobfuscation interface <1>

-dontwarn kotlinx.**


##---------------Begin: proguard configuration for Glide ----------
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep class * extends com.bumptech.glide.module.AppGlideModule {
<init>(...);
}
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
**[] $VALUES;
public *;
}
-keep class com.bumptech.glide.load.data.ParcelFileDescriptorRewinder$InternalRewinder {
*** rewind();
}

# Uncomment for DexGuard only
#-keep resourcexmlelements manifest/application/meta-data@value=GlideModule


##---------------Begin: proguard configuration for RxJava ----------
# Uncomment if you use RxJava
-dontwarn java.util.concurrent.Flow*

# Keep data classes used by Gson
-keep class com.example.module.core.data.source.remote.response.** { *; }

# Keep Gson serialized fields
-keepclassmembers class * {
    @com.google.gson.annotations.SerializedName <fields>;
}

# Keep annotations
-keepattributes *Annotation*

# Keep generic type info
-keepattributes Signature

-keep class com.example.module.core.di.** { *; }
-keepclassmembers class com.example.module.core.di.** { *; }

-keep class com.example.module.core.domain.** { *; }
-keepclassmembers class com.example.module.core.domain.** { *; }

-keep class com.example.module.core.utils.** { *; }
-keepclassmembers class com.example.module.core.utils.** { *; }

-keep class com.example.module.core.ui.** { *; }
-keepclassmembers class com.example.module.core.ui.** { *; }

-keep class com.example.module.core.data.** { *; }
-keepclassmembers class com.example.module.core.data.** { *; }

##---------------Begin: proguard configuration for LeakCannary ----------
-dontwarn com.squareup.haha.guava.**
-dontwarn com.squareup.haha.perflib.**
-dontwarn com.squareup.haha.trove.**
-dontwarn com.squareup.leakcanary.**
-keep class com.squareup.haha.** { *; }
-keep class com.squareup.leakcanary.** { *; }
-keep class leakcanary.** { *; }

# Marshmallow removed Notification.setLatestEventInfo()
-dontwarn android.app.Notification

# === OkHttp3 (termasuk CertificatePinner) ===
-keep class okhttp3.CertificatePinner { *; }
-keep class okhttp3.CertificatePinner$Builder { *; }

# Keep seluruh OkHttp3 API (opsional tapi aman)
-keep class okhttp3.** { *; }
-dontwarn okhttp3.**

# Keep javax security jika diperlukan
-keep class javax.net.ssl.** { *; }
-dontwarn javax.net.ssl.**

# Kalau pakai OkHttp logging/interceptor
-keep class okhttp3.logging.** { *; }
-dontwarn okhttp3.logging.**

# Keep StringConcatFactory agar tidak dihapus
-dontwarn java.lang.invoke.StringConcatFactory

# Keep entity dan DAO jika menggunakan Room (sesuaikan package)
-keep class com.example.module.core.data.source.local.entity.** { *; }
-keep class com.example.module.core.data.source.local.room.** { *; }
-keep class com.example.module.core.data.source.local.entity.SimpleUserEntity
# Keep Kotlin metadata (penting untuk Kotlin reflection)
-keep class kotlin.Metadata { *; }


