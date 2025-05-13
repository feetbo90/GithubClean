# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
-dontwarn id.zoneordering.core.data.Resource$Error
-dontwarn id.zoneordering.core.data.Resource$Loading
-dontwarn id.zoneordering.core.data.Resource$Success
-dontwarn id.zoneordering.core.data.Resource


-dontwarn com.example.module.core.data.Resource$Error
-dontwarn com.example.module.core.data.Resource$Loading
-dontwarn com.example.module.core.data.Resource$Success
-dontwarn com.example.module.core.data.Resource
-dontwarn com.example.module.core.di.CoreModuleKt
-dontwarn com.example.module.core.domain.model.DetailUser
-dontwarn com.example.module.core.domain.repository.IGithubRepository
-dontwarn com.example.module.core.domain.usecase.GithubInteractor
-dontwarn com.example.module.core.domain.usecase.GithubUseCase
-dontwarn com.example.module.core.ui.FollowsAdapter$OnItemClickCallback
-dontwarn com.example.module.core.ui.FollowsAdapter
-dontwarn com.example.module.core.ui.GithubAdapter$OnItemClickCallback
-dontwarn com.example.module.core.ui.GithubAdapter
-dontwarn com.example.module.core.ui.SearchAdapter$OnItemClickCallback
-dontwarn com.example.module.core.ui.SearchAdapter
-dontwarn com.example.module.core.utils.UserImageLoader

