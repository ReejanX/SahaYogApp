package com.fyp.sahayogapp.base

import android.content.Context
import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import com.fyp.sahayogapp.base.interfaces.OnActivityBackPressed

abstract class BaseFragment : Fragment(),
    OnActivityBackPressed {
    private  val TAG = "BaseFragment"
    private var mActivity: BaseActivity? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseActivity) {
            mActivity = context
        }
        if (activity is BaseActivity) {
            mActivity = activity as BaseActivity?
        }
        if (mActivity != null) {
            mActivity!!.setOnBackPressedListener(this)
        }
        Log.i(TAG, "onAttach: ${this}")

    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {

        super.onViewCreated(view, savedInstanceState)
    }

    override fun onBackPressed(): Boolean {
        return true
    }


    fun showProgress() {
        mActivity!!.showProgress()
    }

    fun dismissProgress() {
        mActivity!!.dismissProgress()
    }

    fun showAlert(title: String?, message: String?) {
        mActivity!!.showAlert(title, message)
    }

    fun showAlert(title: String?, message: String?,btnPositive:String, listener:DialogInterface.OnClickListener?){
        mActivity!!.showAlert(title, message,btnPositive, listener)
    }



}