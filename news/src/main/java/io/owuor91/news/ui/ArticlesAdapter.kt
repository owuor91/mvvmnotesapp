package io.owuor91.news.ui;

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.owuor91.mvvmnotesapp.models.Article
import io.owuor91.news.R
import kotlinx.android.synthetic.main.article_list_item.view.*

class ArticlesAdapter(private val list: List<Article>) :
    RecyclerView.Adapter<ArticlesAdapter.ArticlesViewholder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticlesViewholder {
        val rowView =
            LayoutInflater.from(parent.context).inflate(R.layout.article_list_item, parent, false)
        return ArticlesViewholder(rowView)
    }

    override fun getItemCount(): Int {
        return list.count()
    }

    override fun onBindViewHolder(holder: ArticlesViewholder, position: Int) =
        holder.bind(list.get(position))

    class ArticlesViewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Article) = with(itemView) {
            tvTitle.text = item.title
            tvDescription.text = item.description
            setOnClickListener {

            }
        }
    }
}