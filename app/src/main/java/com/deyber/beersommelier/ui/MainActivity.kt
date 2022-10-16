package com.deyber.beersommelier.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.deyber.beersommelier.R
import com.deyber.beersommelier.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_BeerSommelier)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}