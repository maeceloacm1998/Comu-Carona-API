package marcelodev.comu_carona.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import marcelodev.comu_carona.v1.AccountCredentialsVO
import marcelodev.comu_carona.v1.TokenVO
import marcelodev.comu_carona.v1.UpdateRegisterVO
import marcelodev.comu_carona.models.User
import marcelodev.comu_carona.repository.UserRepository
import marcelodev.comu_carona.jwt.JwtTokenProvider
import java.util.logging.Logger

@Service
class AuthService {

    @Autowired
    private lateinit var authenticationManager: AuthenticationManager

    @Autowired
    private lateinit var tokenProvider: JwtTokenProvider

    @Autowired
    private lateinit var repository: UserRepository

    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder


    private val logger = Logger.getLogger(AuthService::class.java.name)

    fun signin(data: AccountCredentialsVO) : ResponseEntity<*> {
        logger.info("Trying log user ${data.username}")
        return try {
            val username = data.username
            val password = data.password
            authenticationManager.authenticate(UsernamePasswordAuthenticationToken(username, password))
            val user = repository.findByUsername(username)
            val tokenResponse: TokenVO = if (user != null) {
                tokenProvider.createAccessToken(username!!, user.roles)
            } else {
                throw UsernameNotFoundException("Username $username not found!")
            }
            ResponseEntity.ok(tokenResponse)
        } catch (e: AuthenticationException) {
            throw BadCredentialsException("Invalid username or password supplied!")
        }
    }

    fun refreshToken(username: String, refreshToken: String) : ResponseEntity<*> {
        logger.info("Trying get refresh token to user $username")

        val user = repository.findByUsername(username)
        val tokenResponse: TokenVO = if (user != null) {
            tokenProvider.refreshToken(refreshToken)
        } else {
            throw UsernameNotFoundException("Username $username not found!")
        }
        return ResponseEntity.ok(tokenResponse)
    }

    fun createUser(data: AccountCredentialsVO): ResponseEntity<*> {
        val user = repository.findByUsername(data.username)

        if (user != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists")
        }

        val encryptedPassword = passwordEncoder.encode(data.password)
        val newUser = User().apply {
            userName = data.username
            password = encryptedPassword
            accountNonExpired = true
            accountNonLocked = true
            credentialsNonExpired = true
            enabled = true
            permissions = mutableListOf() // Adicione permissões conforme necessário
        }

        repository.save(newUser)
        return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully")
    }

    fun updateRegister(data: UpdateRegisterVO, username: String): ResponseEntity<*> {
        logger.info("Trying update user $username")
        val user = repository.findByUsername(username)
            ?: return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found")

        user.apply {
            setFullName(data.fullName!!)
            setBirthDate(data.birthDate!!)
            setPhoneNumber(data.phoneNumber!!)
            setPhotoUrl(data.photoUrl!!)
        }

        repository.save(user)
        return ResponseEntity.status(HttpStatus.OK).body("User updated successfully")
    }
}