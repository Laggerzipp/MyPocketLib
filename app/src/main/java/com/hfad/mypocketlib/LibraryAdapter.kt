package com.hfad.mypocketlib

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hfad.mypocketlib.database.Book
import com.hfad.mypocketlib.databinding.RvItemLibraryBinding

class LibraryAdapter:RecyclerView.Adapter<LibraryAdapter.LibraryHolder>() {
    private val libraryList = ArrayList<Book>()

    class LibraryHolder(view: View):RecyclerView.ViewHolder(view){
        val binding = RvItemLibraryBinding.bind(view)
        fun bind(book:Book) = with(binding){
            imBook.setImageResource(book.imageId)
            tvTitle.text = book.title
            tvGrade.text = book.grade.toString()
            tvLang.text = book.language
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LibraryAdapter.LibraryHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_item_library,parent,false)
        return LibraryHolder(view)
    }

    override fun onBindViewHolder(holder: LibraryAdapter.LibraryHolder, position: Int) {
        holder.bind(libraryList[position])
    }

    override fun getItemCount(): Int {
        return libraryList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addABook(book: Book){
        libraryList.add(book)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addAllBooks(books:List<Book>){
        libraryList.addAll(books)
        notifyDataSetChanged()
    }
}