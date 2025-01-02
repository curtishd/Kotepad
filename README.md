A lightweight notepad written in Kotlin. Enjoy:)!
### jpackage
```ps1
cd build && jpackage.exe -t app-image -n kotepad -p '.\libs\annotations-13.0.jar;.\classes\java\main;.\libs\flatlaf-3.5.4.jar;.\libs\flatlaf-extras-3.5.4.jar;.\libs\jsvg-1.4.0.jar;.\libs\kotlin-stdlib-2.0.21.jar' -m me.cdh/me.cdh.Main --jlink-options '--no-man-pages --no-header-files --strip-debug --compress zip-9 --strip-native-commands'
```