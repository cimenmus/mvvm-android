package com.plumbers.mvvm.ui.movie.moviedetail

import com.plumbers.mvvm.R
import com.plumbers.mvvm.databinding.FragmentMovieDetailsBinding
import com.plumbers.mvvm.ui.common.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_movie_details.*

class MovieDetailsFragment: BaseFragment<FragmentMovieDetailsBinding, MovieDetailsViewModel>() {

    private var movieId: Int = 0

    override fun getViewModelKey() = MovieDetailsViewModel::class.java

    override fun getLayoutRes() = R.layout.fragment_movie_details

    override fun readDataFromArguments() {
        super.readDataFromArguments()
        arguments?.let {
            val safeArgs = MovieDetailsFragmentArgs.fromBundle(it)
            movieId = safeArgs.movieId
        }
    }

    override fun initViews() {
        super.initViews()
        movieDetailsTextView.text = "Movie Details of: $movieId"
    }

}