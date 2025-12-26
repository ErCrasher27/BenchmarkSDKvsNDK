# Sets the minimum CMake version required for this project.
cmake_minimum_required(VERSION 3.22.1)

# Declares the project name. This name is not tied to the final .so name,
# but is useful inside CMake (e.g. ${PROJECT_NAME}).
project("sdkvsndk")

# Creates and names a shared library called "benchmark-native" and
# provides the relative path to its C++ source file.
# Gradle will package the resulting .so inside the APK.
add_library(
        benchmark-native
        SHARED
        benchmark_native.cpp
)

# Finds the Android log library so we can link against it if needed
# (e.g. for __android_log_print calls).
find_library(
        log-lib
        log
)

# Specifies libraries CMake should link to our native library.
# Here we only link against the Android log library.
target_link_libraries(
        benchmark-native
        ${log-lib}
)