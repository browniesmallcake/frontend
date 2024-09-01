package com.example.idiotchefassistant.resultBlock

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class ResultViewModel(private var resultRepository: ResultRepository): ViewModel() {
    private var userLiveData = MutableLiveData<ResultData>()

    fun callBack():LiveData<ResultData>{
        resultRepository.loadResult(object: OnTaskFinish{
            override fun onFinish(data: ResultData){
                userLiveData.postValue(data)
            }
        })
        return userLiveData
    }

    fun upload(video: String?){
        // upload video
        val videoFile = File(video.toString())
        val requestFile = RequestBody.create(MultipartBody.FORM, videoFile)
        val fbody = MultipartBody.Part.createFormData("video", videoFile.name, requestFile)
        detectService.detect(fbody).enqueue(object : Callback<HashMap<String, ArrayList<String>>> {
            override fun onResponse(call: Call<HashMap<String, ArrayList<String>>>, response: Response<HashMap<String, ArrayList<String>>>) {
                if(response.isSuccessful) {
                    val map = response.body()?.keys
                    Log.i("onResponse2","OK")
                    var keyList = arrayOf<String>()
                    if(map != null)
                        for(i in map){
                            keyList += i
                        }
                    resultRepository.uploadResult(keyList)
                }
            }
            override fun onFailure(call: Call<HashMap<String, ArrayList<String>>>, t: Throwable) {
                Log.i("onFailure2",t.toString())
            }
        })
    }
}