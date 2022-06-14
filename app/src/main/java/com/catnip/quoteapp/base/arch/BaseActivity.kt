package com.catnip.coingeckoapp.base.arch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.catnip.quoteapp.R
import com.catnip.quoteapp.base.exception.ApiErrorException
import com.catnip.quoteapp.base.exception.NoInternetConnectionException
import com.catnip.quoteapp.base.wrapper.ViewResource
import com.catnip.quoteapp.base.arch.BaseContract
import java.lang.Exception

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
abstract class BaseActivity<B : ViewBinding, VM : ViewModel>(
    val bindingFactory: (LayoutInflater) -> B
) : AppCompatActivity(), BaseContract.BaseView {

    protected lateinit var binding: B

    protected abstract val viewModel: VM


    open fun onRetrieveIntentData(extras: Bundle?) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onRetrieveIntentData(intent.extras)
        binding = bindingFactory(layoutInflater)
        setContentView(binding.root)
        initView()
        observeData()
    }

    abstract fun initView()

    override fun observeData() {
        //do nothing
    }

    override fun showEmptyData(isEmpty: Boolean) {
        //do nothing
    }

    override fun showContent(isContentVisible: Boolean) {
        //do nothing
    }

    override fun showLoading(isShowLoading: Boolean) {
        //do nothing
    }

    override fun <T> showData(data: T) {
        //do nothing
    }

    override fun showError(isErrorEnabled: Boolean, exception: Exception?) {
        if (isErrorEnabled) {
            Toast.makeText(this, getErrorMessageByException(exception), Toast.LENGTH_SHORT).show()
        }
    }

    protected fun getErrorMessageByException(exception: Exception?): String {
        return when (exception) {
            is NoInternetConnectionException -> this.getString(R.string.no_internet_connection)
            is ApiErrorException -> exception.message.orEmpty()
            else -> this.getString(R.string.unknown_error)
        }
    }

    override fun handleData(viewResource: ViewResource<*>?) {
        viewResource?.let {
            when (viewResource) {
                is ViewResource.Success -> {
                    showContent(true).also {
                        showEmptyData(false)
                        showData(viewResource.data)
                    }
                    showLoading(false)
                    showError(false)
                }
                is ViewResource.Empty -> {
                    showContent(false).also {
                        showEmptyData(true)
                    }
                    showLoading(false)
                    showError(false)
                }
                is ViewResource.Loading -> {
                    showContent(false).also {
                        showEmptyData(false)
                    }
                    showLoading(true)
                    showError(false)

                }
                is ViewResource.Error -> {
                    showContent(false).also {
                        showEmptyData(false)
                    }
                    showLoading(false)
                    showError(true, viewResource.exception)
                }
            }
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

}