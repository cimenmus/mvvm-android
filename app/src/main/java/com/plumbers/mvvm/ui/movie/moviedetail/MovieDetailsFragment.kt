package com.plumbers.mvvm.ui.movie.moviedetail

import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.plumbers.mvvm.R
import com.plumbers.mvvm.common.data.DataResult
import com.plumbers.mvvm.data.model.MovieCastModel
import com.plumbers.mvvm.data.model.MovieModel
import com.plumbers.mvvm.databinding.FragmentMovieDetailsBinding
import com.plumbers.mvvm.ui.common.DataObserver
import com.plumbers.mvvm.ui.common.RecyclerItemClickListener
import com.plumbers.mvvm.ui.common.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_movie_details.*
import kotlinx.android.synthetic.main.fragment_movies.*

@AndroidEntryPoint
class MovieDetailsFragment : BaseFragment<FragmentMovieDetailsBinding, MovieDetailsViewModel>() {

    private lateinit var movie: MovieModel
    private lateinit var castRecyclerAdapter: MovieCastRecyclerAdapter
    private var castList = mutableListOf<MovieCastModel>()

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

    override fun initViews() {
        super.initViews()
        initRecyclerView()
    }

    override fun initLogic() {
        super.initLogic()
        if (castList.isEmpty()) {
            viewModel.getCastOfTheMovie(movieId = movie.id)
        }
    }

    override fun initObservers() {
        super.initObservers()
        val dataObserver = DataObserver<DataResult<List<MovieCastModel>>>(
            lifecycle = lifecycle,
            view = this
        ) {
            if (it is DataResult.Success) {
                castList.addAll(it.data)
                castRecyclerAdapter.notifyDataSetChanged()
            }
        }
        viewModel.movieCastLiveData.observe(viewLifecycleOwner, dataObserver)
    }

    private fun initRecyclerView() {

        val linearLayoutManager = LinearLayoutManager(context)
        castRecyclerView.layoutManager = linearLayoutManager

        val dividerItemDecoration = DividerItemDecoration(context, linearLayoutManager.orientation)
        castRecyclerView.addItemDecoration(dividerItemDecoration)

        castRecyclerAdapter = MovieCastRecyclerAdapter(
            castList = castList,
            object : RecyclerItemClickListener {
                override fun onItemClick(view: View, position: Int) {
                    castList[position].apply {
                        val actionDetail =
                            MovieDetailsFragmentDirections.actionMovieDetailsToPersonDetails(
                                personId = id,
                                personName = name
                            )
                        findNavController().navigate(actionDetail)
                    }
                }
            }
        )
        castRecyclerView.adapter = castRecyclerAdapter
    }
}
