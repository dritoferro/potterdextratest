package br.com.tagliaferrodev.dextra.pottertest.character

import br.com.tagliaferrodev.dextra.pottertest.character.domain.Character
import br.com.tagliaferrodev.dextra.pottertest.character.domain.CharacterDTO
import br.com.tagliaferrodev.dextra.pottertest.character.domain.CharactersDTO
import br.com.tagliaferrodev.dextra.pottertest.integration.PotterClient
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*
import javax.persistence.EntityNotFoundException

@Service
class CharacterService(private val repository: CharacterRepository,
                       private val partner: PotterClient) {

    @Transactional
    fun save(character: Character): Character {
        partner.findByHouseId(character.house!!)

        return repository.save(character)
    }

    @Transactional
    fun findById(id: String): CharacterDTO {
        val character = simpleFind(id)

        val house = partner.findByHouseId(character.house!!)

        return CharacterDTO(character, house)
    }

    private fun simpleFind(id: String): Character {
        return repository.findById(UUID.fromString(id)).orElseThrow { throw EntityNotFoundException("Character with id: $id not found.") }
    }

    @Transactional
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

    fun deleteById(id: String) {
        simpleFind(id)

        repository.deleteById(UUID.fromString(id))
    }
}