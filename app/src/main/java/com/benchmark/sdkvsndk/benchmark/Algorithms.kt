package com.benchmark.sdkvsndk.benchmark

// BENCHMARK-EXTENSION-POINT

/**
 * Kotlin implementation of merge sort on an IntArray.
 *
 * This is a standard recursive, divide‑and‑conquer merge sort:
 *  - Split the array into two halves [l, m] and [m+1, r].
 *  - Recursively sort each half.
 *  - Merge the two sorted halves back into the original array.
 */
fun kotlinMergeSort(a: IntArray, l: Int, r: Int) {
    if (l >= r) return
    val m = (l + r) / 2
    kotlinMergeSort(a, l, m)
    kotlinMergeSort(a, m + 1, r)
    merge(a, l, m, r)
}

/**
 * Merges two consecutive sorted segments of [a]:
 *  - left segment:  [l, m]
 *  - right segment: [m+1, r]
 *
 * The result is a single sorted segment [l, r] inside the same array.
 */
private fun merge(a: IntArray, l: Int, m: Int, r: Int) {
    val left = a.copyOfRange(l, m + 1)
    val right = a.copyOfRange(m + 1, r + 1)

    var i = 0   // index in left
    var j = 0   // index in right
    var k = l   // index in a

    while (i < left.size && j < right.size) {
        a[k++] = if (left[i] <= right[j]) left[i++] else right[j++]
    }
    // Copy any remaining elements.
    while (i < left.size) a[k++] = left[i++]
    while (j < right.size) a[k++] = right[j++]
}

/**
 * Kotlin implementation of binary search on a sorted IntArray.
 *
 * Returns the index of [target] if found, or -1 otherwise.
 */
fun kotlinBinarySearch(a: IntArray, target: Int): Int {
    var l = 0
    var r = a.lastIndex
    while (l <= r) {
        // Unsigned shift to avoid overflow when l and r are large.
        val m = (l + r) ushr 1
        when {
            a[m] == target -> return m
            a[m] < target -> l = m + 1
            else -> r = m - 1
        }
    }
    return -1
}