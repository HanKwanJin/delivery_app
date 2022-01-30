package com.han.delivery_app.presentation.addtrackingitem

import com.han.delivery_app.data.entity.ShippingCompany
import com.han.delivery_app.presentation.BasePresenter
import com.han.delivery_app.presentation.BaseView

class AddTrackingItemsContract {
    interface View: BaseView<Presenter>{
        fun showShippingCompaniesLoadingIndicator()

        fun hideShippingCompaniesLoadingIndicator()

        fun showSaveTrackingItemIndicator()

        fun hideSaveTrackingItemIndicator()

        fun showRecommendCompanyLoadingIndicator()

        fun hideRecommendCompanyLoadingIndicator()

        fun showRecommendCompany(company: ShippingCompany)

        fun showCompanies(companies: List<ShippingCompany>)

        fun enableSaveButton()

        fun disableSaveButton()

        fun showErrorToast(message: String)

        fun finish()
    }

    interface Presenter: BasePresenter{
        var invoice: String?
        var shippingCompanies: List<ShippingCompany>?
        var selectedShippingCompany: ShippingCompany?

        fun fetchShippingCompanies()

        fun fetchRecommendShippingCompany()

        fun changeSelectedShippingCompany(companyName: String)

        fun changeShippingInvoice(invoice: String)

        fun saveTrackingItem()
    }
}
