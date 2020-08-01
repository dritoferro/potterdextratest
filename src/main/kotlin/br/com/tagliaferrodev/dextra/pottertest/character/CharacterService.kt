package br.com.tagliaferrodev.dextra.pottertest.character

import br.com.tagliaferrodev.dextra.pottertest.character.domain.Character
import br.com.tagliaferrodev.dextra.pottertest.integration.PotterClient
import org.springframework.stereotype.Service

@Service
class CharacterService(private val repository: CharacterRepository,
                       private val partner: PotterClient) {

    fun save(character: Character): Character {
        partner.findByHouseId(character.house!!)

        return repository.save(character)
    }
}