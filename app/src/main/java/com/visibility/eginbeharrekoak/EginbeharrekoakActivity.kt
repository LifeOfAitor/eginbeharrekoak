package com.visibility.eginbeharrekoak

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.visibility.eginbeharrekoak.databinding.ActivityEginbeharrekoakBinding

class EginbeharrekoakActivity : AppCompatActivity(), OnEginbeharraMoveListener {

    private lateinit var binding: ActivityEginbeharrekoakBinding
    private lateinit var adapter: EginbeharraAdapter
    private lateinit var eginbeharrenLista: MutableList<Eginbeharra>
    private lateinit var repository: EginbeharrakRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEginbeharrekoakBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()

        repository = EginbeharrakRepository(this)
        eginbeharrenLista = repository.kargatuEginbeharrak()

        adapter = EginbeharraAdapter(eginbeharrenLista, { eginbeharra ->
            val index = eginbeharrenLista.indexOf(eginbeharra)
            if (eginbeharra.egina && index != -1) {
                eginbeharrenLista.removeAt(index)
                adapter.notifyItemRemoved(index)
                repository.guardarEginbeharrak(eginbeharrenLista)
            }
        }, onEginbeharraItemClick = { eginbeharra ->
            EditatuEginbeharraDialog(eginbeharra) { berria ->
                // Zerrendan eguneratu
                val index = eginbeharrenLista.indexOf(eginbeharra)
                if (index != -1) {
                    eginbeharrenLista[index] = berria
                    adapter.notifyItemChanged(index)
                    repository.guardarEginbeharrak(eginbeharrenLista)
                }
            }.show(supportFragmentManager, "EditatuEginbeharra")
        },this) // this: OnEginbeharraMoveListener

        binding.recyclerViewProjects.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewProjects.adapter = adapter

        binding.buttonGehitu.setOnClickListener {
            gehituActivityra()
        }
    }

    override fun onResume() {
        super.onResume()
        val eguneratutakoLista = repository.kargatuEginbeharrak()
        adapter.actualizarLista(eguneratutakoLista)
    }

    private fun gehituActivityra() {
        val intent = Intent(this, GehituActivity::class.java)
        startActivity(intent)
    }

    // Listener metodoak mugitzeko botoientzat
    override fun onMoveUp(position: Int) {
        if (position > 0) {
            eginbeharrenLista.swap(position, position - 1)
            adapter.notifyItemMoved(position, position - 1)
            repository.guardarEginbeharrak(eginbeharrenLista)
        }
    }

    override fun onMoveDown(position: Int) {
        if (position < eginbeharrenLista.size - 1) {
            eginbeharrenLista.swap(position, position + 1)
            adapter.notifyItemMoved(position, position + 1)
            repository.guardarEginbeharrak(eginbeharrenLista)
        }
    }

    // Extension function: zerrendako elementuak trukatzeko
    private fun <T> MutableList<T>.swap(index1: Int, index2: Int) {
        val temp = this[index1]
        this[index1] = this[index2]
        this[index2] = temp
    }
}
