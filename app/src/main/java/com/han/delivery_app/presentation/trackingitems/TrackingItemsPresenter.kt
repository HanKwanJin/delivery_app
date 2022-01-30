package com.han.delivery_app.presentation.trackingitems

import com.han.delivery_app.data.entity.TrackingInformation
import com.han.delivery_app.data.entity.TrackingItem
import com.han.delivery_app.data.repository.TrackingItemRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class TrackingItemsPresenter(
    private val view: TrackingItemsContract.View,
    private val trackingItemRepository: TrackingItemRepository
): TrackingItemsContract.Presenter {
    override var trackingItemInformation: List<Pair<TrackingItem,
            TrackingInformation>> = emptyList()

    override val scope: CoroutineScope = MainScope()

    init {
        trackingItemRepository
            .trackingItems
            .onEach { refresh() }
            .launchIn(scope)
    }

    override fun onViewCreated() {
        fetchTrackingInformation()
    }
    override fun refresh() {
        fetchTrackingInformation(true)
    }
    override fun onDestroyView() {}
    private fun fetchTrackingInformation(forceFetch: Boolean = false) = scope.launch {
        try {
            view.showLoadingIndicator()
            if(trackingItemInformation.isEmpty() || forceFetch){
                trackingItemInformation = trackingItemRepository.getTrackingItemInformation()
            }
            if(trackingItemInformation.isEmpty()){
                view.showNoDataDescription()
            }else{
                view.showTrackingItemInformation(trackingItemInformation)
            }
        }catch (exception: Exception){
            exception.printStackTrace()
        }finally {
            view.hideLoadingIndicator()
        }
    }
}