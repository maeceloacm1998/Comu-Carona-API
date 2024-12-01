package marcelodev.comu_carona.v1.rider

import marcelodev.comu_carona.models.CarRide

data class AvailableCarRidesVO(
    val id: String,
    val waitingAddress: String,
    val destinationAddress: String,
    val waitingHour: String,
    val destinationHour: String,
    val riderPhotoUrl: String,
    val riderUserName: String,
    val carModel: String,
    val carColor: String,
)

fun CarRide.parseRideToAvailableCarRidesVO(): AvailableCarRidesVO {
    return AvailableCarRidesVO(
        id = this.uuid!!,
        waitingAddress = this.waitingAddress,
        destinationAddress = this.destinationAddress,
        waitingHour = this.destinationHour,
        destinationHour = this.destinationHour,
        riderPhotoUrl = this.user.getPhotoUrl(),
        riderUserName = this.user.username,
        carModel = this.carModel,
        carColor = this.carColor
    )
}