package br.com.tagliaferrodev.dextra.pottertest.character.domain

import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Entity
@Table(name = "characters")
data class Character(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        val id: UUID,

        @field:NotNull
        @field:Size(min = 2, max = 50)
        val name: String? = null,

        @field:NotNull
        @Enumerated(EnumType.STRING)
        val role: Role? = null,

        val school: String? = "Hogwarts School of Witchcraft and Wizardry",

        @field:NotNull
        val house: String? = null,

        val patronus: String? = null
)
