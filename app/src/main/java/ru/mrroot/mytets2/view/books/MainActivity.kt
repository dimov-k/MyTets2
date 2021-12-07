package ru.mrroot.mytets2.view.books

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.mrroot.mytets2.R
import ru.mrroot.mytets2.model.Work
import ru.mrroot.mytets2.presenter.books.BooksPresenter
import ru.mrroot.mytets2.presenter.books.PresenterBooksContract
import ru.mrroot.mytets2.repository.OpenLibApi
import ru.mrroot.mytets2.repository.OpenLibRepository
import ru.mrroot.mytets2.view.book.BookActivity
import java.util.*

class MainActivity : AppCompatActivity(), ViewBooksContract, BooksAdapter.Delegate {
    private val adapter = BooksAdapter(this)
    private val presenter: PresenterBooksContract = BooksPresenter(this, createRepository())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUI()
    }

    private fun setUI() {
        setQueryListener()
        setRecyclerView()
    }

    private fun setRecyclerView() {
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter
    }

    private fun setQueryListener() {
        searchEditText.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val query = searchEditText.text.toString()
                if (query.isNotBlank()) {
                    presenter.searchOpenLib(query)
                    return@OnEditorActionListener true
                } else {
                    Toast.makeText(
                        this@MainActivity,
                        getString(R.string.enter_search_word),
                        Toast.LENGTH_SHORT
                    ).show()
                    return@OnEditorActionListener false
                }
            }
            false
        })
    }

    private fun createRepository(): OpenLibRepository {
        return OpenLibRepository(createRetrofit().create(OpenLibApi::class.java))
    }

    private fun createRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(createOkHttpClient())
            .build()
    }

    private fun createOkHttpClient(): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        return httpClient.build()
    }


    override fun displaySearchResults(searchResults: List<Work>, totalCount: String) {
        adapter.updateResults(searchResults)
        resultsCountTextView.text =
            String.format(Locale.getDefault(), getString(R.string.total_count), totalCount)
    }

    override fun displayError() {
        Toast.makeText(this, getString(R.string.undefined_error), Toast.LENGTH_SHORT).show()
    }

    override fun displayError(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    }

    override fun displayLoading(show: Boolean) {
        if (show) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }

    override fun onBookPicked(book: Work) {
        startActivity(
            BookActivity.getIntent(
                this,
                book.title,
                book.authors[0].name,
                "https://covers.openlibrary.org/b/ID/${book.coverId}-M.jpg",
                0
            )
        )
    }

    companion object {
        const val BASE_URL = "https://openlibrary.org"
    }


}