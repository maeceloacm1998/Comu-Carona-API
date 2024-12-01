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
import marcelodev.comu_carona.v1.auth.AccountCredentialsVO
import marcelodev.comu_carona.v1.auth.TokenVO
import marcelodev.comu_carona.v1.auth.UpdateRegisterVO
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

    fun signin(data: AccountCredentialsVO): ResponseEntity<*> {
        logger.info("Trying log user ${data.username}")

        return try {
            val codeEnvironment = System.getenv("ENTER_CODE")
            val username = data.username
            val password = data.username

            if (data.code?.equals(codeEnvironment) == false) {
                logger.warning("Invalid environment code")
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Código de ambiente inválido")
            }

            val tokenResponse = authUser(username!!, password!!)
            logger.info("User logged successfully")
            ResponseEntity.ok(tokenResponse)
        } catch (e: AuthenticationException) {
            throw BadCredentialsException("Username ou Password não existem!")
        }
    }

    private fun authUser(username: String, password: String): TokenVO {
        authenticationManager.authenticate(UsernamePasswordAuthenticationToken(username, password))
        val user = repository.findByUserId(username)
        val tokenResponse: TokenVO = if (user != null) {
            tokenProvider.createAccessToken(username, user.roles)
        } else {
            throw UsernameNotFoundException("Username $username not found!")
        }

        return tokenResponse
    }

    fun refreshToken(username: String, refreshToken: String): ResponseEntity<*> {
        logger.info("Trying get refresh token to user $username")

        val user = repository.findByUserId(username)
        val tokenResponse: TokenVO = if (user != null) {
            tokenProvider.refreshToken(refreshToken)
        } else {
            throw UsernameNotFoundException("Username $username not found!")
        }
        return ResponseEntity.ok(tokenResponse)
    }

    fun createUser(data: UpdateRegisterVO, username: String): ResponseEntity<*> {
        logger.info("Trying create user")
        val user = repository.findByUserId(username)

        if (user != null) {
            logger.warning("Username already exists")
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists")
        }

        val newUser = createUserWithData(username, data)
        repository.save(newUser)

        val tokenResponse = authUser(username, username)

        logger.info("User created successfully, logged in")
        logger.info("the token is: ${tokenResponse.accessToken}")
        return ResponseEntity.ok(tokenResponse)
    }

    fun updatePhotoImageUrl(username: String, photoUrl: String): ResponseEntity<*> {
        logger.info("Trying update photo url to user $username")

        val user = repository.findByUserId(username)
        if (user == null) {
            logger.warning("Username not found")
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Username not found")
        }

        user.setPhotoUrl(photoUrl)
        repository.save(user)

        logger.info("Photo url updated successfully")
        return ResponseEntity.ok().body("Photo url updated successfully")
    }

    private fun createUserWithData(username: String, data: UpdateRegisterVO): User {
        val encryptedPassword = passwordEncoder.encode(username)
        val user = User().apply {
            setUserId(username)
            setUsername(data.fullName!!)
            setBirthDate(data.birthDate!!)
            setPhoneNumber(data.phoneNumber!!)
            setPhotoUrl(data.photoUrl!!)
            this.password = encryptedPassword
            this.accountNonExpired = true
            this.accountNonLocked = true
            this.credentialsNonExpired = true
            this.enabled = true
            this.permissions = mutableListOf()
        }

        return user
    }
}