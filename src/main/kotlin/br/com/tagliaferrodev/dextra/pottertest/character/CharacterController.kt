package br.com.tagliaferrodev.dextra.pottertest.character

import br.com.tagliaferrodev.dextra.pottertest.character.domain.Character
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("characters")
class CharacterController(val service: CharacterService) {

    @PostMapping
    fun add(@RequestBody @Valid character: Character): ResponseEntity<Character> {
        return ResponseEntity.ok(service.save(character))
    }
}