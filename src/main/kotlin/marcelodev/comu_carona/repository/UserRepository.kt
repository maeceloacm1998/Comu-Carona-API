package marcelodev.comu_carona.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import marcelodev.comu_carona.models.User

@Repository
interface UserRepository : JpaRepository<User?, Long?> {

    @Query("SELECT u FROM User u WHERE u.userId =:userName")
    fun findByUserId(@Param("userName") userId: String?): User?
}