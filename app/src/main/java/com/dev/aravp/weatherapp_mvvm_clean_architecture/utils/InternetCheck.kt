package com.dev.aravp.weatherapp_mvvm_clean_architecture.utils

import android.app.AlertDialog
import android.content.Context
import android.net.ConnectivityManager
import com.dev.aravp.weatherapp_mvvm_clean_architecture.R

/**
 * Object providing utility functions for checking and handling internet connectivity.
 */
object InternetCheck {

    /**
     * Checks if the device is connected to the internet.
     *
     * @param context The context used to access system services.
     * @return `true` if connected to the internet, otherwise `false`.
     */
    fun isConnectedToInternet(context: Context) : Boolean {
        val connectivity =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivity.activeNetworkInfo

        if (activeNetwork != null && activeNetwork.isConnected)
            return true
        else {
            showNoInternetDialog(context)
        }

        return false
    }


    /**
     * Displays a dialog notifying the user about the lack of internet connectivity.
     *
     * @param context The context used to create the dialog.
     */
    private fun showNoInternetDialog(context: Context) {
        val alertDialog = AlertDialog.Builder(context)
        alertDialog.setTitle(context.resources.getString(R.string.app_name))
        alertDialog.setCancelable(false)
        alertDialog.setMessage(context.resources.getString(R.string.internet_connection))
        alertDialog.setPositiveButton(
            context.resources.getString(android.R.string.ok)
        ) { dialog, which -> dialog.dismiss() }
        alertDialog.show()
    }
}