package marcelodev.comu_carona.config

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import jakarta.annotation.PostConstruct
import org.springframework.context.annotation.Configuration
import java.io.FileInputStream


@Configuration
class FirebaseConfig {
    @PostConstruct
    fun initializeFirebase() {
        try {
            val serviceAccount =
                FileInputStream("src/main/resources/firebase/comu-carona-firebase.json")

            val options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setStorageBucket(System.getenv("FIREBASE_STORAGE_BUCKET"))
                .build()

            FirebaseApp.initializeApp(options)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}