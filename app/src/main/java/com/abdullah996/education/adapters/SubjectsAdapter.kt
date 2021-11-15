package com.abdullah996.education.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.abdullah996.education.R
import com.abdullah996.education.models.SubjectsModel
import com.bumptech.glide.Glide

/*
class QuizListAdapter: RecyclerView.Adapter<QuizListAdapter.QuizViewHolder>() {

    private var quizListModels: List<QuizListModel>? = null




    fun setQuizListModels(quizListModels: List<QuizListModel>?) {
        this.quizListModels = quizListModels
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizViewHolder {
        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.single_list_item,parent,false)
        return QuizViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: QuizViewHolder, position: Int) {
        holder.listName.text= quizListModels?.get(position)?.name
        val imageUrl= quizListModels?.get(position)?.image
        Glide.with(holder.itemView.context).
                load(imageUrl).
                centerCrop().
                placeholder(R.drawable.placeholder_image).
                into(holder.listImage)
        val listDescription=quizListModels?.get(position)?.desc
        if (listDescription != null) {
            if (listDescription.length >150){
                listDescription.substring(0,150)
            }
                holder.listDesc.text =listDescription+"..."
                    holder.listLevel.text= quizListModels?.get(position)?.level
        }

    }

    override fun getItemCount(): Int {
      if (quizListModels==null){
          return 0
      }
        else{
            return quizListModels!!.size
      }
    }
    class QuizViewHolder(itemView: View) :  RecyclerView.ViewHolder(itemView),View.OnClickListener {
        var listName:TextView=itemView.findViewById(R.id.list_title)
        var listImage:ImageView=itemView.findViewById(R.id.list_image)
        var listDesc:TextView=itemView.findViewById(R.id.list_desc)
        var listLevel:TextView=itemView.findViewById(R.id.list_difficulty)
        var listBtn:TextView=itemView.findViewById(R.id.list_btn)

        override fun onClick(v: View?) {

        }
        init {
            listBtn.setOnClickListener(this)
        }


    }
    interface OnQuizListItemClicked {
        fun onItemClicked(position: Int)
    }
  /* class QuizViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
       View.OnClickListener {
       private val listImage: ImageView
       private val listTitle: TextView
       private val listDesc: TextView
       private val listLevel: TextView
       private val listBtn: Button
       override fun onClick(v: View?) {
           onQuizListItemClicked.onItemClicked(adapterPosition)
       }

       init {
           listImage = itemView.findViewById(R.id.list_image)
           listTitle = itemView.findViewById(R.id.list_title)
           listDesc = itemView.findViewById(R.id.list_desc)
           listLevel = itemView.findViewById(R.id.list_difficulty)
           listBtn = itemView.findViewById(R.id.list_btn)
           listBtn.setOnClickListener(this)
       }
   }

    interface OnQuizListItemClicked {
        fun onItemClicked(position: Int)
    }
*/
}*/
class SubjectsAdapter(onQuizListItemClicked: OnQuizListItemClicked) :
    RecyclerView.Adapter<SubjectsAdapter.QuizViewHolder?>() {
    private var subjectsModels: List<SubjectsModel>? = null
    private val onQuizListItemClicked: OnQuizListItemClicked
    fun setQuizListModels(subjectsModels: List<SubjectsModel>?) {
        this.subjectsModels = subjectsModels
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.single_list_item, parent, false)
        return QuizViewHolder(view,subjectsModels)
    }

    override fun onBindViewHolder(holder: QuizViewHolder, position: Int) {
        holder.listName.text= subjectsModels?.get(position)?.name
        val imageUrl= subjectsModels?.get(position)?.image
        Glide.with(holder.itemView.context).
        load(imageUrl).
        centerCrop().
        placeholder(R.drawable.placeholder_icon).
        into(holder.listImage)
        val listDescription=subjectsModels?.get(position)?.desc
        if (listDescription != null) {
            if (listDescription.length >150){
                listDescription.substring(0,150)
            }
            holder.listDesc.text =listDescription+"..."

        }

    }
    override fun getItemCount(): Int {
        return if (subjectsModels == null) {
            0
        } else {
            subjectsModels!!.size
        }
    }

    inner class QuizViewHolder(itemView: View, subjectsModels: List<SubjectsModel>?) : RecyclerView.ViewHolder(itemView),

        View.OnClickListener {
        var quizListModels=subjectsModels
        var listName:TextView=itemView.findViewById(R.id.list_title)
        var listImage:ImageView=itemView.findViewById(R.id.list_image)
        var listDesc:TextView=itemView.findViewById(R.id.list_desc)
        var listLevel:TextView=itemView.findViewById(R.id.list_difficulty)
        var listBtn:TextView=itemView.findViewById(R.id.list_btn)
        override fun onClick(v: View) {
            quizListModels?.get(adapterPosition)?.let { it.subject_id?.let { it1 ->
                onQuizListItemClicked.onItemClicked(
                    it1
                )
            } }
        }

        init {

            listBtn.setOnClickListener(this)

        }
    }

    interface OnQuizListItemClicked {
        fun onItemClicked(subject: String)
    }

    init {
        this.onQuizListItemClicked = onQuizListItemClicked
    }
}
