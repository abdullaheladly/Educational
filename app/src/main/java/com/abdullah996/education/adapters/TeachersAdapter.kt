package com.abdullah996.education.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.abdullah996.education.R
import com.abdullah996.education.databinding.TeachersListItemBinding
import com.abdullah996.education.models.SubjectsModel
import com.abdullah996.education.models.TeachersModel
import com.bumptech.glide.Glide

class TeachersAdapter(onTeachersListItemClicked: OnTeachersListItemClicked):
RecyclerView.Adapter<TeachersAdapter.TeachersViewHolder>() {

    private var teachersModels: List<TeachersModel>? = null
    private val onteachersListItemClicked: OnTeachersListItemClicked
    fun setTeachersListModels(teachersModels: List<TeachersModel>?) {
        this.teachersModels = teachersModels
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TeachersViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.teachers_list_item, parent, false)
        return TeachersViewHolder(view, teachersModels)
    }

    override fun onBindViewHolder(holder: TeachersViewHolder, position: Int) {
        holder.listName.text=teachersModels?.get(position)?.Teacher_name
        val imageUrl= teachersModels?.get(position)?.Teacher_image
        Glide.with(holder.itemView.context).
        load(imageUrl).
        centerCrop().
        placeholder(R.drawable.placeholder_icon).
        into(holder.listImage)
    }

    override fun getItemCount(): Int {
        return if (teachersModels == null) {
            0
        } else {
            teachersModels!!.size
        }
    }

    inner class TeachersViewHolder(itemView: View, teachers: List<TeachersModel>?) : RecyclerView.ViewHolder(itemView),
            View.OnClickListener {
        var teacherListModels = teachers
        var listName: TextView = itemView.findViewById(R.id.teacherName)

         var listImage: ImageView =itemView.findViewById(R.id.teacherImage)
        override fun onClick(v: View) {
            teacherListModels?.get(adapterPosition)?.let {
                onteachersListItemClicked.onItemClicked(
                    it.Teacher_name.toString()
                )
            }

        }

    }

    interface OnTeachersListItemClicked {
        fun onItemClicked(subject: String)
    }
    init {
        this.onteachersListItemClicked = onTeachersListItemClicked
    }

}

