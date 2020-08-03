package br.com.tagliaferrodev.dextra.pottertest.security

import br.com.tagliaferrodev.dextra.pottertest.error.ApiError
import br.com.tagliaferrodev.dextra.pottertest.error.exceptions.UnauthorizedException
import br.com.tagliaferrodev.dextra.pottertest.user.UserRoles
import br.com.tagliaferrodev.dextra.pottertest.user.dto.AuthDTO
import br.com.tagliaferrodev.dextra.pottertest.util.JWTUtils
import br.com.tagliaferrodev.dextra.pottertest.util.SimpleResponseSender
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AuthenticationFilter(private val jwtUtils: JWTUtils,
                           private val authManager: AuthenticationManager) : UsernamePasswordAuthenticationFilter(), AuthenticationFailureHandler {

    override fun onAuthenticationFailure(request: HttpServletRequest?, response: HttpServletResponse?, exception: AuthenticationException?) {
        val error = ApiError(HttpStatus.UNAUTHORIZED, "Usuário e/ou senha inválidos.")

        SimpleResponseSender.send(response, error)
    }

    override fun attemptAuthentication(request: HttpServletRequest?, response: HttpServletResponse?): Authentication {
        val credentials = ObjectMapper().readValue(request?.inputStream, AuthDTO::class.java)

        val authorities = if (credentials.username.contains("admin")) {
            UserRoles.values().map { SimpleGrantedAuthority(it.name) }
        } else {
            listOf(SimpleGrantedAuthority(UserRoles.USER.name))
        }

        val authToken = UsernamePasswordAuthenticationToken(credentials.username, credentials.password, authorities)

        setAuthenticationFailureHandler(this)

        return authManager.authenticate(authToken)
    }

    override fun successfulAuthentication(
            request: HttpServletRequest?,
            response: HttpServletResponse?,
            chain: FilterChain?,
            authResult: Authentication?) {

        val user = authResult?.principal as UserDetailsImpl

        user.token = jwtUtils.generateToken(user)
        user.tokenExpiration = jwtUtils.getTokenExpiration(user.token)

        if (user.tokenExpiration == 0L) {
            throw UnauthorizedException("Houve um problema ao gerar o token de autenticação")
        }

        SimpleResponseSender.send(response, HttpStatus.OK, user)
    }

    override fun unsuccessfulAuthentication(
            request: HttpServletRequest?,
            response: HttpServletResponse?,
            failed: AuthenticationException?
    ) {
        SecurityContextHolder.clearContext()
        failureHandler.onAuthenticationFailure(request, response, failed)
    }
}
