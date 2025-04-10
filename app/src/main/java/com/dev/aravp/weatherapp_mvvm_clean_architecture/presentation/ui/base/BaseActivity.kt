package com.dev.aravp.weatherapp_mvvm_clean_architecture.presentation.ui.base

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.viewbinding.ViewBinding
import com.dev.aravp.weatherapp_mvvm_clean_architecture.utils.InternetCheck

/**
 * Abstract base class for Activities, providing common functionality such as view binding, network monitoring, and utility methods.
 *
 * @param VB The type of the ViewBinding associated with the activity.
 */
abstract class BaseActivity<VB: ViewBinding> : AppCompatActivity() {

    /**
     * The binding associated with the layout of this activity.
     */
    open lateinit var binding: VB

    /**
     * LiveData to observe changes in network connectivity.
     */


    /**
     * Lazily initialized ConnectivityManager instance.
     */
    private val connectivityManager: ConnectivityManager by lazy {
        getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    private var mLastClickTime = 0L
    private val NETWORK_TAG = "NETWORK_BROADCAST"

    /**
     * Returns the layout resource ID for the activity.
     */
    @LayoutRes
    abstract fun getLayoutRes(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = setViewBinding()
        setContentView(binding.root)

        initViews()
        setObserver()
        initListeners()

    }

    /**
     * Initialize views of the activity.
     */
    abstract fun initViews()

    /**
     * Initialize listeners of the activity.
     */
    abstract fun initListeners()

    /**
     * Set observers for LiveData, etc.
     */
    abstract fun setObserver()

    /**
     * Sets the ViewBinding for the activity.
     */
    abstract fun setViewBinding(): VB

    /**
     * Hides the keyboard.
     *
     * @param input The view that has focus.
     */
    open fun hideKeyBoard(input: View?) {
        input?.let {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(input.windowToken, 0)
        }
    }

    /**
     * Checks if internet connectivity is available.
     *
     * @return `true` if connected to the internet, `false` otherwise.
     */
    fun isInternetConnected(): Boolean {
        return InternetCheck.isConnectedToInternet(this)
    }
}