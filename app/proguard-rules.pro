# Keep annotations
-keepattributes *Annotation*
-keepattributes Signature
-keepattributes EnclosingMethod
-keepattributes InnerClasses
-keepattributes SourceFile,LineNumberTable

# Hide source file name in stack traces (replace with "SourceFile")
-renamesourcefileattribute SourceFile

# AndroidX & Material Components are well-tested with R8.
# Default Android optimize.txt covers most cases.

# Keep MainActivity (referenced from AndroidManifest by name)
-keep class com.example.testan.MainActivity { *; }

# Keep custom Fragment subclasses (referenced by name via FragmentStateAdapter)
-keep class com.example.testan.ui.** extends androidx.fragment.app.Fragment { *; }

# Suppress warnings for known harmless notes
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
-dontwarn javax.annotation.**
-dontwarn org.jetbrains.annotations.**