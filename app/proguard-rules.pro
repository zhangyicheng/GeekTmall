# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/apple/Development/adt-bundle-mac-sdk/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:

-keepclassmembers class fqcn.of.javascript.interface.for.webview {
   public *;
}
-keepclassmembers class * extends android.webkit.WebChromeClient {  #如果你的项目中用到了webview的复杂操作 ，最好加入
     public void *(android.webkit.WebView,java.lang.String);
}
-dontwarn com.squareup.okhttp.**

-dontwarn com.tencent.**
-keep class com.tencent.** { *; }
#-keep class com.tencent.mm.sdk.** {
#   *;
#}
-dontwarn com.tencent.stat.**
-keep class com.tencent.stat.** { *; }
-dontwarn com.sina.weibo.sdk.**
-keep class com.sina.weibo.sdk.** { *; }

-dontwarn android.support.**
-keep class android.support.v4.** { *; }
-keep interface android.support.v4.** { *; }
-keep public class * extends android.support.v4.**
-keep public class * extends android.app.Fragment

-keep class com.android.volley.**{*;}
-keep class * extends java.lang.annotation.Annotation { *; }
-keepattributes *Annotation*
-dontwarn com.google.gson.**
-keep class sun.misc.Unsafe { *; }
-keep class com.idea.fifaalarmclock.entity.***
-keep class com.google.gson.** { *; }
#-keep class com.google.gson.stream.** { *; }


-keepclassmembers class * {
   public <init>(org.json.JSONObject);
}

-keep class com.umeng.**

-keep public class com.idea.fifaalarmclock.app.R$*{
    public static final int *;
}

-keep public class com.umeng.fb.ui.ThreadView {
}

-dontwarn com.umeng.**

-dontwarn org.apache.commons.**

-keep public class * extends com.umeng.**

-keep class com.umeng.** {*; }

-dontwarn okio.**
-verbose
-keepattributes Exceptions,InnerClasses
-dontskipnonpubliclibraryclasses
-dontskipnonpubliclibraryclassmembers
-ignorewarnings

-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends java.lang.Throwable {*;}
-keep public class * extends java.lang.Exception {*;}
-keep public class * extends android.support.v4.view.ViewPager {*;}

#-libraryjars libs/alipaySDK-20150610.jar

-keep class com.alipay.android.app.IAlixPay{*;}
-keep class com.alipay.android.app.IAlixPay$Stub{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback$Stub{*;}
-keep class com.alipay.sdk.app.PayTask{ public *;}
-keep class com.alipay.sdk.app.AuthTask{ public *;}


-keepclasseswithmembernames class * {
    native <methods>;
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}
-keep public class * extends android.view.View {  #保持自定义控件指定规则的方法不被混淆
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
    public void set*(...);
}
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-keepclasseswithmembers class com.geek.geekmall.bean.**  { *; }
-keepclasseswithmembers class com.geek.geekmall.model.**  { *; }

-keep class **.Webview2JsInterface { *; }  #保护WebView对HTML页面的API不被混淆
-keepclasseswithmembers class * implements java.io.Serializable { *; } #需要序列化和反序列化的类不能被混淆（注：Java反射用到的类也不能被混淆）
-dontwarn com.lidroid.xutils.**
-keep class com.lidroid.xutils.** { *; }
-keep class **.R$* { *; }  #保持R文件不被混淆，否则，你的反射是获取不到资源id的

-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}
# adding this in to preserve line numbers so that the stack traces
# can be remapped
-renamesourcefileattribute SourceFile
-keepattributes SourceFile,LineNumberTable

 -keepattributes Signature#类型转换错误
