A collection of random utilities for working with Commodore 64 memory ranges- sprite editor, character set editor, bitmap editor...

Uses Maven Shade plugin to create a fat jar (.jar file including all the JavaFX platform-specific dependencies for whatever platform you're building on)

Profile: YourMama
If enabled, the shaded .jar will include platform-specific dependencies for *all* platforms- i.e. .dlls on Windows, .so on linux, .dylib for Mac...  but this breaks the javafx:run function so it's not on by default.