package marcelodev.comu_carona.v1.auth

data class UpdateRegisterVO (
    val fullName: String? = null,
    val birthDate: String? = null,
    val phoneNumber: String? = null,
    val photoUrl: String? = null,
)