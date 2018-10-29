package com.mallaudin.demo

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.mallaudin.coconut.Coconut
import com.mallaudin.demo.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding =
                DataBindingUtil.setContentView(this, R.layout.activity_main)

        // initialize in App (preferably)
        Coconut.init(MyValidationProvider())
        binding.submit.setOnClickListener {
            Coconut.get().validateLayoutFields(binding.content)
        }

    } // onCreate
}
