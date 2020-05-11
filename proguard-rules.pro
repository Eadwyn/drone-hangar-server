-keepattributes Exceptions,InnerClasses,*Annotation*,Signature,EnclosingMethod

-dontshrink ##默认是开启的，这里关闭shrink，即不删除没有使用的类/成员
-dontoptimize ##默认是开启的，这里关闭字节码级别的优化
-dontwarn java.**

-keepclasseswithmembers public class * { public static void main(java.lang.String[]);} ##保留main方法的类及其方法名

-keepclassmembers enum * {
    public static <methods>;
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

