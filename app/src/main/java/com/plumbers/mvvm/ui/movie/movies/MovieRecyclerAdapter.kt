package com.plumbers.mvvm.ui.movie.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.plumbers.mvvm.data.model.MovieModel
import com.plumbers.mvvm.databinding.RowMovieBinding
import com.plumbers.mvvm.ui.common.RecyclerItemClickListener

class MovieRecyclerAdapter(
    private val movieList: MutableList<MovieModel>,
    private val onClicked: RecyclerItemClickListener
) : RecyclerView.Adapter<MovieRecyclerAdapter.MovieViewHolder>() {

    class MovieViewHolder(
        private val binding: RowMovieBinding,
        private val onClicked: RecyclerItemClickListener
    ) : RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(
                parent: ViewGroup,
                onClicked: RecyclerItemClickListener
            ): MovieViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RowMovieBinding.inflate(layoutInflater, parent, false)
                return MovieViewHolder(binding = binding, onClicked = onClicked)
            }
        }

        init {
            binding.root.setOnClickListener { onClicked(adapterPosition) }
        }

        fun bind(movie: MovieModel) {
            binding.movie = movie
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder =
        MovieViewHolder.from(parent = parent, onClicked = onClicked)

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) =
        holder.bind(movie = movieList[position])

    override fun getItemCount(): Int = movieList.size
}
