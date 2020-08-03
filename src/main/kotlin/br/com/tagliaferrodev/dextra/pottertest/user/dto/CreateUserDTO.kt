package br.com.tagliaferrodev.dextra.pottertest.user.dto

import br.com.tagliaferrodev.dextra.pottertest.user.Usuario
import br.com.tagliaferrodev.dextra.pottertest.util.FromDTO
import javax.validation.constraints.Email
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

data class CreateUserDTO(
        @field:NotNull
        @field:Size(min = 1, max = 100)
        val nome: String? = null,

        @field:NotNull
        @field:Email
        @field:Size(min = 1, max = 255)
        val email: String? = null,

        @field:NotNull
        @field:Size(min = 3, max = 255)
        val senha: String? = null) : FromDTO<Usuario> {

    override fun fromDTO(): Usuario {
        return Usuario(
                nome = nome,
                email = email,
                senha = senha
        )
    }
}