package ru.mrroot.mytets2.view.books

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item.view.*
import ru.mrroot.mytets2.R
import ru.mrroot.mytets2.model.Work
import ru.mrroot.mytets2.utils.click
import ru.mrroot.mytets2.utils.setStartDrawableImageFromUri
import ru.mrroot.mytets2.view.books.BooksAdapter.SearchResultViewHolder

internal class BooksAdapter(private val delegate: Delegate?) :
    RecyclerView.Adapter<SearchResultViewHolder>() {

    private var results: List<Work> = listOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchResultViewHolder {
        return SearchResultViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_item, null)
        )
    }

    override fun onBindViewHolder(
        holder: SearchResultViewHolder,
        position: Int
    ) {
        holder.bind(results[position], delegate)
    }

    override fun getItemCount(): Int {
        return results.size
    }

    fun updateResults(results: List<Work>) {
        this.results = results
        notifyDataSetChanged()
    }

    internal class SearchResultViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(searchResult: Work, delegate: Delegate?) {
            itemView.bookTitle.setStartDrawableImageFromUri("https://covers.openlibrary.org/b/ID/${searchResult.coverId}-M.jpg")
            itemView.bookTitle.text = searchResult.title
            itemView.click { delegate?.onBookPicked(searchResult) }
        }
    }

    interface Delegate {
        fun onBookPicked(book: Work)
    }

}