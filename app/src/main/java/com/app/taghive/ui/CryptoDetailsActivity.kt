package com.app.taghive.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ProgressDialog
import android.content.ContextWrapper
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.app.taghive.R
import com.app.taghive.repository.AppRepository
import com.app.taghive.util.Resource
import com.app.taghive.viewmodel.GetCryptoViewModel
import com.app.taghive.viewmodel.ViewModelProviderFactory
import kotlinx.android.synthetic.main.activity_crypto_details.*
import kotlinx.android.synthetic.main.tool_bar.*


class CryptoDetailsActivity : AppCompatActivity() {

    private lateinit var getcryptoViewModel: GetCryptoViewModel
    lateinit var pDialog: ProgressDialog
    private lateinit var symbol:String
    private lateinit var baseasset:String
    private lateinit var quoteasset:String
    private lateinit var tooltext:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crypto_details)
        init()
        symbol = intent.getStringExtra("SYMBOL")!!
        baseasset = intent.getStringExtra("BASEASSET")!!
        quoteasset = intent.getStringExtra("QUOTEASSET")!!
        setSupportActionBar(tool_bar as Toolbar?)
        back.setOnClickListener { finish() }
        back.isVisible=true
        tooltext=baseasset.uppercase()+"/"+quoteasset.uppercase()
        txtname.text = tooltext
        getDetails(symbol)
    }

    private fun init() {
        val repository = AppRepository()
        val factory = ViewModelProviderFactory(application, repository)
        this.getcryptoViewModel = ViewModelProvider(this, factory)[GetCryptoViewModel::class.java]
    }

    @SuppressLint("SetTextI18n")
    private fun getDetails(symbol :String) {
        getcryptoViewModel.getCryptoDetaillist(symbol)
        getcryptoViewModel.cryptoGetDetailResponse.observe(this) { event ->
            event.getContentIfNotHandled()?.let { response ->
                when (response) {
                    is Resource.Success -> {
                        hidePDialog()
                        response.data?.let { CryptoResponseDetails ->
                            txtcname.setText(CryptoResponseDetails.baseAsset)
                            txtnamec.setText("Name: ")
                            txtcopenprice.setText("Open Price : " + CryptoResponseDetails.openPrice)
                            txtclowprice.setText("Low Price : " + CryptoResponseDetails.lowPrice)
                            txtchighprice.setText("High Price : " + CryptoResponseDetails.highPrice)
                            txtclastprice.setText("Last Price : " + CryptoResponseDetails.lastPrice)
                            txtcvolume.setText("Volume : " + CryptoResponseDetails.volume)
                        }
                    }

                    is Resource.Error -> {
                        hidePDialog()
                        response.message?.let { message ->
                            Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
                        }
                    }

                    is Resource.Loading -> {
                        showdialog()
                    }
                }
            }
        }
    }

    private fun hidePDialog() {

        if (pDialog.isShowing()) {
            val context = (pDialog.getContext() as ContextWrapper).baseContext
            if (context is Activity) {
                if (!context.isFinishing) pDialog.dismiss()
            } else pDialog.dismiss()
        }
    }

    @Suppress("DEPRECATION")
    private fun showdialog() {
        pDialog = ProgressDialog(this)
        pDialog.setMessage("Loading...")
        pDialog.setCancelable(false)
        pDialog.setCanceledOnTouchOutside(false)
        pDialog.show()
    }
}