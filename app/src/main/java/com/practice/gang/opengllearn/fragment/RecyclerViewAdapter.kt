package com.practice.gang.opengllearn.fragment

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.practice.gang.opengllearn.R

/**
 * Created by gang on 2018/6/12.
 */
internal class RecyclerViewAdapter(items: List<ItemFactory.FragmentItem>, listener: BaseFragment.OnListFragmentInteractionListener) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    private var mValues: List<ItemFactory.FragmentItem> = items
    private var mListener: BaseFragment.OnListFragmentInteractionListener = listener

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mItem = mValues.get(position)
        var title: String = mValues.get(position).title
        holder.mTitle.setText(title.substring(0, title.indexOf("Activity")))
        holder.itemView.setOnClickListener {
            if (null != mListener) {
                mListener.onListFragmentInteraction(holder.mItem)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewAdapter.ViewHolder {
        var view: View = LayoutInflater.from(parent.context).inflate(R.layout.fragment_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mValues.size
    }

    internal class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var mItem: ItemFactory.FragmentItem
        var mTitle: TextView = itemView.findViewById<TextView>(R.id.title)
    }
}