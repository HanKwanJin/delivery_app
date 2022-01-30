package com.han.delivery_app.data.repository

import com.han.delivery_app.data.entity.ShippingCompany

interface ShippingCompanyRepository {
    suspend fun getShippingCompanies(): List<ShippingCompany>

    suspend fun getRecommendShippingCompany(invoice: String): ShippingCompany?
}