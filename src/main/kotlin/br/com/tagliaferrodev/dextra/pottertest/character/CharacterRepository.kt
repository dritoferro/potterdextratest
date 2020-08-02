package br.com.tagliaferrodev.dextra.pottertest.character

import br.com.tagliaferrodev.dextra.pottertest.character.domain.Character
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CharacterRepository : JpaRepository<Character, UUID> {

    fun findAllByHouse(house: String): Optional<List<Character>>

    fun findAllByNameContaining(name: String): Optional<List<Character>>
}
