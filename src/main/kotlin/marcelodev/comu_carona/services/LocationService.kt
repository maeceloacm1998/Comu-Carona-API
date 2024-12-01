package marcelodev.comu_carona.services

import com.google.maps.GeoApiContext
import com.google.maps.PlaceAutocompleteRequest.*
import com.google.maps.PlacesApi
import com.google.maps.model.LatLng
import com.google.maps.model.PlaceAutocompleteType
import org.springframework.stereotype.Service
import java.util.logging.Logger

@Service
class LocationService(
    private val context: GeoApiContext
) {
    private val logger = Logger.getLogger(LocationService::class.java.name)

    fun autoCompleteAddress(address: String): List<String> {
        try {
            logger.info("Auto complete address: $address")

            val sessionToken = SessionToken()
            logger.info("Session token: $sessionToken")
            val request =
                PlacesApi.placeAutocomplete(context, address, sessionToken)
                    .location(LatLng(-19.9167, -43.9345))
                    .radius(5000)
                    .types(PlaceAutocompleteType.ADDRESS)
            logger.info("Request: $request")
            val response = request.await()

            logger.info("Response: $response")
            return response.map { it.description }
        } catch (e: Exception) {
            logger.severe("Error during autocomplete request: ${e.message}")
            throw e
        }
    }
}