package ru.mrroot.mytets2.model

import com.google.gson.annotations.SerializedName

data class Author(
    @SerializedName("key")
    val key: String,
    @SerializedName("name")
    val name: String
)