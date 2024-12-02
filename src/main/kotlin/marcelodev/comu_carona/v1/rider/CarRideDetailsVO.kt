package marcelodev.comu_carona.v1.rider

import marcelodev.comu_carona.mapper.CustomMapper
import marcelodev.comu_carona.models.CarRide
import marcelodev.comu_carona.utils.CarRideUtils
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
    var isFullSeats: Boolean = false,
    var reservations: List<CarRideReservationVO> = mutableListOf(),
    var shareDeeplink: String = "",
    var bottomSheetCarRideUser: CarRideUserBottomSheetVO = CarRideUserBottomSheetVO(),
)

fun CarRide.parseRideToDetailsCarRideVO(customMapper: CustomMapper): DetailsCarRideVO {
    val reservations = customMapper.parseListObject(this.reservations, CarRideReservationVO::class.java)

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
        isFullSeats = this.quantitySeats == reservations.size,
        reservations = reservations.map {
            it.photoUrl = this.user.getPhotoUrl()
            it.birthDate = formatBirthDate(it.birthDate); it
        },
        shareDeeplink = CarRideUtils.createShareCarRide(
            id = this.uuid!!,
            riderUserName = this.user.username,
            waitingAddress = this.waitingAddress,
            destinationAddress = this.destinationAddress,
            waitingHour = this.waitingHour,
            destinationHour = this.destinationHour,
        ),
        bottomSheetCarRideUser = CarRideUserBottomSheetVO(
            bottomSheetRiderPlate = this.carPlate,
            bottomSheetRiderUsername = this.user.username,
            bottomSheetRiderDescription = formatBirthDate(this.user.getBirthDate()),
            bottomSheetRiderPhoto = this.user.getPhotoUrl(),
            bottomSheetCarRiderDescription = createRideDescription(
                carModel = this.carModel,
                carColor = this.carColor,
                carPlate = this.carPlate,
            ),
            bottomSheetRiderPhoneNumber = this.user.getPhoneNumber()
        )
    )
}