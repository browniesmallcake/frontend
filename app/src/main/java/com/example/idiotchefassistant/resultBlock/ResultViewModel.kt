package com.example.idiotchefassistant.resultBlock

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.idiotchefassistant.itemBlock.IngredientItem
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import com.example.idiotchefassistant.resultBlock.ResultRepository.OnTaskFinish
import okhttp3.RequestBody.Companion.asRequestBody

class ResultViewModel(private var resultRepository: ResultRepository): ViewModel() {
    private var userLiveData = MutableLiveData<ResultData>()
    private val _isUploading = MutableLiveData<Boolean>()
    val isUploading: LiveData<Boolean> get() = _isUploading
    private val _uploadResult = MutableLiveData<Boolean>()
    val uploadResult: LiveData<Boolean> get() = _uploadResult

    fun callBack(): LiveData<ResultData> {
        resultRepository.loadData(object : OnTaskFinish {
            override fun onFinish(data: ResultData) {
                userLiveData.postValue(data)
            }
        })
        return userLiveData
    }

    fun addData(title: String, image: ArrayList<String>) {
        val currentMap = resultRepository.getData()?.toMutableMap() ?: mutableMapOf()
        currentMap[title] = image
        resultRepository.uploadData(currentMap)
    }

    fun editData(oldTitle: String, newTitle: String) {
        val currentMap = resultRepository.getData()?.toMutableMap() ?: mutableMapOf()
        val images = currentMap.remove(oldTitle)
        if (images != null) {
            currentMap[newTitle] = images
            resultRepository.uploadData(currentMap)
        }
    }

    fun deleteData(title: String, image: ArrayList<String>? = null) {
        val currentMap = resultRepository.getData()?.toMutableMap() ?: return

        if (image == null || currentMap[title] == image) {
            currentMap.remove(title)
        }
        resultRepository.uploadData(currentMap)
    }

    fun findData(item: String): Boolean {
        val currentMap = resultRepository.getData()
        return currentMap?.containsKey(item) == true
    }

//    fun uploadVideo(video: String?) {
//        _isUploading.postValue(true)
//        // upload video
//        val videoFile = File(video.toString())
//        val requestFile = videoFile.asRequestBody(MultipartBody.FORM)
//        val body = MultipartBody.Part.createFormData("video", videoFile.name, requestFile)
//        detectService.detect(body).enqueue(object : Callback<HashMap<String, String>> {
//            override fun onResponse(
//                call: Call<HashMap<String, ArrayList<String>>>,
//                response: Response<HashMap<String, ArrayList<String>>>
//            ) {
//                if (response.isSuccessful) {
//                    val map = response.body()
//                    Log.i("onResponse2", "OK")
//                    // get ingredient list
//                    ingredientService.getList()
//                        .enqueue(object : Callback<ArrayList<IngredientItem>> {
//                            override fun onResponse(
//                                call: Call<ArrayList<IngredientItem>>,
//                                response: Response<ArrayList<IngredientItem>>
//                            ) {
//                                if (response.isSuccessful) {
//                                    val list = response.body()
//                                    val names: Array<String>? =
//                                        list?.map { it.name }?.toTypedArray()
//                                    val mandarins: Array<String>? =
//                                        list?.map { it.mandarin }?.toTypedArray()
//                                    Log.i("onResponse3", "OK")
//                                    val updateMap = map?.mapKeys { entry ->
//                                        val newKey = entry.key
//                                        val index = names?.indexOfFirst {
//                                            it.equals(
//                                                newKey,
//                                                ignoreCase = true
//                                            )
//                                        }
//                                        newKey.replace("_", " ")
//                                        if (index != -1) {
//                                            "${index?.let { mandarins?.get(it) }} ${index?.let { names[it] }}"
//                                        } else {
//                                            newKey
//                                        }
//                                    }
//                                    val updateMapKotlin: Map<String, ArrayList<String>> = updateMap
//                                        ?.mapValues { entry ->
//                                            entry.value.toList().toCollection(ArrayList())
//                                        }
//                                        ?: emptyMap()
//                                    resultRepository.uploadData(updateMapKotlin)
//                                    _uploadResult.postValue(true)
//                                    callBack()
//                                } else {
//                                    _uploadResult.postValue(false)
//                                }
//                                _isUploading.postValue(false)
//                            }
//
//                            override fun onFailure(
//                                call: Call<ArrayList<IngredientItem>>,
//                                t: Throwable
//                            ) {
//                                Log.i("onFailure3", t.toString())
//                                _uploadResult.postValue(false)
//                                _isUploading.postValue(false)
//                            }
//                        })
//                }
//            }
//
//            override fun onFailure(call: Call<HashMap<String, String>>, t: Throwable) {
//                Log.i("onFailure2", t.toString())
//                _uploadResult.postValue(false)
//                _isUploading.postValue(false)
//            }
//        })
//    }

    fun uploadPhotos(photos: ArrayList<String>?) {
        _isUploading.postValue(true)
        val finalMap = mutableMapOf<String, ArrayList<String>>()
        var completedRequests = 0
        val totalRequests = photos?.size ?: 0

        // 上傳每張圖片
        photos?.forEach { p ->
            val photoFile = File(p)
            val requestFile = photoFile.asRequestBody(MultipartBody.FORM)
            val body = MultipartBody.Part.createFormData("image", photoFile.name, requestFile)

            detectService.detect(body).enqueue(object : Callback<HashMap<String, String>> {
                override fun onResponse(
                    call: Call<HashMap<String, String>>,
                    response: Response<HashMap<String, String>>
                ) {
                    if (response.isSuccessful) {
                        val map = response.body()
                        // 更新 finalMap，每個 key 對應的 ArrayList 加入新圖片
                        map?.forEach { (key, value) ->
                            val currentList = finalMap.getOrPut(key) { arrayListOf() }
                            currentList.add(value)
                        }
                        completedRequests++

                        // 當所有請求完成後才進行 ingredientService.getList 的呼叫
                        if (completedRequests == totalRequests) {
                            // 呼叫 ingredientService.getList() 處理資料
                            ingredientService.getList().enqueue(object : Callback<ArrayList<IngredientItem>> {
                                override fun onResponse(
                                    call: Call<ArrayList<IngredientItem>>,
                                    response: Response<ArrayList<IngredientItem>>
                                ) {
                                    if (response.isSuccessful) {
                                        val list = response.body()
                                        val names: Array<String>? = list?.map { it.name }?.toTypedArray()
                                        val mandarins: Array<String>? = list?.map { it.mandarin }?.toTypedArray()

                                        Log.i("onResponse3", "OK")

                                        // 替換 finalMap 中的 key，將英文名稱替換為中文名稱並去掉 '_'
                                        val updateMap = finalMap.mapKeys { entry ->
                                            val newKey = entry.key
                                            val index = names?.indexOfFirst {
                                                it.equals(newKey, ignoreCase = true)
                                            }
                                            newKey.replace("_", " ").let {
                                                if (index != -1) {
                                                    "${index?.let { mandarins?.get(it) }} ${index?.let { names[it] }}"
                                                } else {
                                                    it
                                                }
                                            }
                                        }

                                        // 將最終的 updateMap 上傳到 resultRepository
                                        resultRepository.uploadData(updateMap)
                                        _uploadResult.postValue(true)
                                        callBack()
                                    } else {
                                        _uploadResult.postValue(false)
                                    }
                                    _isUploading.postValue(false)
                                }

                                override fun onFailure(call: Call<ArrayList<IngredientItem>>, t: Throwable) {
                                    Log.i("onFailure3", t.toString())
                                    _uploadResult.postValue(false)
                                    _isUploading.postValue(false)
                                }
                            })
                        }
                    }
                }

                override fun onFailure(call: Call<HashMap<String, String>>, t: Throwable) {
                    Log.i("onFailure2", t.toString())
                    completedRequests++
                    if (completedRequests == totalRequests) {
                        _uploadResult.postValue(false)
                        _isUploading.postValue(false)
                    }
                }
            })
        }

        // 如果沒有圖片要上傳，直接更新狀態
        if (totalRequests == 0) {
            _isUploading.postValue(false)
            _uploadResult.postValue(false)
        }
    }

}