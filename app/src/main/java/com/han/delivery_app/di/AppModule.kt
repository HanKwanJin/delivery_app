package com.han.delivery_app.di

import android.app.Activity
import com.han.delivery_app.BuildConfig
import com.han.delivery_app.data.api.SweetTrackerApi
import com.han.delivery_app.data.api.Url
import com.han.delivery_app.data.db.AppDatabase
import com.han.delivery_app.data.entity.TrackingInformation
import com.han.delivery_app.data.entity.TrackingItem
import com.han.delivery_app.data.preference.PreferenceManager
import com.han.delivery_app.data.preference.SharedPreferenceManager
import com.han.delivery_app.data.repository.*
import com.han.delivery_app.presentation.addtrackingitem.AddTrackingItemFragment
import com.han.delivery_app.presentation.addtrackingitem.AddTrackingItemPresenter
import com.han.delivery_app.presentation.addtrackingitem.AddTrackingItemsContract
import com.han.delivery_app.presentation.trackinghistory.TrackingHistoryContract
import com.han.delivery_app.presentation.trackinghistory.TrackingHistoryFragment
import com.han.delivery_app.presentation.trackinghistory.TrackingHistoryPresenter
import com.han.delivery_app.presentation.trackingitems.TrackingItemsContract
import com.han.delivery_app.presentation.trackingitems.TrackingItemsFragment
import com.han.delivery_app.presentation.trackingitems.TrackingItemsPresenter
import com.han.delivery_app.work.AppWorkerFactory
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

val appModule = module{
    single { Dispatchers.IO }
    //Database
    single { AppDatabase.build(androidApplication()) }
    single { get<AppDatabase>().trackingItemDao() }
    single { get<AppDatabase>().shippingCompanyDao() }

    //Api
    single {
        OkHttpClient()
            .newBuilder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = if(BuildConfig.DEBUG){
                        HttpLoggingInterceptor.Level.BODY
                    }else{
                        HttpLoggingInterceptor.Level.NONE
                    }
                }
            )
            .build()
    }
    single<SweetTrackerApi>{
        Retrofit.Builder().baseUrl(Url.SWEET_TRACKER_API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
            .create()
    }
    //Preference
    single { androidContext().getSharedPreferences("preference", Activity.MODE_PRIVATE) }
    single<PreferenceManager> { SharedPreferenceManager(get()) }
    //Repository
    single<TrackingItemRepository> { TrackingItemRepositoryStub() }
    //single<TrackingItemRepository> { TrackingItemRepositoryImpl(get(),get(), get()) }
    single<ShippingCompanyRepository> { ShippingCompanyRepositoryImpl(get(),get(),get(),get())}

    // Work
    single { AppWorkerFactory(get(), get()) }

    //Presentation
    scope<TrackingItemsFragment>{
        scoped<TrackingItemsContract.Presenter> { TrackingItemsPresenter(getSource(), get()) }
    }
    scope<AddTrackingItemFragment>{
        scoped<AddTrackingItemsContract.Presenter> {
            AddTrackingItemPresenter(getSource(), get(),get())
        }
    }
    scope<TrackingHistoryFragment> {
        scoped<TrackingHistoryContract.Presenter> { (trackingItem: TrackingItem, trackingInformation: TrackingInformation) ->
            TrackingHistoryPresenter(getSource(), get(), trackingItem, trackingInformation)
        }
    }

}