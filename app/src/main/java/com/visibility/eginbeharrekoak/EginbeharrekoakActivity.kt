package com.visibility.eginbeharrekoak

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException
import com.visibility.eginbeharrekoak.databinding.ActivityEginbeharrekoakBinding

class EginbeharrekoakActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eginbeharrekoak)
        val binding = ActivityEginbeharrekoakBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // JSON-etik eginbeharrak kargatu
        val eginbeharrenLista = kargatuEginbeharrakJson()

        // Egiaztatu datuak ondo kargatu diren Logcat-en
        if (eginbeharrenLista != null) {
            for (eginbeharra in eginbeharrenLista) {
                Log.d("Eginbeharrak", "Izenburua: ${eginbeharra.izenburua}, Eginda: ${eginbeharra.egina}")
            }
        } else {
            Log.e("Eginbeharrak", "Errorea JSON fitxategia kargatzean.")
        }
        // sortu recyclerview aldagaia
        val adapter: EginbeharraAdapter = EginbeharraAdapter(eginbeharrenLista)
        binding.recyclerViewProjects.setAdapter(adapter)
    }

    private fun kargatuEginbeharrakJson(): List<Eginbeharra> {
        val jsonString: String
        try {
            jsonString = assets.open("egitekoak.json").bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return emptyList()
        }

        val listType = object : TypeToken<List<Eginbeharra>>() {}.type
        return Gson().fromJson(jsonString, listType)
    }
}
