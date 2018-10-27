package com.mallaudin.demo

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.mallaudin.coconut.Coconut
import com.mallaudin.coconut.Logger
import com.mallaudin.coconut.validation.CoconutValidationProvider
import com.mallaudin.demo.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding =
                DataBindingUtil.setContentView(this, R.layout.activity_main)

        // initialize in App (preferably)
        Coconut.init(CoconutValidationProvider(), debug = true, logger = object : Logger {
            override fun log(message: String?) {
                Log.d("Coconut", message)
            }
        })
        binding.submit.setOnClickListener {
            Coconut.get().validateLayoutFields(binding.content)
        }

    } // onCreate
}
