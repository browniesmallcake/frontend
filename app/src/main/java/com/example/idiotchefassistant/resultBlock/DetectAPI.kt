import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface DetectAPI {
    @Multipart
    @POST("/detect/latest/img")
    fun detect(
        @Part("") image: MultipartBody.Part
    ): Call<HashMap<String,String>>
}
