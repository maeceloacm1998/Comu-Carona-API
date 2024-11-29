package marcelodev.comu_carona.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.BAD_REQUEST)
class InvalidCarRideStateException(message: String?): RuntimeException(message)