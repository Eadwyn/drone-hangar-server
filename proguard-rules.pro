-dontshrink
-dontoptimize
-keepdirectories
-adaptclassstrings
#-dontusemixedcaseclassnames
-flattenpackagehierarchy 'com.utrons.dronehangarserver'
-keepattributes Exceptions,InnerClasses,Signature,Deprecated,SourceFile,LineNumberTable,LocalVariable*Table,*Annotation*,Synthetic,EnclosingMethod

-keep class com.utrons.dronehangarserver.MainApplication
-keep class * extends org.springframework.boot.ApplicationRunner

#-keepclasseswithmembers public class com.utrons.dronehangarserver.MainApplication {
#    public static void main(java.lang.String[]);
#}

# Keep - Applications. Keep all application classes, along with their 'main'
# methods.
#-keepclasseswithmembers public class * {
#    public static void main(java.lang.String[]);
#}
-keepclassmembers public class * {
    public static void main(java.lang.String[]);
}

# Also keep - Enumerations. Keep the special static methods that are required in
# enumeration classes.
-keepclassmembers enum  * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# Keep names - Native method names. Keep all native class/method names.
-keepclasseswithmembers,includedescriptorclasses,allowshrinking class * {
    native <methods>;
}

#-keepnames class * implements java.io.Serializable
-keepclassmembers class * implements java.io.Serializable {
    private static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    !static !transient <fields>;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

# 不混淆setter/getter，否则spring mvc无法进行值设置和json化
-keepclassmembers class * {
    *** get*();
    void set*(***);
    boolean is*();
}