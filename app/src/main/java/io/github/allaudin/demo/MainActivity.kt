package io.github.allaudin.demo

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.github.allaudin.coconut.Coconut
import io.github.allaudin.coconut.CoconutValidationProvider
import io.github.allaudin.demo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding =
                DataBindingUtil.setContentView(this, R.layout.activity_main)

        // initialize in App (preferably)
        Coconut.init(CoconutValidationProvider.get())

        binding.submit.setOnClickListener { v -> Coconut.get().validateLayoutFields(binding.content) }

    } // onCreate
}
