package com.example.converter.data

import com.example.converter.data.models.CurrencyResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyApi {
    @GET("/latest?apikey=fca_live_UNRVeLUoMzFah6WbNGGbM7VMgviGoLLDJ3bPkwKX")
    suspend fun getRates(@Query("base") base : String): Response<CurrencyResponse>

}