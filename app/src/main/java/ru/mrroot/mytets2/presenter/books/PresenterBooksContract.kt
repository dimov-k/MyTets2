package ru.mrroot.mytets2.presenter.books

import ru.mrroot.mytets2.presenter.PresenterContract

internal interface PresenterBooksContract : PresenterContract {
    fun searchOpenLib(searchQuery: String)
}