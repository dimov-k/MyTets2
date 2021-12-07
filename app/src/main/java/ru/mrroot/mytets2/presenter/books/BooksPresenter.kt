package ru.mrroot.mytets2.presenter.books

import retrofit2.Response
import ru.mrroot.mytets2.model.WorksSubj
import ru.mrroot.mytets2.repository.OpenLibRepository
import ru.mrroot.mytets2.repository.OpenLibRepository.OpenLibRepositoryCallback
import ru.mrroot.mytets2.view.books.ViewBooksContract

internal class BooksPresenter internal constructor(
    private val viewContract: ViewBooksContract,
    private val repository: OpenLibRepository
) : PresenterBooksContract, OpenLibRepositoryCallback {

    override fun searchOpenLib(searchQuery: String) {
        viewContract.displayLoading(true)
        repository.searchOpenLib(searchQuery, this)
    }

    override fun handleOpenLibResponse(response: Response<WorksSubj?>?) {
        viewContract.displayLoading(false)
        if (response != null && response.isSuccessful) {
            val searchResponse = response.body()
            val searchResults = searchResponse?.works
            val totalCount = searchResponse?.workCount
            if (searchResults != null && totalCount != null) {
                viewContract.displaySearchResults(
                    searchResults,
                    totalCount
                )
            } else {
                viewContract.displayError("Search results or total count are null")
            }
        } else {
            viewContract.displayError("Response is null or unsuccessful")
        }
    }

    override fun handleOpenLibError() {
        viewContract.displayLoading(false)
        viewContract.displayError()
    }
}
