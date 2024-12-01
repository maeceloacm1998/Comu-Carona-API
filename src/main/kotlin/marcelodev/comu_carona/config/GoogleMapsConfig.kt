package marcelodev.comu_carona.config

import com.google.maps.GeoApiContext
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class GoogleMapsConfig {
    @Value("\${google.maps.api.key}")
    private val apiKey: String? = null

    @Bean
    fun geoApiContext(): GeoApiContext {
        return GeoApiContext.Builder()
            .apiKey(apiKey)
            .build()
    }
}