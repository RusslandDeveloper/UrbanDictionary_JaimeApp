package com.example.urbandictionaryapp.model

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepositoryUrbanDictionnary {

private var viewModeListener:((ItemDescriptionResponse?)-> Unit)? = null


    fun getWordDefinition(input: String) {
        onWordDefinitionChanged(input)
    }


    private fun onWordDefinitionChanged(input: String) {
        ApiInterfaceResponse.initRetrofit().getDefinition(input)
            .enqueue(object : Callback<ItemDescriptionResponse> {
                override fun onFailure(call: Call<ItemDescriptionResponse>, t: Throwable) {
                    viewModeListener?.invoke(null)
                }

                override fun onResponse(
                    call: Call<ItemDescriptionResponse>,
                    response: Response<ItemDescriptionResponse>
                ) {
                    viewModeListener?.invoke(response.body())
                }
            })

    }

    fun setListener(viewModeListener: (ItemDescriptionResponse?) -> Unit) {
        this.viewModeListener = viewModeListener
    }

    fun removeListener() {
        viewModeListener = null
    }


}