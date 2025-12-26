package com.benchmark.sdkvsndk.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.benchmark.sdkvsndk.R
import com.benchmark.sdkvsndk.benchmark.BenchmarkAlgorithm
import com.benchmark.sdkvsndk.benchmark.runBinarySearchBenchmark
import com.benchmark.sdkvsndk.benchmark.runMergeSortBenchmark
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BenchmarkViewModel : ViewModel() {

    data class BenchmarkResult(
        val algorithm: BenchmarkAlgorithm,
        val inputSize: InputSize,
        val sdkTimeMs: Long,
        val ndkTimeMs: Long
    ) {
        val speedup: Double
            get() = if (ndkTimeMs == 0L) 0.0 else sdkTimeMs.toDouble() / ndkTimeMs.toDouble()
    }

    data class InputSize(
        val labelResId: Int, val n: Int
    )

    private val _algorithms = listOf(
        BenchmarkAlgorithm.MERGE_SORT, BenchmarkAlgorithm.BINARY_SEARCH
    )
    val algorithms: List<BenchmarkAlgorithm> = _algorithms

    private val _inputSizes = listOf(
        InputSize(R.string.input_size_1k, 1_000),
        InputSize(R.string.input_size_10k, 10_000),
        InputSize(R.string.input_size_100k, 100_000)
    )
    val inputSizes: List<InputSize> = _inputSizes

    private val _selectedAlgorithm = MutableLiveData(_algorithms.first())
    val selectedAlgorithm: LiveData<BenchmarkAlgorithm> = _selectedAlgorithm

    private val _selectedInputSize = MutableLiveData(_inputSizes[1])
    val selectedInputSize: LiveData<InputSize> = _selectedInputSize

    private val _lastResult = MutableLiveData<BenchmarkResult?>()
    val lastResult: LiveData<BenchmarkResult?> = _lastResult

    private val _isRunning = MutableLiveData(false)
    val isRunning: LiveData<Boolean> = _isRunning

    fun onAlgorithmSelected(algorithm: BenchmarkAlgorithm) {
        _selectedAlgorithm.value = algorithm
    }

    fun onInputSizeSelected(inputSize: InputSize) {
        _selectedInputSize.value = inputSize
    }

    fun runBenchmark() {
        val algo = _selectedAlgorithm.value ?: return
        val size = _selectedInputSize.value ?: return

        if (_isRunning.value == true) return

        _isRunning.value = true

        viewModelScope.launch {
            val result = withContext(Dispatchers.Default) {
                when (algo) {
                    BenchmarkAlgorithm.MERGE_SORT -> runMergeSortBenchmark(size)
                    BenchmarkAlgorithm.BINARY_SEARCH -> runBinarySearchBenchmark(size)
                }
            }
            _lastResult.value = result
            _isRunning.value = false
        }
    }
}