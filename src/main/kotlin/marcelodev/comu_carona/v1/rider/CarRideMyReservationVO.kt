package marcelodev.comu_carona.v1.rider

import marcelodev.comu_carona.mapper.CustomMapper
import marcelodev.comu_carona.models.CarRide
import marcelodev.comu_carona.models.enums.CarRideState.Companion.fromValue
import marcelodev.comu_carona.models.enums.CarRideState.MY_RIDE

data class CarRideMyReservationVO(
    var uuid: String? = "",
    var waitingAddress: String? = "",
    var destinationAddress: String? = "",
    var waitingHour: String? = "",
    var destinationHour: String? = "",
    var states: List<String> = emptyList(),
    var riderInformation: CarRideReservationVO = CarRideReservationVO(),
    var isMyCarRide: Boolean = false
)

fun CarRide.parseRideToCarRideMyReservationVO(userId: String, customMapper: CustomMapper): CarRideMyReservationVO {
    val states: MutableList<String> = mutableListOf(fromValue(this.status).value)
    val isMyCarRide = this.user.getUserId() == userId
    if (isMyCarRide) {
        states.add(MY_RIDE.value)
    }

    return CarRideMyReservationVO(
        uuid = this.uuid,
        waitingAddress = this.waitingAddress,
        destinationAddress = this.destinationAddress,
        waitingHour = this.waitingHour,
        destinationHour = this.destinationHour,
        states = states,
        riderInformation = CarRideReservationVO(
            photoUrl = this.user.getPhotoUrl(),
            birthDate = this.user.getBirthDate(),
            phoneNumber = this.user.getPhoneNumber(),
            username = this.user.username
        ),
        isMyCarRide = isMyCarRide
    )
}