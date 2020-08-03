package br.com.tagliaferrodev.dextra.pottertest.user

import br.com.tagliaferrodev.dextra.pottertest.user.dto.CreateUserDTO
import br.com.tagliaferrodev.dextra.pottertest.util.JWTUtils
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("users")
class UsuarioController(val service: UsuarioService, private val jwtUtils: JWTUtils) {

    @PostMapping
    fun addUser(@RequestBody @Valid usuario: CreateUserDTO): ResponseEntity<Any> {
        val createdSuccessfully = service.save(usuario.fromDTO())

        return if (createdSuccessfully.id != null) {
            val data = object {
                val message = "Usuário criado com sucesso!"
            }
            ResponseEntity(data, HttpStatus.CREATED)
        } else {
            val data = object {
                val message = "Erro ao criar o usuário, tente novamente mais tarde"
            }
            ResponseEntity(data, HttpStatus.BAD_REQUEST)
        }
    }
}
