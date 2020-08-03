package br.com.tagliaferrodev.dextra.pottertest.user

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UsuarioRepository : JpaRepository<Usuario, UUID> {

    fun findByEmail(email: String): Optional<Usuario>
}
