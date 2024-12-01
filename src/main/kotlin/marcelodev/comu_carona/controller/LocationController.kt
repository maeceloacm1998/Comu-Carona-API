package marcelodev.comu_carona.controller

import com.google.maps.model.AutocompletePrediction
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import marcelodev.comu_carona.services.LocationService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/location/v1")
@Tag(name = "Location", description = "End-points to location entity")
class LocationController(
    private val locationService: LocationService
) {

    @Operation(
        summary = "Auto complete address", description = "Auto complete address", responses = [
            ApiResponse(
                responseCode = "200",
                description = "Auto complete address",
                content = [
                    Content(
                        mediaType = "application/json",
                        array = ArraySchema(schema = Schema(implementation = AutocompletePrediction::class))
                    )
                ]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Invalid data, error when trying to auto complete address",
            ),
            ApiResponse(
                responseCode = "401",
                description = "Unauthorized",
            ),
        ]
    )
    @PostMapping("/auto-complete-address")
    fun autoCompleteAddress(
        @Parameter(description = "Input address to autocomplete", required = true)
        @RequestParam address: String
    ): ResponseEntity<*> {
        val autoCompleteAddress = locationService.autoCompleteAddress(address)
        return ResponseEntity.ok(autoCompleteAddress)
    }
}