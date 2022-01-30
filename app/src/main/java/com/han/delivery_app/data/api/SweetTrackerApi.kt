package com.han.delivery_app.data.api

import com.han.delivery_app.BuildConfig
import com.han.delivery_app.data.entity.ShippingCompanies
import com.han.delivery_app.data.entity.ShippingCompany
import com.han.delivery_app.data.entity.TrackingInformation
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SweetTrackerApi {
    @GET("api/v1/trackingInfo?t_key=${BuildConfig.DELIVERY_API_KEY}")
    suspend fun getTrackingInformation(
        @Query("t_code") companyCode: String,
        @Query("t_invoice") invoice: String
    ): Response<TrackingInformation>

    @GET("api/v1/companylist?t_key=${BuildConfig.DELIVERY_API_KEY}")
    suspend fun getShippingCompanies(): Response<ShippingCompanies>

    @GET("api/v1/recommend?t_key=${BuildConfig.DELIVERY_API_KEY}")
    suspend fun getRecommendShippingCompanies(
        @Query("t_invoice") invoice: String
    ): Response<ShippingCompanies>
}