package br.com.tagliaferrodev.dextra.pottertest.user

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*
import javax.persistence.EntityNotFoundException

@Service
class UsuarioService(private val repository: UsuarioRepository) {

    private fun getEnconder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Transactional
    fun save(usuario: Usuario): Usuario {
        usuario.senha = getEnconder().encode(usuario.senha)
        repository.save(usuario)

        return usuario
    }

    @Transactional
    fun findById(id: UUID) = repository.findById(id).orElseThrow { throw EntityNotFoundException("Usuário não encontrado") }

    @Transactional
    fun findForLogin(field: String) = repository.findByEmail(field).orElseThrow { throw EntityNotFoundException("Usuário não encontrado") }
}
