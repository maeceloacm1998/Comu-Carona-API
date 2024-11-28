package marcelodev.comu_carona.v1.rider

import marcelodev.comu_carona.models.CarRide
import marcelodev.comu_carona.utils.CarRideUtils.createDescription
import marcelodev.comu_carona.utils.CarRideUtils.createRideDescription
import marcelodev.comu_carona.utils.CarRideUtils.formatDateTime

data class DetailsCarRideVO(
    val id: String = "",
    var dateTitle: String = "",
    var description: String = "",
    var riderUsername: String = "",
    var riderDescription: String = "",
    var waitingAddress: String? = "",
    var destinationAddress: String? = "",
    var waitingHour: String? = "",
    var destinationHour: String? = "",
    // FALTA IMPLEMENTAR VALIDACAO SE EXISTE VAGA
)

fun CarRide.parseRideToDetailsCarRideVO(): DetailsCarRideVO {
    return DetailsCarRideVO(
        id = this.uuid!!,
        dateTitle = formatDateTime(this.createdAt.toString()),
        description = createDescription(
            waitingHour = this.waitingHour,
            waitingAddress = this.waitingAddress,
            destinationHour = this.destinationHour,
            destinationAddress = this.destinationAddress,
            totalSeats = this.quantitySeats
        ),
        riderUsername = this.user.username,
        riderDescription = createRideDescription(
            carModel = this.carModel,
            carColor = this.carColor,
            carPlate = this.carPlate,
        ),
        waitingAddress = this.waitingAddress,
        destinationAddress = this.destinationAddress,
        waitingHour = this.destinationHour,
        destinationHour = this.destinationHour,
    )
}