package marcelodev.comu_carona.services

import com.google.cloud.firestore.Firestore
import com.google.firebase.cloud.FirestoreClient
import com.google.firebase.cloud.StorageClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import java.util.logging.Logger

@Service
class FirebaseService {
    private val BUCKET_NAME = System.getenv("FIREBASE_STORAGE_BUCKET")
    private val logger = Logger.getLogger(FirebaseService::class.java.name)


    /**
     * Upload a file to Firebase Storage and save the download link in Firestore
     * @param file MultipartFile File to upload
     * @return String? Download URL
     */
    suspend fun uploadFileAndSaveLink(file: MultipartFile): String? {
        return try {
            logger.info("Uploading file: ${file.originalFilename}")
            val fileName = "uploads/" + file.originalFilename
            withContext(Dispatchers.IO) {
                StorageClient.getInstance().bucket().create(fileName, file.bytes, file.contentType)
            }

            val downloadUrl = String.format(
                "https://firebasestorage.googleapis.com/v0/b/%s/o/%s?alt=media", BUCKET_NAME, fileName.replace("/", "%2F")
            )
            logger.info("File uploaded successfully. Download URL: $downloadUrl")

            // Save the link in Firestore
            saveFileLinkToFirestore(file.originalFilename, downloadUrl)

            logger.info("File link saved successfully")
            downloadUrl
        } catch (e: IOException) {
            logger.severe("Error uploading file: ${e.message}")
            null
        }
    }

    /**
     * Save file link to Firestore
     * @param fileName String? File name
     * @param downloadUrl String Download URL
     */
    private suspend fun saveFileLinkToFirestore(fileName: String?, downloadUrl: String) {
        val firestore: Firestore = FirestoreClient.getFirestore()

        val fileData: MutableMap<String, Any?> = HashMap()
        fileData["fileName"] = fileName
        fileData["fileUrl"] = downloadUrl

        try {
            logger.info("Saving file link: $fileName")
            withContext(Dispatchers.IO) {
                firestore.collection("files").document(fileName!!).set(fileData)
            }
            logger.info("File link saved successfully")
        } catch (e: Exception) {
            System.err.println("Error saving file link: " + e.message)
        }
    }
}