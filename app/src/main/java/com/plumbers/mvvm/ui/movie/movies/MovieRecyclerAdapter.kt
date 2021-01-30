package com.plumbers.mvvm.ui.movie.movies

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.plumbers.mvvm.R
import com.plumbers.mvvm.data.model.MovieModel
import com.plumbers.mvvm.ui.common.RecyclerItemClickListener
import com.plumbers.mvvm.ui.common.ext.loadTmdbImage

class MovieRecyclerAdapter(
    private val movieList: MutableList<MovieModel>,
    private val clickListener: RecyclerItemClickListener
) : RecyclerView.Adapter<MovieRecyclerAdapter.ModelViewHolder>() {

    class ModelViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        private val imageView: ImageView = view.findViewById(R.id.movieImageView)
        private val titleTextView: TextView = view.findViewById(R.id.titleTextView)
        private val releaseDateTextView: TextView = view.findViewById(R.id.releaseDateTextView)
        private val averageTextView: TextView = view.findViewById(R.id.averageTextView)

        fun bindItems(
            movie: MovieModel,
            position: Int,
            clickListener: RecyclerItemClickListener
        ) {
            movie.apply {
                imageView.loadTmdbImage(url = posterImagePath)
                titleTextView.text = title
                releaseDateTextView.text = releaseDate
                averageTextView.text = average.toString()

                view.setOnClickListener {
                    clickListener.onItemClick(view = view, position = position)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModelViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_movie, parent, false)
        return ModelViewHolder(view)
    }

    override fun onBindViewHolder(holder: ModelViewHolder, position: Int) =
        holder.bindItems(
            movie = movieList[position],
            position = position,
            clickListener = clickListener
        )

    override fun getItemCount(): Int = movieList.size
}
