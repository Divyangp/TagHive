package com.app.taghive.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.app.taghive.R
import com.app.taghive.model.CryptoResponseItem

class CryptoAdapter(context: Context, private val cryptolist: List<CryptoResponseItem>) : BaseAdapter() {
    private val layoutInflater = LayoutInflater.from(context)

    override fun getCount(): Int {
        return cryptolist.size
    }

    @SuppressLint("SetTextI18n")
    override fun getView(position: Int, view: View?, viewGroup: ViewGroup?): View? {
        val viewHolder: ViewHolder
        val rowView: View?
        if (view == null) {
            rowView = layoutInflater.inflate(R.layout.raw_crypto, viewGroup, false)
            viewHolder = ViewHolder(rowView)
            rowView.tag = viewHolder
        } else {
            rowView = view
            viewHolder = rowView.tag as ViewHolder
        }

        val items=cryptolist[position]
        viewHolder.txtsymbol.text = items.baseAsset
        viewHolder.txtsymbolc.text = "/ "+items.quoteAsset
        viewHolder.txtprice.text = items.lastPrice
        viewHolder.txtvolume.text = items.volume
        viewHolder.txtcryptoname.text = items.baseAsset
        return rowView
    }

    override fun getItem(position: Int): Any {
        return 0
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    private class ViewHolder(view: View?) {
        var txtsymbol = view?.findViewById(R.id.txtsymbol) as TextView
        var txtsymbolc = view?.findViewById(R.id.txtsymbolc) as TextView
        var txtprice = view?.findViewById(R.id.txtprice) as TextView
        var txtvolume = view?.findViewById(R.id.txtvolume) as TextView
        val txtcryptoname = view?.findViewById(R.id.txtcryptoname) as TextView
    }
}