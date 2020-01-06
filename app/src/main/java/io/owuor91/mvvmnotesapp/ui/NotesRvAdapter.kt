package io.owuor91.mvvmnotesapp.ui;

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.owuor91.mvvmnotesapp.R
import io.owuor91.mvvmnotesapp.models.Note
import kotlinx.android.synthetic.main.note_list_item.view.*

class NotesRvAdapter(private val list: List<Note>) :
    RecyclerView.Adapter<NotesRvAdapter.NotesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val rowView =
            LayoutInflater.from(parent.context).inflate(R.layout.note_list_item, parent, false)
        return NotesViewHolder(rowView)
    }

    override fun getItemCount(): Int {
        return list.count()
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val item = list.get(position)

        holder.bind(item)
    }

    class NotesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Note) = with(itemView) {

            tvNoteText.text = item.noteText
            tvTitle.text = item.title


            setOnClickListener {

            }
        }
    }
}