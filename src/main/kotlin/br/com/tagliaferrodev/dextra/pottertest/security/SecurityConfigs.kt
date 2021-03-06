package br.com.tagliaferrodev.dextra.pottertest.security

import br.com.tagliaferrodev.dextra.pottertest.util.JWTUtils
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Order(Ordered.HIGHEST_PRECEDENCE)
class SecurityConfigs(private val userDetailsService: UserDetailsServiceImpl,
                      private val jwtUtils: JWTUtils) : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {
        http.cors().and().csrf().disable()

        http.authorizeRequests()
                .antMatchers(HttpMethod.POST, "/login", "/users").permitAll()
                .and()
                .authorizeRequests().anyRequest().authenticated()

        http.addFilter(AuthenticationFilter(jwtUtils, authenticationManager()))
        http.addFilter(AuthorizationFilter(authenticationManager(), jwtUtils, userDetailsService))

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userDetailsService).passwordEncoder(bcryptPasswordEncoder())
    }

    @Bean
    fun bcryptPasswordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun configCors(): CorsConfigurationSource {
        val source = UrlBasedCorsConfigurationSource()
        val cors = CorsConfiguration()
        cors.allowedOrigins = mutableListOf("*")
        cors.allowedMethods = mutableListOf(HttpMethod.GET.name, HttpMethod.POST.name, HttpMethod.PUT.name, HttpMethod.DELETE.name, HttpMethod.OPTIONS.name)
        cors.allowedHeaders = mutableListOf("*")
        cors.allowCredentials = true
        val map = hashMapOf("/**" to cors)
        source.setCorsConfigurations(map)
        return source
    }
}