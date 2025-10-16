package com.visibility.eginbeharrekoak

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.visibility.eginbeharrekoak.databinding.ActivityGehituBinding
import android.view.Gravity
import android.widget.ImageView
import android.widget.TextView


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

        showCustomToast(this, "$eginbeharIzena eginbeharra gehituta")


        // 5. Itxi Activity hau eta itzuli aurrekora (EginbeharrekoakActivity)
        finish()
    }

    //custom toast erabiltzeko, horrela mezua hobeto egongo da
    fun showCustomToast(context: Context, message: String) {
        val inflater = LayoutInflater.from(context)
        val layout = inflater.inflate(R.layout.custom_toast, null)

        // ezarri ikonoa eta textua
        val icon = layout.findViewById<ImageView>(R.id.toast_icon)
        icon.setImageResource(R.drawable.icc_added)

        val text = layout.findViewById<TextView>(R.id.toast_text)
        text.text = message

        // Crear y mostrar Toast
        with(Toast(context)) {
            duration = Toast.LENGTH_SHORT
            view = layout
            setGravity(Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL, 0, 100)
            show()
        }
    }


}
