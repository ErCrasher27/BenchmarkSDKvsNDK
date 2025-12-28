package com.benchmark.sdkvsndk.benchmark

// BENCHMARK-EXTENSION-POINT

/**
 * Singleton that exposes native (C++) benchmark functions to Kotlin.
 *
 * The corresponding implementations live in `benchmark_native.cpp` and are
 * exported via JNI with matching names:
 *
 *  - Java_com_benchmark_sdkvsndk_benchmark_NativeBenchmark_mergeSort
 *  - Java_com_benchmark_sdkvsndk_benchmark_NativeBenchmark_binarySearch
 */
object NativeBenchmark {

    init {
        // Load the native shared library at app startup.
        // The name "benchmark-native" must match the one declared in CMakeLists.txt:
        // add_library(benchmark-native SHARED ...)
        System.loadLibrary("benchmark-native")
    }

    /**
     * Runs merge sort on the provided array in C++ and returns
     * the execution time in milliseconds.
     *
     * Implemented in `benchmark_native.cpp` as a JNI function.
     */
    external fun mergeSort(data: IntArray): Long

    /**
     * Runs a binary search on the given sorted array in C++ for the
     * specified target value and returns the execution time in milliseconds.
     *
     * Implemented in `benchmark_native.cpp` as a JNI function.
     */
    external fun binarySearch(sorted: IntArray, target: Int): Long
}