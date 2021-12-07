package ru.mrroot.mytets2.presenter.book

import ru.mrroot.mytets2.presenter.PresenterContract

internal interface PresenterBookContract : PresenterContract {
    fun setRaiting(count: Int)
    fun onIncrement()
    fun onDecrement()
}