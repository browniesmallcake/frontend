import okhttp3.RequestBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface DetectAPI {
    @Multipart
    @POST("/detect/latest/video")
    fun detect(
        @Part("") video: RequestBody
    )
}
