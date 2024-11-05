package com.example.idiotchefassistant.resultBlock

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.idiotchefassistant.detectService
import com.example.idiotchefassistant.ingredientService
import com.example.idiotchefassistant.itemBlock.IngredientItem
import com.example.idiotchefassistant.recipeBlock.RecipeItem
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
    var iids = emptyArray<Int>()
    var names = emptyArray<String>()
    var mandarins = emptyArray<String>()

    fun callBack(): LiveData<ResultData> {
        resultRepository.loadData(object : OnTaskFinish {
            override fun onFinish(data: ResultData) {
                userLiveData.postValue(data)
            }
        })
        return userLiveData
    }

    private fun validateIdsAndNames(): Boolean {
        if (iids.size != names.size) {
            Log.e("validateIdsAndNames", "iids and names size mismatch")
            return false
        }
        return true
    }

    fun addData(title: String, images: ArrayList<String>) {
        if (!validateIdsAndNames()) return
        if (iids.isEmpty() || names.isEmpty()) {
            Log.e("addData", "iids or names not initialized")
            return
        }
        val currentData = resultRepository.getData()?: ResultData()
        val currentMap = currentData.result?.toMutableMap() ?: mutableMapOf()
        val index = names.indexOfFirst { it.equals(title, ignoreCase = true) }
        if (index == -1) {
            Log.e("addData", "Title not found in names")
            return
        }
        currentMap[iids[index]] = IngItem(title, images)
        currentData.result = currentMap
        resultRepository.uploadData(currentData)
    }

    fun editData(oldName: String, newName: String) {
        if (!validateIdsAndNames()) return
        if (iids.isEmpty() || names.isEmpty()) {
            Log.e("editData", "iids or names not initialized")
            return
        }
        val currentData = resultRepository.getData()?: ResultData()
        val currentMap = currentData.result?.toMutableMap() ?: mutableMapOf()
        val oldIndex = names.indexOfFirst { it.equals(oldName, ignoreCase = true) }
        val newIndex = names.indexOfFirst { it.equals(newName, ignoreCase = true) }
        if (oldIndex == -1 || newIndex == -1) {
            Log.e("editData", "Invalid indices for old or new name")
            return
        }
        val ingItem = currentMap[iids[oldIndex]]
        if (ingItem != null) {
            val newItem = IngItem(newName, ingItem.images)
            currentMap.remove(iids[oldIndex])
            currentMap[iids[newIndex]] = newItem
            currentData.result = currentMap
            resultRepository.uploadData(currentData)
        }
    }

    fun deleteData(title: String) {
        if (!validateIdsAndNames()) return
        if (iids.isEmpty() || names.isEmpty()) {
            Log.e("deleteData", "iids or names not initialized")
            return
        }
        val currentData = resultRepository.getData()?: ResultData()
        val currentMap = currentData.result?.toMutableMap() ?: return
        val index = names.indexOfFirst { it.equals(title, ignoreCase = true) }
        if (index == -1) {
            Log.e("deleteData", "Title not found in names")
            return
        }
        currentMap.remove(iids[index])
        currentData.result = currentMap
        resultRepository.uploadData(currentData)
    }

    fun findData(title: String): Boolean {
        if (!validateIdsAndNames()) return false
        if (iids.isEmpty() || names.isEmpty()) {
            Log.e("findData", "iids or names not initialized")
            return false
        }
        val currentData = resultRepository.getData()?: ResultData()
        val currentMap = currentData.result?.toMutableMap() ?: mutableMapOf()
        val index = names.indexOfFirst { it.equals(title, ignoreCase = true) }
        if (index == -1) {
            Log.e("findData", "Title not found in names")
            return true
        }
        return currentMap.containsKey(iids[index])
    }

    fun uploadPhotos(photos: ArrayList<String>?) {
        _isUploading.postValue(true)
        val finalMap = mutableMapOf<Int, IngItem>()
        var completedRequests = 0
        val totalRequests = photos?.size ?: 0

        ingredientService.getList().enqueue(object : Callback<ArrayList<IngredientItem>> {
            override fun onResponse(
                call: Call<ArrayList<IngredientItem>>,
                response: Response<ArrayList<IngredientItem>>
            ) {
                if (response.isSuccessful) {
                    val list = response.body()
                    iids = list?.map {it.id}?.toTypedArray()!!
                    names = list.map { it.name }.toTypedArray()
                    mandarins  = list.map { it.mandarin }.toTypedArray()
                    Log.i("ingredientService", "OK")
                    val formattedNames = Array(names.size) { i -> "${mandarins[i]} ${names[i].replace("_", " ")}" }
                    // 上傳每張圖片
                    photos?.forEach { p ->
                        val photoFile = File(p)
                        val requestFile = photoFile.asRequestBody(MultipartBody.FORM)
                        val body = MultipartBody.Part.createFormData("image", photoFile.name, requestFile)
                        detectService.detect(body).enqueue(object : Callback<HashMap<String, String>> {
                            // call API 取得辨識結果
                            override fun onResponse(
                                call: Call<HashMap<String, String>>,
                                response: Response<HashMap<String, String>>
                            ) {
                                if (response.isSuccessful) {
                                    val map = response.body()
                                    // 遍歷 detectService 回傳的結果，根據 names 比對 iids 並更新 finalMap
                                    map?.forEach { (key, value) ->
                                        val index = names.indexOfFirst { it.equals(key, ignoreCase = true) }
                                        if(index != -1){
                                            val iid = iids[index]
                                            val currentIngItem = finalMap[iid]

                                            if(currentIngItem != null){
                                                // 如果 finalMap 已經有這個 iid，則更新 images 列表
                                                val updatedImages = currentIngItem.images
                                                updatedImages.add(value)
                                                val updatedItem = IngItem(currentIngItem.name, updatedImages)
                                                finalMap[iid] = updatedItem
                                            }
                                            else{
                                                val newName = "${mandarins[index]} ${names[index]}".replace("_", " ")
                                                finalMap[iid] = IngItem(newName, arrayListOf(value))
                                            }
                                            Log.i("detectService", "Added image for name: $key with iid: $iid")
                                        } else{
                                            Log.w("detectService", "No matching iid found for name: $key")
                                        }
                                    }
                                    completedRequests++
                                    // 當所有請求完成後才進行 ingredientService.getList 的呼叫
                                    if (completedRequests == totalRequests) {
                                        val data = ResultData()
                                        data.result = finalMap
                                        resultRepository.uploadData(data)
                                        _uploadResult.postValue(true)
                                        callBack()
                                        _isUploading.postValue(false)
                                        names = formattedNames
                                    }
                                } else{
                                    Log.e("detectService", "Failed to detect: ${response.message()}")
                                    handleFailure()
                                }
                            }
                            override fun onFailure(call: Call<HashMap<String, String>>, t: Throwable) {
                                Log.e("detectService", "Request failed: ${t.message}")
                                handleFailure()
                            }
                            private fun handleFailure(){
                                completedRequests++
                                if (completedRequests == totalRequests) {
                                    _uploadResult.postValue(false)
                                    _isUploading.postValue(false)
                                }
                            }
                        })
                    }
                    if(totalRequests == 0){
                        _isUploading.postValue(false)
                        _uploadResult.postValue(false)
                    }
                }else{
                    Log.e("ingredientService", "Failed to get ingredients: ${response.message()}")
                    _isUploading.postValue(false)
                }
            }
            override fun onFailure(call: Call<ArrayList<IngredientItem>>, t: Throwable) {
                Log.e("ingredientService", "Request failed: ${t.message}")
                _isUploading.postValue(false)
            }
        })
    }

    fun resultSearch(): LiveData<List<RecipeItem>>{
        val liveData = MutableLiveData<List<RecipeItem>>()
        val iids = resultRepository.getData()?.result?.keys?.toList()?: emptyList()
        if(iids.isEmpty()){
            liveData.postValue(emptyList())
            return liveData
        }
        return resultRepository.searchByIids(0, iids)
    }
}