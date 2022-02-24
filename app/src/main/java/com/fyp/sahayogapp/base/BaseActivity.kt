package com.fyp.sahayogapp.base

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.graphics.Rect
import android.os.Build
import android.view.*
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.fyp.sahayogapp.R
import com.fyp.sahayogapp.base.interfaces.OnActivityBackPressed


abstract class BaseActivity : AppCompatActivity() {
    //private var onBackPressedListener: OnActivityBackPressed? = null
    private var view: RelativeLayout? = null
    private val progressBar: ProgressBar? = null
    private var loadingLayout: RelativeLayout? = null

    private val onBackPressListener = arrayListOf<OnActivityBackPressed>()

    override fun setContentView(layoutResID: Int) {
        view = LayoutInflater.from(this).inflate(R.layout.base_layout, null) as RelativeLayout
        val frameLayout = view!!.findViewById<FrameLayout>(R.id.content_view)

        frameLayout.addView(layoutInflater.inflate(layoutResID, view, false))
        supportActionBar!!.hide()

        loadingLayout = view!!.findViewById(R.id.loading_layout)
        dismissProgress()
        super.setContentView(view)
    }

    fun setOnBackPressedListener(onBackPressedListener: OnActivityBackPressed) {
        //this.onBackPressedListener = onBackPressedListener
        this.onBackPressListener.add(onBackPressedListener)
    }

    private val TAG = "BaseActivity"

    override fun onBackPressed() {
        var called = false
        var isBackPressed = true
        for (l in onBackPressListener) {
            if (onBackPressListener.size == 0) {
                called = true
            }
            if (!l.onBackPressed()) {
                isBackPressed = false
                break
            }
            called = true
        }
        if (onBackPressListener.size == 0) {
            called = true
        }
        if (called) {
            if(isBackPressed){
                super.onBackPressed()
            }
        }
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val v = currentFocus
            val outRect = Rect()
            if (v == null) {
                return super.dispatchTouchEvent(event)
            }
            v.getGlobalVisibleRect(outRect)
            if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                v.clearFocus()
                dismissKeyboard(v)
            }
        }
        return super.dispatchTouchEvent(event)
    }


    fun dismissKeyboard(view: View) {
        val imm =
            (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun showProgress() {
        loadingLayout!!.animation = AnimationUtils.loadAnimation(
            applicationContext,
            android.R.anim.fade_in
        )
        loadingLayout!!.visibility = View.VISIBLE
    }

    fun dismissProgress() {
        loadingLayout!!.animation = AnimationUtils.loadAnimation(
            applicationContext,
            android.R.anim.fade_out
        )
        loadingLayout!!.visibility = View.GONE
    }

    fun showAlert(title: String?, message: String?) {
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("Ok", null)
            .show()
    }

    fun showAlert(
        title: String?,
        message: String?,
        btnPositive : String,
        listener: DialogInterface.OnClickListener?
    ) {
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setNegativeButton("Cancel",null)
            .setPositiveButton(btnPositive, listener)
            .show()
    }



}