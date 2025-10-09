package com.visibility.eginbeharrekoak

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.visibility.eginbeharrekoak.databinding.ItemEginbeharraBinding

// Class honek balio du recyclerview barruan dauden itemak informazioz populatzeko
// json fitxeroan egungo dira fitxero honetako eginbehar guztiak
// Fitxero honek List<Eginbeharra> lista jasoko du nun listan daden json fitxategiko datuak


//Gehituko dugu lambda funtzio bat konstruktorean: onEginbeharraClick
class EginbeharraAdapter(
    private val eginbeharrak: List<Eginbeharra>,
    private val onEginbeharraClick: (Eginbeharra) -> Unit
) : RecyclerView.Adapter<EginbeharraAdapter.EginbeharraViewHolder>() {

    class EginbeharraViewHolder(val binding: ItemEginbeharraBinding) :
        RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EginbeharraViewHolder {
        val binding = ItemEginbeharraBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return EginbeharraViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EginbeharraViewHolder, position: Int) {
        val eginbeharra = eginbeharrak[position]

        holder.binding.textViewIzenburua.text = eginbeharra.izenburua
        holder.binding.textViewDeskripzioa.text = eginbeharra.deskripzioa
        holder.binding.checkboxDone.isChecked = eginbeharra.egina

        // 2. Ezarri OnClickListener-a CheckBox-ari
        holder.binding.checkboxDone.setOnClickListener {
            // Klik egitean, egoera aldatu
            eginbeharra.egina = holder.binding.checkboxDone.isChecked
            onEginbeharraClick(eginbeharra)
        }
    }

    override fun getItemCount() = eginbeharrak.size
}