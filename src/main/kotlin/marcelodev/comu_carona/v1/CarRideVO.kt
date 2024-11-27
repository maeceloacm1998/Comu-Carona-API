package marcelodev.comu_carona.v1

data class CarRideVO (
    var carModel: String = "",
    var carColor: String = "",
    var carPlate: String = "",
    var quantitySeats: Int = 0,
    var address: String = "",
    var hour: String = "",
    var status: String = "",
    var isTwoPassengersBehind: Boolean = false
)