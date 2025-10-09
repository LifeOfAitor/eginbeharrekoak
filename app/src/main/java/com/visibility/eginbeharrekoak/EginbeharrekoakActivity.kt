package com.visibility.eginbeharrekoak

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.visibility.eginbeharrekoak.databinding.ActivityEginbeharrekoakBinding
import java.io.IOException

class EginbeharrekoakActivity : AppCompatActivity() {

    // Binding klasea, layout elementuekin komunikatzeko
    private lateinit var binding: ActivityEginbeharrekoakBinding

    // RecyclerView-rako adapterra
    private lateinit var adapter: EginbeharraAdapter

    // Eginbeharren zerrenda, lista aldakorra (mutable) da, ondo gorde eta kudeatzeko
    private lateinit var eginbeharrenLista: MutableList<Eginbeharra>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Layoutaren binding-a sortu eta ezarri
        binding = ActivityEginbeharrekoakBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Eginbeharren zerrenda kargatu, lehenik bilatzen du dispositiboaren fitxategi lokalean
        // eta ez badago, assets-en dagoena kargatzen du
        eginbeharrenLista = kargatuEginbeharrakLocalJson()

        // Adapterra sortu eta callback-a definitu:
        // Eginbeharra markatzen denean (checkbox-a aktibatzen denean),
        // eginbeharrak ezabatuko dira zerrendatik eta eguneratuko da fitxategi lokaleko JSON-a
        adapter = EginbeharraAdapter(eginbeharrenLista) { eginbeharra ->
            // Eginbeharraren indizea aurkitu zerrendan
            val index = eginbeharrenLista.indexOf(eginbeharra)
            // Baldintza: eginbeharra egina bada eta zerrendan badago
            if (eginbeharra.egina && index != -1) {
                // Eginbeharra ezabatu zerrendatik
                eginbeharrenLista.removeAt(index)
                // Adapterrari ezabapena jakinarazi, RecyclerView eguneratzeko
                adapter.notifyItemRemoved(index)
                // Zerrenda eguneratua gorde fitxategi lokalean JSON formatuan
                guardarEginbeharrakJson(eginbeharrenLista)
            }
        }

        // RecyclerView konfiguratu: Layout manager-a eta adapterra ezarri
        binding.recyclerViewProjects.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewProjects.adapter = adapter
    }

    /**
     * Assets karpetatik JSON fitxategia kargatu eta Eginbeharra motako MutableList bihurtu
     */
    private fun kargatuEginbeharrakJson(): MutableList<Eginbeharra> {
        val jsonString: String
        try {
            // "egitekoak.json" fitxategia irakurri assets karpetatik
            jsonString = assets.open("egitekoak.json").bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            // Arazo bat badago, zerrenda hutsa itzuli
            return mutableListOf()
        }

        // JSON-a MutableList<Eginbeharra> bihurtzeko motaren deskribapena
        val listType = object : TypeToken<MutableList<Eginbeharra>>() {}.type
        // Gson erabiliz JSON deserializatu eta zerrenda itzuli
        return Gson().fromJson(jsonString, listType)
    }

    /**
     * Eginbeharrak JSON formatuan serializeatu eta fitxategi lokal batean gorde
     * Horrela aldaketak persistitzen dira
     */
    private fun guardarEginbeharrakJson(lista: List<Eginbeharra>) {
        // Zerrenda JSON string bihurtu
        val jsonString = Gson().toJson(lista)
        // Fitxategi lokala irekita idazteko eta JSON-a bertan idatzi
        openFileOutput("egitekoak.json", MODE_PRIVATE).use {
            it.write(jsonString.toByteArray())
        }
    }

    /**
     * Lehenik fitxategi lokaletik JSON-a kargatu saiatu,
     * fitxategia ez badago, assets-etik kargatu jatorrizko zerrenda
     */
    private fun kargatuEginbeharrakLocalJson(): MutableList<Eginbeharra> {
        return try {
            // Fitxategi lokala irakurri
            val jsonString = openFileInput("egitekoak.json").bufferedReader().use { it.readText() }
            val listType = object : TypeToken<MutableList<Eginbeharra>>() {}.type
            // JSON deserializatu eta itzuli
            Gson().fromJson(jsonString, listType)
        } catch (e: IOException) {
            // Fitxategia ez badago, asset-etik kargatu
            kargatuEginbeharrakJson()
        }
    }
}
