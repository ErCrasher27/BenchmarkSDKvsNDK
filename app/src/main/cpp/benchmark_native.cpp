#include <jni.h>
#include <vector>
#include <algorithm>
#include <chrono>

// BENCHMARK-EXTENSION-POINT

// JNI entry point for the native merge sort benchmark.
// external fun mergeSort(data: IntArray): Long
extern "C" JNIEXPORT jlong JNICALL
Java_com_benchmark_sdkvsndk_benchmark_NativeBenchmark_mergeSort(JNIEnv *env, jobject /*thiz*/,
                                                                jintArray data) {

    // Get the length of the Java int array.
    jsize n = env->GetArrayLength(data);

    // Obtain a pointer to the array elements.
    // This may copy the data or pin it, depending on the VM implementation.
    jint *elements = env->GetIntArrayElements(data, nullptr);

    // Copy the data into a C++ vector for easier manipulation.
    std::vector<int> vec(elements, elements + n);

    // Measure the time spent only inside the C++ algorithm.
    auto start = std::chrono::high_resolution_clock::now();

    // Use a stable sort implementation from the C++ standard library.
    std::stable_sort(vec.begin(), vec.end());

    auto end = std::chrono::high_resolution_clock::now();
    auto diff = std::chrono::duration_cast<std::chrono::milliseconds>(end - start);

    // Release the elements back to the JVM.
    // Mode 0 = copy back and free the buffer (if it was copied).
    env->ReleaseIntArrayElements(data, elements, 0);

    // Return the elapsed time in milliseconds as a jlong.
    return static_cast<jlong>(diff.count());
}

// JNI entry point for the native binary search benchmark.
// external fun binarySearch(sorted: IntArray, target: Int): Long
extern "C" JNIEXPORT jlong JNICALL
Java_com_benchmark_sdkvsndk_benchmark_NativeBenchmark_binarySearch(JNIEnv *env, jobject /*thiz*/,
                                                                   jintArray sorted, jint target) {

    // Get length and raw elements from the Java int array.
    jsize n = env->GetArrayLength(sorted);
    jint *elements = env->GetIntArrayElements(sorted, nullptr);

    // Copy into a C++ vector. The array is expected to be already sorted.
    std::vector<int> vec(elements, elements + n);

    auto start = std::chrono::high_resolution_clock::now();

    // Perform a lower_bound (binary search) on the vector.
    // We ignore the actual index/value because we only care about timing.
    auto it = std::lower_bound(vec.begin(), vec.end(), target);
    (void) it; // Explicitly mark 'it' as unused to avoid compiler warnings.

    auto end = std::chrono::high_resolution_clock::now();
    auto diff = std::chrono::duration_cast<std::chrono::milliseconds>(end - start);

    // Release the Java array elements.
    env->ReleaseIntArrayElements(sorted, elements, 0);

    // Return the elapsed time in milliseconds.
    return static_cast<jlong>(diff.count());
}