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
-dontoptimize
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-verbose
# Preserve some attributes that may be required for reflection.
-keep class com.mappls.sdk.maps.** { *; }
-keepattributes AnnotationDefault,
                EnclosingMethod,
                InnerClasses,
                RuntimeVisibleAnnotations,
                RuntimeVisibleParameterAnnotations,
                RuntimeVisibleTypeAnnotations,
                Signature
-keepattributes *Annotation*

-dontwarn org.w3c.dom.**
-dontwarn org.joda.time.**
-dontwarn org.shaded.apache.**
-dontwarn org.ietf.jgss.**
-dontwarn com.firebase.**
-dontwarn org.conscrypt.**
-dontwarn org.bouncycastle.**
-dontwarn org.openjsse.**
-keep public class com.google.firebase.** {*;}
-keep class com.google.android.gms.internal.** {*;}
-keepclasseswithmembers class com.google.firebase.FirebaseException
# Basic ProGuard rules for Firebase Android SDK 2.0.0+
-keep class com.firebase.** { *; }

-keepnames class com.fasterxml.jackson.** { *; }
-keepnames class javax.servlet.** { *; }
-keepnames class org.ietf.jgss.** { *; }

-keepattributes LineNumberTable,SourceFile
-renamesourcefileattribute SourceFile