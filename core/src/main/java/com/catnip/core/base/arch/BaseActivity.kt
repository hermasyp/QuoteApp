package com.catnip.core.base.arch

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.catnip.core.R
import com.catnip.core.base.exception.ApiErrorException
import com.catnip.core.base.exception.NoInternetConnectionException
import com.catnip.core.base.wrapper.ViewResource

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

    override fun <T : ViewResource<*>> handleData(viewResource: T) {
        viewResource.let {
            resetView()
            when (viewResource) {
                is ViewResource.Success<*> -> {
                    showContent(true).also {
                        showData(viewResource.data)
                    }
                }
                is ViewResource.Empty<*> -> {
                    showEmptyData(true)
                }
                is ViewResource.Loading<*> -> {
                    showLoading(true)
                }
                is ViewResource.Error<*> -> {
                    showError(true, viewResource.exception)
                }
            }
        }
    }


    private fun resetView() {
        showContent(false)
        showLoading(false)
        showError(false)
        showEmptyData(false)
    }

    fun enableHomeAsBack() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

}