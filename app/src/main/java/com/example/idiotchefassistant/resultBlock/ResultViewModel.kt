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
        resultRepository.loadData(object: OnTaskFinish{
            override fun onFinish(data: ResultData){
                userLiveData.postValue(data)
            }
        })
        return userLiveData
    }

    fun addData(title: String, image: String) {
        val currentMap = resultRepository.getDatas()?.toMutableMap() ?: mutableMapOf()
        if (currentMap.containsKey(title)) {
            currentMap[title]?.add(image)
        } else {
            currentMap[title] = arrayListOf(image)
        }
        resultRepository.uploadData(currentMap)
    }

    fun deleteData(title: String, image: String? = null) {
        val currentMap = resultRepository.getDatas()?.toMutableMap() ?: return

        if (image == null) {
            currentMap.remove(title)
        } else {
            currentMap[title]?.remove(image)
            if (currentMap[title].isNullOrEmpty()) {
                currentMap.remove(title)
            }
        }
        resultRepository.uploadData(currentMap)
    }

    fun findData(item: String):Boolean {
        val currentMap = resultRepository.getDatas()
        return currentMap?.containsKey(item) == true
    }

    fun uploadVideo(video: String?){
        // upload video
        val videoFile = File(video.toString())
        val requestFile = RequestBody.create(MultipartBody.FORM, videoFile)
        val fbody = MultipartBody.Part.createFormData("video", videoFile.name, requestFile)
        detectService.detect(fbody).enqueue(object : Callback<HashMap<String, ArrayList<String>>> {
            override fun onResponse(call: Call<HashMap<String, ArrayList<String>>>, response: Response<HashMap<String, ArrayList<String>>>) {
                if(response.isSuccessful) {
                    val map = response.body()
                    Log.i("onResponse2","OK")
                    resultRepository.uploadData(map?: emptyMap())
//                    callBack()
                }
            }
            override fun onFailure(call: Call<HashMap<String, ArrayList<String>>>, t: Throwable) {
                Log.i("onFailure2",t.toString())
            }
        })
    }
}