package com.visibility.eginbeharrekoak

import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo // Añadir import para EditorInfo
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.visibility.eginbeharrekoak.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var repository: EginbeharrakRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        repository = EginbeharrakRepository(this)

        // erakutsi eginbeharrak
        binding.buttonEgitekoak.setOnClickListener {
            ikusiEgitekoak()
        }

        // "+" ikonoari ezarri metodoa
        binding.inputNewTask.setEndIconOnClickListener {
            sortuEginbeharAzkarra()
        }

        // "enter" botoiak ere deituko du metodoa
        binding.inputNewTask.editText?.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                sortuEginbeharAzkarra()
                true
            } else {
                false
            }
        }

        // data eta ordua ezarri
        val now = Calendar.getInstance().time
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        binding.timeText.text = timeFormat.format(now)

        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        binding.dateText.text = dateFormat.format(now)
    }

    private fun ikusiEgitekoak() {
        val intent = Intent(this, EginbeharrekoakActivity::class.java)
        startActivity(intent)
    }

    // egiteko azkarra sortzeko (deskripziorik gabe)
    private fun sortuEginbeharAzkarra() {
        val eginbeharIzena = binding.inputNewTask.editText?.text.toString().trim()
        val eginbeharDeskripzioa = "" // Dejar la descripción vacía o default

        if (eginbeharIzena.isNotBlank()) {
            gehituEginbeharBerria(eginbeharIzena, eginbeharDeskripzioa)
            // Textua garbitu
            binding.inputNewTask.editText?.text?.clear()
        } else {
            // errore mezua erakutsi
            binding.inputNewTask.error = "Mesedez, idatzi eginbeharra"
        }
    }

    // eginbeharra gordetzeko
    private fun gehituEginbeharBerria(eginbeharIzena: String, eginbeharDeskripzioa: String) {
        // Kargatu uneko zerrenda osoa fitxategitik
        val unekoEginbeharrak = repository.kargatuEginbeharrak()

        // Sortu eginbehar berria
        val eginbeharBerria = Eginbeharra(
            eginbeharIzena, // Título de la tarea
            eginbeharDeskripzioa, // Descripción vacía
            false // Estado por defecto
        )

        // Gehitu eginbehar berria zerrendara
        unekoEginbeharrak.add(eginbeharBerria)

        // Gorde zerrenda osoa eta eguneratua fitxategian
        repository.guardarEginbeharrak(unekoEginbeharrak)

        ikusiEgitekoak()
    }
}