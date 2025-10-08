package com.visibility.eginbeharrekoak

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.visibility.eginbeharrekoak.databinding.ItemEginbeharraBinding

class EginbeharraAdapter(private val eginbeharrak: List<Eginbeharra>) :
    RecyclerView.Adapter<EginbeharraAdapter.EginbeharraViewHolder>() {

    class EginbeharraViewHolder(val binding: ItemEginbeharraBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EginbeharraViewHolder {
        val binding = ItemEginbeharraBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EginbeharraViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EginbeharraViewHolder, position: Int) {
        val eginbeharra = eginbeharrak.get(position)
        holder.binding.textViewIzenburua.text = eginbeharra.izenburua
        holder.binding.textViewDeskripzioa.text = eginbeharra.deskripzioa
        holder.binding.checkboxDone.isChecked = eginbeharra.egina
    }

    override fun getItemCount() = eginbeharrak.size
}
