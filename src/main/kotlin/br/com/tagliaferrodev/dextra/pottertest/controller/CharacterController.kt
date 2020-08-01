package br.com.tagliaferrodev.dextra.pottertest.controller

import br.com.tagliaferrodev.dextra.pottertest.integration.PotterClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("characters")
class CharacterController(val service: PotterClient) {

    @GetMapping("teste")
    fun teste() {
        service.getData()
    }
}