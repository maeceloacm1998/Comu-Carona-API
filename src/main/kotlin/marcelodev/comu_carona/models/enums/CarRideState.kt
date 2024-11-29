package marcelodev.comu_carona.models.enums

import marcelodev.comu_carona.exceptions.InvalidCarRideStateException

enum class CarRideState(
    val value: String
) {
    MY_RIDE("Minha carona"),
    IN_PROGRESS("Em andamento"),
    CANCELED("Cancelado"),
    FINISHED("Concluído");

    companion object {
        fun fromValue(value: String): CarRideState {
            return when(value) {
                MY_RIDE.toString() -> MY_RIDE
                IN_PROGRESS.toString() -> IN_PROGRESS
                CANCELED.toString() -> CANCELED
                FINISHED.toString() -> FINISHED
                else -> throw InvalidCarRideStateException("Invalid car ride state")
            }
        }
    }
}