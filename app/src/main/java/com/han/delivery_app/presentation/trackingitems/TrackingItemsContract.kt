package com.han.delivery_app.presentation.trackingitems

import com.han.delivery_app.data.entity.TrackingInformation
import com.han.delivery_app.data.entity.TrackingItem
import com.han.delivery_app.presentation.BasePresenter
import com.han.delivery_app.presentation.BaseView

class TrackingItemsContract {
    interface View: BaseView<Presenter>{
        fun showLoadingIndicator()

        fun hideLoadingIndicator()

        fun showNoDataDescription()

        fun showTrackingItemInformation(trackingItemInformation: List<Pair<TrackingItem,TrackingInformation>>)
    }
    interface Presenter: BasePresenter{
        var trackingItemInformation: List<Pair<TrackingItem, TrackingInformation>>

        fun refresh()
    }
}