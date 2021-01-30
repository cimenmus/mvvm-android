package com.plumbers.mvvm.ui.movie.moviedetail

import com.plumbers.mvvm.R
import com.plumbers.mvvm.data.model.MovieModel
import com.plumbers.mvvm.databinding.FragmentMovieDetailsBinding
import com.plumbers.mvvm.ui.common.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_movie_details.*

@AndroidEntryPoint
class MovieDetailsFragment : BaseFragment<FragmentMovieDetailsBinding, MovieDetailsViewModel>() {

    private lateinit var movie: MovieModel

    override fun getViewModelKey() = MovieDetailsViewModel::class.java

    override fun getLayoutRes() = R.layout.fragment_movie_details

    override fun getTitle() = movie.title

    override fun readDataFromArguments() {
        super.readDataFromArguments()
        arguments?.let {
            val safeArgs = MovieDetailsFragmentArgs.fromBundle(it)
            movie = safeArgs.movie
            binding.movie = movie
        }
    }
}
