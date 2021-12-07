package ru.mrroot.mytets2.view.books

import  ru.mrroot.mytets2.model.Work

internal interface ViewBooksContract {
    fun displaySearchResults(
        searchResults: List<Work>,
        totalCount: String
    )

    fun displayError()
    fun displayError(error: String)
    fun displayLoading(show: Boolean)
}
