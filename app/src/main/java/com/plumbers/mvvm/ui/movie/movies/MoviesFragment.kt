package com.plumbers.mvvm.ui.movie.movies

import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.plumbers.mvvm.R
import com.plumbers.mvvm.common.data.DataResult
import com.plumbers.mvvm.databinding.FragmentMoviesBinding
import com.plumbers.mvvm.ui.common.DataObserver
import com.plumbers.mvvm.ui.common.EndlessRecyclerViewScrollListener
import com.plumbers.mvvm.ui.common.RecyclerItemClickListener
import com.plumbers.mvvm.ui.common.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_movies.*

@AndroidEntryPoint
class MoviesFragment : BaseFragment<FragmentMoviesBinding, MoviesViewModel>() {

    private lateinit var movieRecyclerAdapter: MovieRecyclerAdapter

    override fun getViewModelKey() = MoviesViewModel::class.java

    override fun getLayoutRes() = R.layout.fragment_movies

    override fun getTitleRes() = R.string.title_movies

    override fun initViews() {
        super.initViews()
        initRecyclerView()
    }

    override fun initLogic() {
        super.initLogic()
        if (viewModel.movieList.isEmpty()){
            viewModel.getMovies()
        }
    }

    override fun initObservers() {
        super.initObservers()
        val dataObserver = DataObserver<DataResult<Any>>(
            lifecycle = lifecycle,
            view = this
        ) {
            if (it is DataResult.Success) {
                movieRecyclerAdapter.notifyDataSetChanged()
            }
        }
        viewModel.moviesLiveData.observe(viewLifecycleOwner, dataObserver)
    }

    private fun initRecyclerView() {

        val linearLayoutManager = LinearLayoutManager(context)
        movieRecyclerView.layoutManager = linearLayoutManager

        val dividerItemDecoration = DividerItemDecoration(context, linearLayoutManager.orientation)
        movieRecyclerView.addItemDecoration(dividerItemDecoration)

        movieRecyclerAdapter = MovieRecyclerAdapter(
            movieList = viewModel.movieList,
            object : RecyclerItemClickListener {
                override fun onItemClick(view: View, position: Int) {
                    val actionDetail = MoviesFragmentDirections.actionMovieListToMovieDetails(movie = viewModel.movieList[position])
                    findNavController().navigate(actionDetail)
                }
            }
        )
        movieRecyclerView.adapter = movieRecyclerAdapter

        movieRecyclerView.addOnScrollListener(object : EndlessRecyclerViewScrollListener(linearLayoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                viewModel.getMovies(showLoading = false)
            }
        })
    }
}
