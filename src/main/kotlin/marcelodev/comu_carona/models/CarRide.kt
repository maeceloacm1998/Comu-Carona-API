package marcelodev.comu_carona.models

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "car_ride")
data class CarRide(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,
    @Column(name = "rider_id")
    var riderId: String = "",
    var createdAt: LocalDateTime = LocalDateTime.now(),
    var carModel: String = "",
    var carColor: String = "",
    var carPlate: String = "",
    var quantitySeats: Int = 0,
    var waitingAddress: String = "",
    var destinationAddress: String = "",
    var waitingHour: String = "",
    var destinationHour: String = "",
    var status: String = "",
    var isTwoPassengersBehind: Boolean = false,

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "rider_id", referencedColumnName = "user_id", insertable = false, updatable = false)
    var user: User,
)