package marcelodev.comu_carona.models.enums

enum class CarRideState(
    val value: String
) {
    MY_RIDE("Minha carona"),
    IN_PROGRESS("Em andamento"),
    CANCELED("Cancelado"),
    FINISHED("Concluído"),
}