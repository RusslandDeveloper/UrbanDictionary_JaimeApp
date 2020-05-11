package com.example.urbandictionaryapp.model

import com.squareup.moshi.Json

data class ItemsModel (
@field:Json(name = "definition") var definition:String,
@field:Json(name = "example") var example: String,
@field:Json(name = "thumbs_up") var thumbs_up: Int,
@field:Json(name = "thumbs_down") var thumbs_down: Int,
@field:Json(name = "author") var author: String
)

data class ItemDescriptionResponse(

@field:Json(name = "list") var list : List<ItemsModel>)

