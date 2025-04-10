package com.dev.aravp.weatherapp_mvvm_clean_architecture.presentation.ui.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

/**
 * Abstract base class for Fragments, providing common functionality such as view initialization,
 * lifecycle handling, keyboard management, loading indicator, internet connectivity check, and utility methods.
 *
 * @param DB The type of ViewDataBinding associated with the Fragment.
 */
abstract class BaseFragment<DB : ViewDataBinding> : Fragment() {

    /**
     * The ViewDataBinding associated with the layout of this Fragment.
     */
    open lateinit var binding: DB

    /**
     * Indicates if the fragment is loaded.
     */
    var isLoaded = false

    /**
     * Reference to the parent BaseActivity.
     */
    private lateinit var baseActivity: BaseActivity<ViewBinding>

    /**
     * Returns the layout resource ID for the Fragment.
     */
    @LayoutRes
    abstract fun getLayoutRes(): Int

    private fun init(
        inflater: LayoutInflater, container: ViewGroup?
    ) {
        binding = DataBindingUtil.inflate(inflater, getLayoutRes(), container, false)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        init(inflater, container)
        super.onCreateView(inflater, container, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //if (savedInstanceState == null) {
        initViews()
        initListeners()
        setObserver()
        // }
    }

    /**
     * Initialize views of the fragment.
     */
    abstract fun initViews()

    /**
     * Initialize listeners of the fragment.
     */
    abstract fun initListeners()

    /**
     * Set observers for LiveData, etc.
     */
    abstract fun setObserver()

    /**
     * Method to handle back press if required.
     */
    open fun backPress() {}

    override fun onResume() {
        super.onResume()
        isLoaded = true
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseActivity<*>) {
            this.baseActivity = context as BaseActivity<ViewBinding>
        }
    }

    /**
     * Hides the keyboard.
     *
     * @param input The view that has focus.
     */
    fun hideKeyBoard(input: View?) {
        input?.let {
            val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(input.windowToken, 0)
        }
    }

    /**
     * Show the keyboard.
     *
     * @param input The view that has focus.
     */
    open fun showKeyBoard(input: View?) {
        input?.let {
            val imm =
                requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(input, InputMethodManager.SHOW_IMPLICIT)
        }
    }
}