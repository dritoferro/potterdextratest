package br.com.tagliaferrodev.dextra.pottertest.util

interface FromDTO<Entity> {
    fun fromDTO(): Entity
}
