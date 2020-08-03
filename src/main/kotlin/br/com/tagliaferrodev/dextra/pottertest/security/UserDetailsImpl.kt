package br.com.tagliaferrodev.dextra.pottertest.security

import br.com.tagliaferrodev.dextra.pottertest.user.UserRoles
import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*

class UserDetailsImpl(
        val id: UUID?,
        val nome: String?,
        val email: String?,
        @JsonIgnore
        private val senha: String? = ""
) : UserDetails {

    var token: String = ""

    var tokenExpiration: Long = 0

    @JsonIgnore
    private val autoridades: MutableCollection<out GrantedAuthority> =
            if (email?.contains("admin")!!) {
                UserRoles.values().map { SimpleGrantedAuthority(it.name) }.toMutableList()
            } else {
                mutableListOf(SimpleGrantedAuthority(UserRoles.USER.name))
            }

    override fun getAuthorities() = autoridades

    override fun isEnabled() = true

    override fun getUsername() = email

    override fun isCredentialsNonExpired() = true

    override fun getPassword() = senha

    override fun isAccountNonExpired() = true

    override fun isAccountNonLocked() = true
}