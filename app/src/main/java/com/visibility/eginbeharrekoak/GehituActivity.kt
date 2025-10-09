package com.visibility.eginbeharrekoak

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.visibility.eginbeharrekoak.databinding.ActivityGehituBinding


class GehituActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGehituBinding
    private lateinit var repository: EginbeharrakRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityGehituBinding.inflate(layoutInflater)
        setContentView(binding.root)
        repository = EginbeharrakRepository(this)
        binding.btnSortu.setOnClickListener {
            // Lortu erabiltzaileak idatzitako testua
            val eginbeharIzena = binding.editTextIzenburua.text.toString()
            val eginbeharDeskripzioa = binding.editTextDescripcion.text.toString()

            // Ziurtatu testua ez dagoela hutsik
            if (eginbeharIzena.isNotBlank() && eginbeharDeskripzioa.isNotBlank()) {
                // Deitu metodo laguntzaileari logika guztia kudeatzeko
                gehituEginbeharBerria(eginbeharIzena, eginbeharDeskripzioa)
            } else {
                // Erabiltzaileari jakinarazi zerbait idatzi behar duela
                Toast.makeText(this, "Mesedez, idatzi eginbehar baten izena", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun gehituEginbeharBerria(eginbeharIzena: String, eginbeharDeskripzioa: String) {
        // 1. Kargatu uneko zerrenda osoa fitxategitik
        val unekoEginbeharrak = repository.kargatuEginbeharrak()

        // 2. Sortu eginbehar berria
        val eginbeharBerria = Eginbeharra(
            eginbeharIzena, eginbeharDeskripzioa, false
        )

        // 3. Gehitu eginbehar berria zerrendara
        unekoEginbeharrak.add(eginbeharBerria)

        // 4. Gorde zerrenda osoa eta eguneratua fitxategian
        repository.guardarEginbeharrak(unekoEginbeharrak)

        // 5. Itxi Activity hau eta itzuli aurrekora (EginbeharrekoakActivity)
        finish()
    }

}
