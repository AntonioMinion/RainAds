package com.rainads.rainadsapp.util.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import com.rainads.rainadsapp.util.CustomModelDisplayName

class CustomArrayAdapter<T : CustomModelDisplayName>(context: Context,
                                                     @LayoutRes private val layoutResource: Int,
                                                     @IdRes private val textViewResourceId: Int = 0,
                                                     values: List<T>) : ArrayAdapter<T>(context, layoutResource, values) {

    @LayoutRes
    var dropdownLayoutResource: Int = 0
    @IdRes
    var dropdownTextResource: Int = 0

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = createViewFromResource(convertView, parent, layoutResource, textViewResourceId)

        return bindData(getItem(position), view)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = createViewFromResource(convertView, parent, dropdownLayoutResource, dropdownTextResource)

        return bindData(getItem(position), view)
    }


    private fun createViewFromResource(convertView: View?, parent: ViewGroup, layoutResource: Int, textId: Int): TextView {
        val context = parent.context
        val view = convertView
                ?: LayoutInflater.from(context).inflate(layoutResource, parent, false)
        return try {
            if (textId == 0) {
                view as TextView
            } else {
                view.findViewById(textId)
                        ?: throw RuntimeException("Failed to find view with ID " + "${context.resources.getResourceName(textId)} in item layout")
            }
        } catch (ex: ClassCastException) {
            Log.e("CustomArrayAdapter", "You must supply a resource ID for a TextView")
            throw IllegalStateException("ArrayAdapter requires the resource ID to be a TextView", ex)
        }
    }

    private fun bindData(value: T, view: TextView): TextView {
        view.text = value.displayName
        return view
    }
}