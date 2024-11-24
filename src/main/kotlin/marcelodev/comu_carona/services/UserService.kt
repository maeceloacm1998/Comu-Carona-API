package marcelodev.comu_carona.services

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import marcelodev.comu_carona.repository.UserRepository
import java.util.logging.Logger

@Service
class UserService(
    private val userRepository: UserRepository
): UserDetailsService {
    private val logger = Logger.getLogger(ExampleService::class.java.name)

    override fun loadUserByUsername(username: String?): UserDetails {
        logger.info("Finding User by id: $username")

        val user = userRepository.findByUsername(username)
            ?: throw UsernameNotFoundException("No records found for this ID")

        return user
    }

}