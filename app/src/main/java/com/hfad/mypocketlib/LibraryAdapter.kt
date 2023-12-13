package com.hfad.mypocketlib

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hfad.mypocketlib.database.Book
import com.hfad.mypocketlib.databinding.RvItemLibraryBinding

class LibraryAdapter(private val listener: Listener):RecyclerView.Adapter<LibraryAdapter.LibraryHolder>() {
    private val libraryList = ArrayList<Book>()

    class LibraryHolder(view: View):RecyclerView.ViewHolder(view){
        private val binding = RvItemLibraryBinding.bind(view)
        fun bind(book:Book, listener: Listener) = with(binding){
            imBook.setImageResource(book.imageId)
            tvTitle.text = book.title
            tvGrade.text = book.grade
            tvLang.text = book.language
            layoutItem.setOnClickListener {
                listener.onClick(book)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LibraryHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_item_library,parent,false)
        return LibraryHolder(view)
    }

    override fun onBindViewHolder(holder: LibraryHolder, position: Int) {
        holder.bind(libraryList[position], listener)
    }

    override fun getItemCount(): Int {
        return libraryList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addBook(book: Book){
        libraryList.add(book)
        notifyDataSetChanged()
    }

    interface Listener{
        fun onClick(book: Book)
    }
}