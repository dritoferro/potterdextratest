package br.com.tagliaferrodev.dextra.pottertest.character.domain

import br.com.tagliaferrodev.dextra.pottertest.house.House

data class CharacterDTO(
        val character: Character,
        val house: House
)
