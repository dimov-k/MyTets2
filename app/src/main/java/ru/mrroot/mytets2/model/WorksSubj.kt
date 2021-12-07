package ru.mrroot.mytets2.model

import com.google.gson.annotations.SerializedName

data class WorksSubj(
    @SerializedName("key")
    val key: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("work_count")
    val workCount: String,
    @SerializedName("works")
    val works: List<Work>
)
