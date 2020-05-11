package com.example.urbandictionaryapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.urbandictionaryapp.model.ItemDescriptionResponse
import com.example.urbandictionaryapp.model.RepositoryUrbanDictionnary

class UrbanDictionaryViewModel : ViewModel() {

    private val urbanDescription = MutableLiveData<ItemDescriptionResponse>()
    private val urbanDescriptionError = MutableLiveData<String>()

    val repository: RepositoryUrbanDictionnary by lazy {
        RepositoryUrbanDictionnary()
    }

    fun getDescription(input: String) {
        repository.setListener(::updateObservable)
        repository.getWordDefinition(input)
    }

    private fun updateObservable(response: ItemDescriptionResponse?) {
        if (response == null)
            urbanDescriptionError.postValue("Error Message")
        else
            urbanDescription.postValue(response)
    }

    fun sortDataUp() {
        urbanDescription.value?.list?.sortedWith(compareBy {
            it.thumbs_up
        })?.reversed()?.apply {
            urbanDescription.postValue(ItemDescriptionResponse(this))
        }
    }

    fun sortDataDown() {
        urbanDescription.value?.list?.sortedWith(compareBy {
            it.thumbs_down
        })?.reversed()?.apply {
            urbanDescription.postValue(ItemDescriptionResponse(this))
        }

    }

    fun getUrbanDescription(): LiveData<ItemDescriptionResponse> = urbanDescription
    fun getUrbanDescriptionError(): LiveData<String> = urbanDescriptionError

    override fun onCleared() {
        super.onCleared()
        repository.removeListener()
    }


}