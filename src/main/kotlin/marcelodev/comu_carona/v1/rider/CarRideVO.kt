package marcelodev.comu_carona.v1.rider

data class CarRideVO (
    var carModel: String? = "",
    var carColor: String? = "",
    var carPlate: String? = "",
    var quantitySeats: Int? = 0,
    var waitingAddress: String? = "",
    var destinationAddress: String? = "",
    var waitingHour: String? = "",
    var destinationHour: String? = "",
    var status: String? = "",
    var isTwoPassengersBehind: Boolean? = false
)