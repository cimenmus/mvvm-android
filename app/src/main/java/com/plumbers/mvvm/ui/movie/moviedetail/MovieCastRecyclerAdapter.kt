package com.plumbers.mvvm.ui.movie.moviedetail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.plumbers.mvvm.R
import com.plumbers.mvvm.data.model.MovieCastModel
import com.plumbers.mvvm.ui.common.RecyclerItemClickListener
import com.plumbers.mvvm.ui.common.ext.loadTmdbImage

class MovieCastRecyclerAdapter(
    private val castList: MutableList<MovieCastModel>,
    private val clickListener: RecyclerItemClickListener
) : RecyclerView.Adapter<MovieCastRecyclerAdapter.ModelViewHolder>() {

    class ModelViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        private val imageView: ImageView = view.findViewById(R.id.profileImageView)
        private val artistNameTextView: TextView = view.findViewById(R.id.artistNameTextView)
        private val characterNameTextView: TextView = view.findViewById(R.id.characterNameTextView)

        fun bindItems(
            castModel: MovieCastModel,
            position: Int,
            clickListener: RecyclerItemClickListener
        ) {
            castModel.apply {
                imageView.loadTmdbImage(url = imagePath)
                artistNameTextView.text = name
                characterNameTextView.text = character

                view.setOnClickListener {
                    clickListener.onItemClick(view = view, position = position)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModelViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_cast, parent, false)
        return ModelViewHolder(view)
    }

    override fun onBindViewHolder(holder: ModelViewHolder, position: Int) =
        holder.bindItems(
            castModel = castList[position],
            position = position,
            clickListener = clickListener
        )

    override fun getItemCount(): Int = castList.size
}