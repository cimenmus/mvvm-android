package com.plumbers.mvvm.ui.movie.movies

import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.plumbers.mvvm.R
import com.plumbers.mvvm.common.data.Result
import com.plumbers.mvvm.common.data.succeeded
import com.plumbers.mvvm.databinding.FragmentPopularMoviesBinding
import com.plumbers.mvvm.ui.common.DataObserver
import com.plumbers.mvvm.ui.common.EndlessRecyclerViewScrollListener
import com.plumbers.mvvm.ui.common.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_popular_movies.*

@AndroidEntryPoint
class PopularMoviesFragment : BaseFragment<FragmentPopularMoviesBinding, PopularMoviesViewModel>() {

    private lateinit var movieRecyclerAdapter: MovieRecyclerAdapter

    override fun getViewModelKey() = PopularMoviesViewModel::class.java

    override fun getLayoutRes() = R.layout.fragment_popular_movies

    override fun getTitleRes() = R.string.title_movies

    override fun initViews() {
        super.initViews()
        initRecyclerView()
    }

    override fun initLogic() {
        super.initLogic()
        if (viewModel.movieList.isEmpty()) {
            viewModel.getMovies()
        }
    }

    override fun initObservers() {
        super.initObservers()
        val dataObserver = DataObserver<Result<Any>>(
            lifecycle = lifecycle,
            view = this
        ) {
            if (it.succeeded) {
                movieRecyclerAdapter.notifyDataSetChanged()
            }
        }
        viewModel.moviesLiveData.observe(viewLifecycleOwner, dataObserver)
    }

    private fun initRecyclerView() {

        val linearLayoutManager = LinearLayoutManager(context)
        popularMoviesRecyclerView.layoutManager = linearLayoutManager

        val dividerItemDecoration = DividerItemDecoration(context, linearLayoutManager.orientation)
        popularMoviesRecyclerView.addItemDecoration(dividerItemDecoration)

        movieRecyclerAdapter = MovieRecyclerAdapter(
            movieList = viewModel.movieList,
            onClicked = {
                val actionDetail =
                    PopularMoviesFragmentDirections.actionMovieListToMovieDetails(movie = viewModel.movieList[it])
                findNavController().navigate(actionDetail)
            }
        )
        popularMoviesRecyclerView.adapter = movieRecyclerAdapter

        popularMoviesRecyclerView.addOnScrollListener(object :
                EndlessRecyclerViewScrollListener(linearLayoutManager) {
                override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                    viewModel.getMovies(showLoading = false)
                }
            })
    }
}
