package com.example.searchview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class MyAdapter(private val itemList: List<String>) :
    RecyclerView.Adapter<MyAdapter.ViewHolder>(), Filterable {

    var filteredList: List<String> = itemList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            android.R.layout.simple_list_item_1, parent, false
        )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = filteredList[position]
    }

    override fun getItemCount(): Int = filteredList.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(android.R.id.text1)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val queryString = constraint?.toString()?.toLowerCase(Locale.ROOT)
                val filterResults = FilterResults()
                filterResults.values = if (queryString == null || queryString.isEmpty()) {
                    itemList
                } else {
                    itemList.filter { it.toLowerCase(Locale.ROOT).contains(queryString) }
                }
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredList = results?.values as List<String>
                notifyDataSetChanged()
            }
        }
    }
}
