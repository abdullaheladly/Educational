package com.abdullah996.education.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.abdullah996.education.R
import com.abdullah996.education.models.QuizListModel
import com.abdullah996.education.models.TeachersModel
import com.bumptech.glide.Glide

class QuizListAdapter(onQuizListItemClicked: OnQuizListItemClicked):
    RecyclerView.Adapter<QuizListAdapter.QuizListViewHolder>() {

    private var quizListModels: List<QuizListModel>? = null
    private val onQuizListItemClicked: OnQuizListItemClicked
    fun setQuizListModels(quizListModels: List<QuizListModel>?) {
        this.quizListModels = quizListModels
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): QuizListViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.quiz_list_item, parent, false)
        return QuizListViewHolder(view, quizListModels)
    }

    override fun onBindViewHolder(holder: QuizListViewHolder, position: Int) {
        holder.listName.text= quizListModels?.get(position)?.name
        val imageUrl= quizListModels?.get(position)?.image
        Glide.with(holder.itemView.context).
        load(imageUrl).
        centerCrop().
        placeholder(R.drawable.placeholder_icon).
        into(holder.listImage)
        val listDescription=quizListModels?.get(position)?.desc
        if (listDescription != null) {
            if (listDescription.length >150){
                listDescription.substring(0,150)
            }
            holder.listDesc.text =listDescription+"..."

        }
    }

    override fun getItemCount(): Int {
        return if (quizListModels == null) {
            0
        } else {
            quizListModels!!.size
        }
    }

    inner class QuizListViewHolder(itemView: View, quizes: List<QuizListModel>?) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        var teacherListModels = quizes
        var listName:TextView=itemView.findViewById(R.id.list_title)
        var listImage:ImageView=itemView.findViewById(R.id.list_image)
        var listDesc:TextView=itemView.findViewById(R.id.list_desc)
        var listLevel:TextView=itemView.findViewById(R.id.list_difficulty)
        var listBtn:TextView=itemView.findViewById(R.id.list_btn)
        override fun onClick(v: View) {
            teacherListModels?.get(adapterPosition)?.let {
                onQuizListItemClicked.onItemClicked(
                    it.desc.toString()
                )
            }

        }

    }

    interface OnQuizListItemClicked {
        fun onItemClicked(subject: String)
    }
    init {
        this.onQuizListItemClicked = onQuizListItemClicked
    }
}