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
    // Injetamos a configuração para obter o AuthenticationManager
    private val authenticationConfiguration: AuthenticationConfiguration
) {

    private val PUBLIC_POST_MATCHERS = arrayOf("/customers")
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
                    .requestMatchers(*ADMIN_MATCHERS).hasAuthority(Role.ADMIN.description)
                    .anyRequest().authenticated()
            }

        // Configuração dos Filtros
        http.addFilter(AuthenticationFilter(authenticationManager(), customerRepository, jwtUtil))
        http.addFilter(AuthorizationFilter(authenticationManager(), userDetails, jwtUtil))

        return http.build()
    }

    @Bean
    fun webSecurityCustomizer(): org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer {
        return org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer { web ->
            web.ignoring().requestMatchers( // Também mudou aqui para requestMatchers
                "/v2/api-docs", "/swagger-resources/**", "/swagger-ui.html", "/webjars/**"
            )
        }
    }
}