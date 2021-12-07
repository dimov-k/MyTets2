package ru.mrroot.mytets2.repository

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import ru.mrroot.mytets2.model.WorksSubj

internal interface OpenLibApi {

    @GET("/subjects/{subject}.json?limit=100")
    fun getWorksBySubject(@Path("subject") subject: String?): Call<WorksSubj?>?

}
