package com.visibility.eginbeharrekoak

data class Eginbeharra(
    var izenburua: String,
    var deskripzioa: String,
    var egina: Boolean

) {
    //checkbox-a klikatzean aldatuko da egina-ren egoera
    fun eginda() {
        egina = !egina
    }
}