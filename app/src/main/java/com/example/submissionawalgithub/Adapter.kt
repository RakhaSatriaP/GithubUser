package com.example.submissionawalgithub

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.submissionawalgithub.databinding.CardUserBinding

class Adapter(
    private val data: MutableList<Response.Item> = mutableListOf(),
    private val listener: (Response.Item) -> Unit
) : RecyclerView.Adapter<Adapter.ViewHolder>() {

    class ViewHolder(private val v: CardUserBinding) : RecyclerView.ViewHolder(v.root) {
        fun bind(item: Response.Item) {
            v.fotoUser.load(item.avatar_url) {
                transformations(CircleCropTransformation())
            }
            v.namaUser.text = item.login
        }
    }

    fun setData(data: MutableList<Response.Item>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
        holder.itemView.setOnClickListener {
            listener(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(CardUserBinding.inflate(LayoutInflater.from(parent.context), parent, false))

}
