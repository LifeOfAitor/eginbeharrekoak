package com.visibility.eginbeharrekoak

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import com.google.android.material.textfield.TextInputEditText

class EditatuEginbeharraDialog(
    private val eginbeharra: Eginbeharra,
    private val onSave: (Eginbeharra) -> Unit
) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val context = requireContext()
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.dialog_editatu_eginbeharra, null)

        val editTextIzenburua = view.findViewById<TextInputEditText>(R.id.editTextIzenburua)
        val editTextDeskripzioa = view.findViewById<TextInputEditText>(R.id.editTextDeskripzioa)


        // Lehen balioak ezarri
        editTextIzenburua.setText(eginbeharra.izenburua)
        editTextDeskripzioa.setText(eginbeharra.deskripzioa)

        return AlertDialog.Builder(context)
            .setTitle("Eginbeharra editatu")
            .setView(view)
            .setPositiveButton("Gorde") { _, _ ->
                val berria = eginbeharra.copy(
                    izenburua = editTextIzenburua.text.toString(),
                    deskripzioa = editTextDeskripzioa.text.toString()
                )
                onSave(berria)
            }
            .setNegativeButton("Utzi", null)
            .create()
    }
}
