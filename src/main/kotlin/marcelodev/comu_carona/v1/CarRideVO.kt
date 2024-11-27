package marcelodev.comu_carona.v1

data class CarRideVO (
    val carModel: String? = null,
    val carColor: String? = null,
    val carPlate: String? = null,
    val quantitySeats: Int? = null,
    val address: String? = null,
    val hour: String? = null,
    val isTwoPassengersBehind: Boolean = false,
)