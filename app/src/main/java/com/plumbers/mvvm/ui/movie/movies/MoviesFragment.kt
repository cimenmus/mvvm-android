package com.plumbers.mvvm.ui.movie.movies

import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.plumbers.mvvm.R
import com.plumbers.mvvm.databinding.FragmentMoviesBinding
import com.plumbers.mvvm.ui.common.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_movies.*

class MoviesFragment: BaseFragment<FragmentMoviesBinding, MoviesViewModel>() {

    override fun getViewModelKey() = MoviesViewModel::class.java

    override fun getLayoutRes() = R.layout.fragment_movies

    override fun initViews() {
        super.initViews()
        moviesTextView.setOnClickListener {
            val actionDetail = MoviesFragmentDirections.actionMovieListToMovieDetails(movieId = 1234)
            findNavController().navigate(actionDetail)
        }
    }

    override fun initLogic() {
        super.initLogic()
        viewModel.getMovies()
    }

    override fun initObservers() {
        super.initObservers()
        viewModel.moviesLiveData.observe(viewLifecycleOwner, Observer {
            print(it)
        })
    }

}