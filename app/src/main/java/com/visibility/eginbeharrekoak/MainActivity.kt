package com.visibility.eginbeharrekoak

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.visibility.eginbeharrekoak.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //button egitekoak ezarri akzioa
        binding.buttonEgitekoak.setOnClickListener {
            ikusiEgitekoak()
        }
        binding.buttonGehitu.setOnClickListener {
            gehituActivityra()
        }
    }

    private fun gehituActivityra() {
        val intent = Intent(this, GehituActivity::class.java)
        startActivity(intent)
    }

    private fun ikusiEgitekoak() {
        val intent = Intent(this, EginbeharrekoakActivity::class.java)
        startActivity(intent)
    }
}
