package com.benchmark.sdkvsndk.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.benchmark.sdkvsndk.R
import com.benchmark.sdkvsndk.benchmark.BenchmarkAlgorithm
import com.benchmark.sdkvsndk.databinding.FragmentBenchmarkBinding
import com.benchmark.sdkvsndk.viewmodel.BenchmarkViewModel

class BenchmarkFragment : Fragment() {

    private var _binding: FragmentBenchmarkBinding? = null
    private val binding get() = _binding!!
    private val viewModel: BenchmarkViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBenchmarkBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAlgorithmSpinner()
        setupInputSizeSpinner()
        setupObservers()
        setupButtons()
    }

    private fun setupButtons() {
        binding.runBenchmarkButton.setOnClickListener {
            viewModel.runBenchmark()
        }

        binding.viewStatisticsButton.setOnClickListener {
            val results = viewModel.getBenchmarkResults()
            if (results != null) {
                val action =
                    BenchmarkFragmentDirections.actionBenchmarkFragmentToStatisticsFragment(results)
                findNavController().navigate(action)
            } else {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.benchmark_stats_require_runs),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun setupObservers() {
        viewModel.lastResult.observe(viewLifecycleOwner) { result ->
            result ?: return@observe

            binding.sdkTimeText.text = getString(R.string.benchmark_sdk_time, result.sdkTimeMs)
            binding.ndkTimeText.text = getString(R.string.benchmark_ndk_time, result.ndkTimeMs)
            binding.speedupText.text = getString(
                R.string.benchmark_speedup, String.format("%.2f", result.speedup)
            )
        }

        viewModel.canViewStatistics.observe(viewLifecycleOwner) { canView ->
            // binding.viewStatisticsButton.isEnabled = canView handled by toast and no action by null
        }
    }

    private fun setupAlgorithmSpinner() {
        val labels = viewModel.algorithms.map {
            when (it) {
                BenchmarkAlgorithm.MERGE_SORT -> getString(R.string.algorithm_merge_sort)
                BenchmarkAlgorithm.BINARY_SEARCH -> getString(R.string.algorithm_binary_search)
            }
        }

        val adapter = ArrayAdapter(
            requireContext(), android.R.layout.simple_spinner_item, labels
        ).also {
            it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }

        binding.algorithmSpinner.adapter = adapter
        binding.algorithmSpinner.setSelection(0)

        binding.algorithmSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?, view: View?, position: Int, id: Long
                ) {
                    viewModel.onAlgorithmSelected(viewModel.algorithms[position])
                }

                override fun onNothingSelected(parent: AdapterView<*>?) = Unit
            }
    }

    private fun setupInputSizeSpinner() {
        val labels = viewModel.inputSizes.map { getString(it.labelResId) }

        val adapter = ArrayAdapter(
            requireContext(), android.R.layout.simple_spinner_item, labels
        ).also {
            it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }

        binding.inputSizeSpinner.adapter = adapter
        binding.inputSizeSpinner.setSelection(1)

        binding.inputSizeSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?, view: View?, position: Int, id: Long
                ) {
                    viewModel.onInputSizeSelected(viewModel.inputSizes[position])
                }

                override fun onNothingSelected(parent: AdapterView<*>?) = Unit
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}