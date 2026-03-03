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
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class SecurityConfig(
    private val customerRepository: CustomerRepository,
    private val userDetails: UserDetailsCustomService,
    private val jwtUtil: JwtUtil,
    private val customEntryPoint: CustomAuthenticationEntryPoint,
    private val authenticationConfiguration: AuthenticationConfiguration
) {

    // Adicionado o singular para evitar o erro de antes
    private val PUBLIC_POST_MATCHERS = arrayOf("/customers", "/customer")
    private val PUBLIC_GET_MATCHERS = arrayOf("/books")
    private val ADMIN_MATCHERS = arrayOf("/admin/**")

    @Bean
    fun bCryptPasswordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun authenticationManager(): org.springframework.security.authentication.AuthenticationManager {
        return authenticationConfiguration.authenticationManager
    }

    @Bean
    fun filterChain(http: HttpSecurity): org.springframework.security.web.SecurityFilterChain {
        http
            .cors { it.disable() }
            .csrf { it.disable() }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .exceptionHandling { it.authenticationEntryPoint(customEntryPoint) }
            .authorizeHttpRequests { auth ->
                auth
                    .requestMatchers(HttpMethod.POST, *PUBLIC_POST_MATCHERS).permitAll()
                    .requestMatchers(HttpMethod.GET, *PUBLIC_GET_MATCHERS).permitAll()
                    // IMPORTANTE: Liberar o login aqui explicitamente
                    .requestMatchers("/login").permitAll()
                    .requestMatchers(*ADMIN_MATCHERS).hasAuthority(Role.ADMIN.description)
                    .anyRequest().authenticated()
            }

        // --- AJUSTE NOS FILTROS ---
        val authFilter = AuthenticationFilter(authenticationManager(), customerRepository, jwtUtil)
        // Esta linha abaixo é o que faz o JWT ser gerado na rota certa!
        authFilter.setFilterProcessesUrl("/login")

        http.addFilter(authFilter)
        http.addFilter(AuthorizationFilter(authenticationManager(), userDetails, jwtUtil))

        return http.build()
    }

    @Bean
    fun webSecurityCustomizer(): org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer {
        return org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer { web ->
            web.ignoring().requestMatchers(
                "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui/index.html"
            )
        }
    }
}