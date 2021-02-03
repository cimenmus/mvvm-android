package com.plumbers.mvvm.ui.movie.moviedetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.plumbers.mvvm.data.model.MovieCastModel
import com.plumbers.mvvm.databinding.RowCastBinding
import com.plumbers.mvvm.ui.common.RecyclerItemClickListener

class MovieCastRecyclerAdapter(
    private val castList: MutableList<MovieCastModel>,
    private val onClicked: RecyclerItemClickListener
) : RecyclerView.Adapter<MovieCastRecyclerAdapter.CastViewHolder>() {

    class CastViewHolder(
        private val binding: RowCastBinding,
        private val onClicked: RecyclerItemClickListener
    ) : RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(
                parent: ViewGroup,
                onClicked: RecyclerItemClickListener
            ): CastViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RowCastBinding.inflate(layoutInflater, parent, false)
                return CastViewHolder(binding = binding, onClicked = onClicked)
            }
        }

        init {
            binding.root.setOnClickListener { onClicked(adapterPosition) }
        }

        fun bind(cast: MovieCastModel) {
            binding.cast = cast
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastViewHolder =
        CastViewHolder.from(parent = parent, onClicked = onClicked)

    override fun onBindViewHolder(holder: CastViewHolder, position: Int) =
        holder.bind(cast = castList[position])

    override fun getItemCount(): Int = castList.size
}
