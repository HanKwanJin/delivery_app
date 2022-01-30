package com.han.delivery_app.presentation

interface BaseView<PresenterT: BasePresenter> {
    val presenter: PresenterT
}