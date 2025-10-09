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
    private lateinit var repository: EginbeharrakRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Layoutaren binding-a sortu eta ezarri
        binding = ActivityEginbeharrekoakBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Eginbeharren zerrenda kargatu, lehenik bilatzen du dispositiboaren fitxategi lokalean
        // eta ez badago, assets-en dagoena kargatzen du
        eginbeharrenLista = repository.kargatuEginbeharrak()

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
                repository.guardarEginbeharrak(eginbeharrenLista)
            }
        }

        // RecyclerView konfiguratu: Layout manager-a eta adapterra ezarri
        binding.recyclerViewProjects.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewProjects.adapter = adapter
    }
}

