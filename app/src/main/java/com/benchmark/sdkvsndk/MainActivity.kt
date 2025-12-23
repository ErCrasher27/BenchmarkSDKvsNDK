package com.benchmark.sdkvsndk

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.benchmark.sdkvsndk.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    /**
     * A native method that is implemented by the 'sdkvsndk' native library,
     * which is packaged with this application.
     */
    @Suppress("unused")
    external fun stringFromJNI(): String

    companion object {
        init {
            System.loadLibrary("sdkvsndk")
        }
    }
}
