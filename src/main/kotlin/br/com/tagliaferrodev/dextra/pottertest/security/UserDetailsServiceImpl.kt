package br.com.tagliaferrodev.dextra.pottertest.security

import br.com.tagliaferrodev.dextra.pottertest.error.exceptions.UnauthorizedException
import br.com.tagliaferrodev.dextra.pottertest.user.UsuarioService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl(private val service: UsuarioService) : UserDetailsService {

    override fun loadUserByUsername(username: String?): UserDetails {
        if (username.isNullOrBlank()) {
            throw UnauthorizedException("Usuário não pode estar em branco")
        }
        val usuario = service.findForLogin(username)

        return UserDetailsImpl(
                id = usuario.id,
                nome = usuario.nome,
                email = usuario.email,
                senha = usuario.senha
        )
    }
}