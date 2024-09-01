package com.example.idiotchefassistant.resultBlock

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.appcompat.app.AlertDialog
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

    fun addData(item: String) {
        val currentArray = resultRepository.getNowResults()
        val updatedArray = currentArray?.plus(item) ?: arrayOf(item)
        resultRepository.uploadResult(updatedArray)
    }

    fun deleteData(item: String) {
        val currentArray = resultRepository.getNowResults()
        if (currentArray != null){
            val updatedArray = currentArray.filter { it != item }.toTypedArray()
            resultRepository.uploadResult(updatedArray)
        }
    }

    fun findData(item: String):Boolean {
        val currentArray = resultRepository.getNowResults()
        if (currentArray == null || !currentArray.contains(item)) {
            return false
        } else {
            return true
        }
    }

    fun uploadVideo(video: String?){
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
//                    callBack()
                }
            }
            override fun onFailure(call: Call<HashMap<String, ArrayList<String>>>, t: Throwable) {
                Log.i("onFailure2",t.toString())
            }
        })
    }
}