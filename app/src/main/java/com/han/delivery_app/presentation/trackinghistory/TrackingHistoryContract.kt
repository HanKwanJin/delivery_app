package com.han.delivery_app.presentation.trackinghistory

import com.han.delivery_app.data.entity.TrackingInformation
import com.han.delivery_app.data.entity.TrackingItem
import com.han.delivery_app.presentation.BasePresenter
import com.han.delivery_app.presentation.BaseView

class TrackingHistoryContract {

    interface View : BaseView<Presenter> {

        fun hideLoadingIndicator()

        fun showTrackingItemInformation(trackingItem: TrackingItem, trackingInformation: TrackingInformation)

        fun finish()
    }

    interface Presenter : BasePresenter {

        fun refresh()

        fun deleteTrackingItem()
    }
}