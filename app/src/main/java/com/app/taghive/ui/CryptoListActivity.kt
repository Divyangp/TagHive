package com.app.taghive.ui

import com.app.taghive.adapters.CryptoAdapter
import android.app.Activity
import android.app.ProgressDialog
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.app.taghive.R
import com.app.taghive.model.CryptoResponseItem
import com.app.taghive.repository.AppRepository
import com.app.taghive.util.Resource
import com.app.taghive.viewmodel.CryptoViewModel
import com.app.taghive.viewmodel.ViewModelProviderFactory
import kotlinx.android.synthetic.main.activity_cryptolist.*

class CryptoListActivity : AppCompatActivity() {

    private lateinit var cryptoViewModel: CryptoViewModel
    private lateinit var adapter: CryptoAdapter
    @Suppress("DEPRECATION")
    private lateinit var pDialog: ProgressDialog
    private lateinit var arrayList:ArrayList<CryptoResponseItem>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cryptolist)
        init()
        arrayList= ArrayList()
        getData()
        swipeContainer.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(this, R.color.black))
        swipeContainer.setColorSchemeColors(Color.WHITE)

        swipeContainer.setOnRefreshListener {
            arrayList.clear()
            getData()
            adapter= CryptoAdapter(applicationContext,arrayList)
            listview.adapter=adapter
            swipeContainer.isRefreshing = false
        }

        listview.setOnItemClickListener { _, _, position, _ ->
            val intent = Intent(applicationContext, CryptoDetailsActivity::class.java)
            intent.putExtra("SYMBOL", arrayList[position].symbol)
            intent.putExtra("BASEASSET", arrayList[position].baseAsset)
            intent.putExtra("QUOTEASSET", arrayList[position].quoteAsset)
            startActivity(intent)
        }

    }

    private fun init() {
        val repository = AppRepository()
        val factory = ViewModelProviderFactory(application, repository)
        this.cryptoViewModel = ViewModelProvider(this, factory)[CryptoViewModel::class.java]
    }


    private fun getData() {
        cryptoViewModel.getCryptolist()
        cryptoViewModel.cryptoGetResponse.observe(this) { event ->
            event.getContentIfNotHandled()?.let { response ->
                when (response) {
                    is Resource.Success -> {
                        hidePDialog()
                        response.data?.let {
                            arrayList = response.data
                            adapter = CryptoAdapter(applicationContext, arrayList)
                            listview.adapter = adapter
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


