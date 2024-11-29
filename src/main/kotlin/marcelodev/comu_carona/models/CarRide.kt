package marcelodev.comu_carona.models

import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "car_ride")
data class CarRide(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,
    @Column(name = "rider_id")
    var riderId: String = "",
    var createdAt: LocalDateTime? = null,
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

    @Column(name = "uuid", unique = true, nullable = false, updatable = false)
    var uuid: String? = null,

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "rider_id", referencedColumnName = "user_id", insertable = false, updatable = false)
    var user: User,

    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    @JoinColumn(name = "car_rider_uuid", referencedColumnName = "uuid", insertable = false, updatable = false)
    var reservations: MutableList<ReservationCarRide> = mutableListOf()
) {
    @PrePersist
    fun prePersist() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now()
        }
        if (uuid.isNullOrBlank()) {
            uuid = UUID.randomUUID().toString()
        }
    }
}