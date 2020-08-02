package br.com.tagliaferrodev.dextra.pottertest.character.domain

import br.com.tagliaferrodev.dextra.pottertest.house.House

data class CharactersDTO(
        val house: House,
        val characters: List<Character>
)
