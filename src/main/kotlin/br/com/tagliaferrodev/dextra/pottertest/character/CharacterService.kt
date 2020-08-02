package br.com.tagliaferrodev.dextra.pottertest.character

import br.com.tagliaferrodev.dextra.pottertest.character.domain.Character
import br.com.tagliaferrodev.dextra.pottertest.character.domain.CharacterDTO
import br.com.tagliaferrodev.dextra.pottertest.character.domain.CharactersDTO
import br.com.tagliaferrodev.dextra.pottertest.integration.PotterClient
import org.springframework.stereotype.Service
import java.util.*
import javax.persistence.EntityNotFoundException

@Service
class CharacterService(private val repository: CharacterRepository,
                       private val partner: PotterClient) {

    fun save(character: Character): Character {
        partner.findByHouseId(character.house!!)

        return repository.save(character)
    }

    fun findById(id: String): CharacterDTO {
        val character = repository.findById(UUID.fromString(id)).orElseThrow { throw EntityNotFoundException("Character with id: $id not found.") }

        val house = partner.findByHouseId(character.house!!)

        return CharacterDTO(character, house)
    }

    fun findByAttributes(name: String?, houseId: String?): List<CharactersDTO> {
        val characters = if (!name.isNullOrBlank()) {
            repository.findAllByNameContaining(name).orElseThrow { throw EntityNotFoundException("Cannot find any character containing this name: $name") }
        } else if (!houseId.isNullOrBlank()) {
            repository.findAllByHouse(houseId).orElseThrow { throw EntityNotFoundException("Cannot find any character for this house: $houseId") }
        } else {
            repository.findAll()
        }

        return characters.groupBy { it.house }.map { (house, characters) ->
            val houseData = partner.findByHouseId(house!!)

            CharactersDTO(houseData, characters)
        }
    }
}