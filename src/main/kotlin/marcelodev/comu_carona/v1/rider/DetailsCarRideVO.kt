package marcelodev.comu_carona.v1.rider

import marcelodev.comu_carona.mapper.CustomMapper
import marcelodev.comu_carona.models.CarRide
import marcelodev.comu_carona.utils.CarRideUtils.createDescription
import marcelodev.comu_carona.utils.CarRideUtils.createRideDescription
import marcelodev.comu_carona.utils.CarRideUtils.formatBirthDate
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
    val reservations: List<ReservationCarRideVO> = mutableListOf(),
    val bottomSheetCarRideUser: BottomSheetCarRideUserVO,
    // FALTA IMPLEMENTAR VALIDACAO SE EXISTE VAGA
)

fun CarRide.parseRideToDetailsCarRideVO(customMapper: CustomMapper): DetailsCarRideVO {
    val reservations = customMapper.parseListObject(this.reservations, ReservationCarRideVO::class.java)

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
        reservations = reservations.map { it.birthDate = formatBirthDate(it.birthDate); it },
        bottomSheetCarRideUser = BottomSheetCarRideUserVO(
            bottomSheetRiderUsername = this.user.username,
            bottomSheetRiderDescription = formatBirthDate(this.user.getBirthDate()),
            bottomSheetRiderPhoneNumber = this.user.getPhoneNumber()
        )
    )
}