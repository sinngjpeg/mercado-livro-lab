package br.com.jpegsinng.mercadolivro.config

import br.com.jpegsinng.mercadolivro.enums.Role
import br.com.jpegsinng.mercadolivro.repository.CustomerRepository
import br.com.jpegsinng.mercadolivro.security.AuthenticationFilter
import br.com.jpegsinng.mercadolivro.security.AuthorizationFilter
import br.com.jpegsinng.mercadolivro.security.CustomAuthenticationEntryPoint
import br.com.jpegsinng.mercadolivro.security.JwtUtil
import br.com.jpegsinng.mercadolivro.service.UserDetailsCustomService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
class SecurityConfig(
    private val userDetailsService: UserDetailsCustomService,
    private val jwtUtil: JwtUtil,
    private val customEntryPoint: CustomAuthenticationEntryPoint,
    private val customerRepository: CustomerRepository
) {

    private val PUBLIC_POST_MATCHERS = arrayOf("/customers")
    private val PUBLIC_GET_MATCHERS = arrayOf("/books")
    private val ADMIN_MATCHERS = arrayOf("/admin/**")

    @Bean
    fun securityFilterChain(
        http: HttpSecurity,
        authenticationManager: AuthenticationManager
    ): SecurityFilterChain {

        val authenticationFilter =
            AuthenticationFilter(authenticationManager, customerRepository, jwtUtil)

        val authorizationFilter =
            AuthorizationFilter(authenticationManager, userDetailsService, jwtUtil)

        http
            .csrf { it.disable() }
            .cors { }
            .sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .exceptionHandling {
                it.authenticationEntryPoint(customEntryPoint)
            }
            .authorizeHttpRequests {
                it.requestMatchers(HttpMethod.POST, *PUBLIC_POST_MATCHERS).permitAll()
                it.requestMatchers(HttpMethod.GET, *PUBLIC_GET_MATCHERS).permitAll()
                it.requestMatchers(*ADMIN_MATCHERS).hasAuthority(Role.ADMIN.description)
                it.anyRequest().authenticated()
            }
            .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
            .addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter::class.java)

        return http.build()
    }

    @Bean
    fun authenticationManager(
        authenticationConfiguration: AuthenticationConfiguration
    ): AuthenticationManager =
        authenticationConfiguration.authenticationManager

    @Bean
    fun passwordEncoder(): PasswordEncoder =
        BCryptPasswordEncoder()

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val config = CorsConfiguration()
        config.allowCredentials = true
        config.addAllowedOriginPattern("*")
        config.addAllowedHeader("*")
        config.addAllowedMethod("*")

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", config)
        return source
    }
}
