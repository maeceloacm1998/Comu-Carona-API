package marcelodev.comu_carona.models

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
data class CarRide(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    var userId: String? = null,

    var createdAt: LocalDateTime = LocalDateTime.now(),

    var carModel: String? = null,

    var carColor: String? = null,

    var carPlate: String? = null,

    var quantitySeats: Int? = null,

    var address: String? = null,

    var hour: String? = null,

    var status: String? = null,

    var isTwoPassengersBehind: Boolean = false,
)