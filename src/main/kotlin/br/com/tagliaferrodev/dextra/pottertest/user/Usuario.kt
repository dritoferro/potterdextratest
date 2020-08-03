package br.com.tagliaferrodev.dextra.pottertest.user

import com.fasterxml.jackson.annotation.JsonIgnore
import java.util.*
import javax.persistence.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Entity
@Table(name = "users")
data class Usuario(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        val id: UUID? = null,

        @field:NotNull
        @field:Size(min = 1, max = 100)
        val nome: String? = null,

        @field:NotNull
        @field:Column(unique = true)
        @field:Email
        @field:Size(min = 3, max = 255)
        val email: String? = null,

        @field:NotNull
        @JsonIgnore
        var senha: String? = null
)
