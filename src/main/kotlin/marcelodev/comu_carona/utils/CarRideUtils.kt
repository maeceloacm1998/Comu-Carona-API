package marcelodev.comu_carona.utils

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

object CarRideUtils {
    private const val DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss"
    private const val OUTPUT_DATE_TIME_FORMAT = "EEEE, d 'de' MMMM"
    private const val LOCALE = "pt"
    private const val LOCALE_BR = "BR"

    fun formatDateTime(dateTime: String): String {
        val inputFormatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)
        val outputFormatter = DateTimeFormatter.ofPattern(OUTPUT_DATE_TIME_FORMAT, Locale(LOCALE, LOCALE_BR))
        val date = LocalDateTime.parse(dateTime, inputFormatter)
        return date.format(outputFormatter)
    }

    fun createDescription(
        totalSeats: Int,
        waitingAddress: String,
        destinationAddress: String,
        waitingHour: String,
        destinationHour: String
    ): String {
        return "Temos no total $totalSeats vagas disponíveis para a carona de ${waitingAddress} para ${destinationAddress}.\n" +
                "Horário de saída: ${waitingHour}.\n" +
                "Horário de chegada: ${destinationHour}.\n\n" +
                "Caso tenha dúvidas, entre em contato pelo whatsapp, clicando nos detalhes do motorista a cima."
    }

    fun createRideDescription(
        carModel: String,
        carColor: String,
        carPlate: String,
    ): String {
        return "Placa: $carPlate - $carModel | $carColor"
    }
}