package com.example.converter

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.converter.databinding.ActivityMainBinding
import com.example.converter.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.convertButton.setOnClickListener {
            viewModel.convert(
                binding.etFrom.text.toString(),
                binding.spFromCurrency.selectedItem.toString(),
                binding.spToCurrency.selectedItem.toString()
            )
        }

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.conversion.collect { event ->
                    when(event) {
                        is MainViewModel.CurrencyEvent.Success -> {
                            binding.progressBar.isVisible = false
                            binding.tvResult.setTextColor(Color.BLACK)
                            binding.tvResult.text = event.resultText
                        }
                        is MainViewModel.CurrencyEvent.Failure -> {
                            binding.progressBar.isVisible = false
                            binding.tvResult.setTextColor(Color.RED)
                            binding.tvResult.text = event.errorText
                        }
                        is MainViewModel.CurrencyEvent.Loading -> {
                            binding.progressBar.isVisible = true
                        }
                        else -> Unit
                    }
                }
            }
        }
    }
}
