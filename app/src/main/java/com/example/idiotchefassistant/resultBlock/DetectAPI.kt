import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface DetectAPI {
    @Multipart
    @POST("/detect/latest/video")
    fun detect(
        @Part("") video: MultipartBody.Part
    ): Call<HashMap<String,ArrayList<String>>>
}
