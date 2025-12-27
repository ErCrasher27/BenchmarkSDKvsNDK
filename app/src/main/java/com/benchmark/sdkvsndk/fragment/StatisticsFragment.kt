package com.benchmark.sdkvsndk.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.benchmark.sdkvsndk.R
import com.benchmark.sdkvsndk.databinding.FragmentStatisticsBinding
import com.benchmark.sdkvsndk.viewmodel.StatisticsViewModel

class StatisticsFragment : Fragment() {

    private var _binding: FragmentStatisticsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: StatisticsViewModel by viewModels()
    private val args: StatisticsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStatisticsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.setResults(args.benchmarkResults)
        setupUI()
        setupButtons()
    }

    private fun setupUI() {
        val results = viewModel.results ?: return

        binding.apply {
            tvInputSize.text = getString(R.string.statistics_input_size, results.inputSize)
            tvIterations.text = getString(R.string.statistics_iterations, results.iterations)
            tvSpeedup.text = viewModel.getSpeedupLabel()
            tvWinPercentage.text = viewModel.getWinnerLabel()
            tvSdkAverage.text = getString(R.string.statistics_sdk_average, results.sdkAverage)
            tvNdkAverage.text = getString(R.string.statistics_ndk_average, results.ndkAverage)

            progressSdkWins.progress = results.sdkWinPercentage
            progressNdkWins.progress = results.ndkWinPercentage
        }
    }

    private fun setupButtons() {
        binding.btnRunAgain.setOnClickListener {
            findNavController().popBackStack(R.id.benchmarkFragment, false)
        }

        binding.btnBackHome.setOnClickListener {
            findNavController().popBackStack(R.id.homeFragment, false)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}