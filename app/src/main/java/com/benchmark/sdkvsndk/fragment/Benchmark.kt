package com.benchmark.sdkvsndk.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.benchmark.sdkvsndk.R
import com.benchmark.sdkvsndk.viewmodel.BenchmarkViewModel

class Benchmark : Fragment() {

    companion object {
        fun newInstance() = Benchmark()
    }

    private val viewModel: BenchmarkViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_benchmark, container, false)
    }
}