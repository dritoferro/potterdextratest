package br.com.tagliaferrodev.dextra.pottertest.character

import br.com.tagliaferrodev.dextra.pottertest.character.domain.Character
import br.com.tagliaferrodev.dextra.pottertest.character.domain.CharacterDTO
import br.com.tagliaferrodev.dextra.pottertest.character.domain.CharactersDTO
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("characters")
class CharacterController(val service: CharacterService) {

    @PostMapping
    fun add(@RequestBody @Valid character: Character): ResponseEntity<Character> {
        return ResponseEntity(service.save(character), HttpStatus.CREATED)
    }

    @GetMapping("{id}")
    fun getCharacterById(@PathVariable id: String): ResponseEntity<CharacterDTO> {
        return ResponseEntity.ok(service.findById(id))
    }

    @GetMapping
    fun getCharactersByAttribute(@RequestParam("name") name: String?,
                                 @RequestParam("house") house: String?): ResponseEntity<List<CharactersDTO>> {
        return ResponseEntity.ok(service.findByAttributes(name, house))
    }
}