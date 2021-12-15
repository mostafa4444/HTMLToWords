package com.example.htmlwords.base

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.htmlwords.utils.DialogBuilder

abstract class BaseActivity<VM : BaseViewModel, VB : ViewDataBinding> : AppCompatActivity() , View.OnClickListener{

    var isNetwork: Boolean = false

    open var dialogDismissAction: (() -> Unit)? = null

    protected var baseViewModel: VM? = null

    lateinit var baseViewBinding : VB

    protected abstract fun initView()

    protected abstract fun getContentView(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        baseViewBinding = DataBindingUtil.setContentView(this, getContentView())
        initializeViewModel()
        baseViewModel?.start()
        initView()
        subscribeLiveData()
    }

    protected abstract fun initializeViewModel()

    private fun showLongToast(msg: String){
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }

    private fun showShortToast(msg: String){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    fun showDialog(msg: String, btnTitle: String?, myAction: (() -> Unit)?) {
        runOnUiThread {
            DialogBuilder.showAdviceDialog(
                this as AppCompatActivity,
                btnTitle!!,
                msg,
                myAction
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        baseViewModel?.stop()
    }

    protected open fun subscribeLiveData() {
//        baseViewModel?.errorDialog?.observe(viewLifecycleOwner, {
////            showError(it, this.getString(R.string.ok), null)
//        })
//        baseViewModel?.successDialog?.observe(viewLifecycleOwner,  {
////            showSuccess(it, this.getString(R.string.ok))
//        })
    }

}