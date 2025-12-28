package com.benchmark.sdkvsndk.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.benchmark.sdkvsndk.R
import com.benchmark.sdkvsndk.benchmark.BenchmarkAlgorithm
import com.benchmark.sdkvsndk.benchmark.runBinarySearchBenchmark
import com.benchmark.sdkvsndk.benchmark.runMergeSortBenchmark
import com.benchmark.sdkvsndk.model.BenchmarkResult
import com.benchmark.sdkvsndk.model.BenchmarkResults
import com.benchmark.sdkvsndk.model.InputSize
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BenchmarkViewModel : ViewModel() {

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

    private val sdkTimes = mutableListOf<Long>()
    private val ndkTimes = mutableListOf<Long>()
    private var iterations = 0

    private val _canViewStatistics = MutableLiveData(false)
    val canViewStatistics: LiveData<Boolean> = _canViewStatistics

    fun onAlgorithmSelected(algorithm: BenchmarkAlgorithm) {
        _selectedAlgorithm.value = algorithm
        resetStatistics()
    }

    fun onInputSizeSelected(inputSize: InputSize) {
        _selectedInputSize.value = inputSize
        resetStatistics()
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

            sdkTimes.add(result.sdkTimeMs)
            ndkTimes.add(result.ndkTimeMs)
            iterations++

            _lastResult.value = result
            _isRunning.value = false
            _canViewStatistics.value = iterations >= 10
        }
    }

    fun getBenchmarkResults(): BenchmarkResults? {
        if (iterations < 10) return null
        val algorithm = _selectedAlgorithm.value ?: return null
        val size = _selectedInputSize.value ?: return null

        return BenchmarkResults(
            sdkTimes = sdkTimes.toList(),
            ndkTimes = ndkTimes.toList(),
            algorithm = algorithm,
            inputSize = size.n,
            iterations = iterations
        )
    }

    private fun resetStatistics() {
        sdkTimes.clear()
        ndkTimes.clear()
        iterations = 0
        _canViewStatistics.value = false
    }
}
