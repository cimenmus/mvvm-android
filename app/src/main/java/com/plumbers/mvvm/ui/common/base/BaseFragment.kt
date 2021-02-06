package com.plumbers.mvvm.ui.common.base

import android.os.Bundle
import android.view.*
import androidx.annotation.LayoutRes
import androidx.annotation.MenuRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.plumbers.mvvm.data.AppError
import com.plumbers.mvvm.ui.common.DialogUtils
import com.plumbers.mvvm.ui.common.autoCleared
import javax.inject.Inject

abstract class BaseFragment<VB : ViewDataBinding, VM : ViewModel> : Fragment(), BaseView {

    val viewModel: VM by lazy { ViewModelProvider(this).get(getViewModelKey()) }
    var binding by autoCleared<VB>()

    @Inject
    lateinit var dialogUtils: DialogUtils

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, getLayoutRes(), container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        readDataFromArguments()
        setUpActionBar()
        initViews()
        initObservers()
        initLogic()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        getMenuRes()?.let {
            inflater.inflate(it, menu)
        }
    }

    @LayoutRes
    protected abstract fun getLayoutRes(): Int

    @StringRes
    protected open fun getTitleRes(): Int? = null

    protected open fun getTitle(): String? = null

    @MenuRes
    protected open fun getMenuRes(): Int? = null

    protected abstract fun getViewModelKey(): Class<VM>

    open fun readDataFromArguments() {}

    open fun initViews() {}

    open fun initObservers() {}

    open fun initLogic() {}

    private fun setUpActionBar() {
        getTitleRes()?.let {
            setActionBarTitle(title = getString(it))
        }
        getTitle()?.let {
            setActionBarTitle(title = it)
        }
    }

    private fun setActionBarTitle(title: String?) {
        title?.let {
            if (activity is AppCompatActivity) {
                (activity as AppCompatActivity).supportActionBar?.title = it
            }
        }
    }

    override fun showLoading() {
        dialogUtils.showProgressDialog(context = context)
    }

    override fun hideLoading() {
        dialogUtils.hideProgressDialog()
    }

    override fun showError(error: AppError) {
        dialogUtils.showAlertDialog(context = context, error = error)
    }
}
