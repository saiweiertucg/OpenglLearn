package com.practice.gang.opengllearn.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.practice.gang.opengllearn.R
import java.lang.RuntimeException

/**
 * Created by gang on 2018/6/12.
 */
class BaseFragment : Fragment() {

    companion object {
        val PKG: String = "pkg"

        fun newInstance(pkg: String): Fragment {
            var fragment: BaseFragment = BaseFragment()
            var args: Bundle = Bundle()
            args.putString(PKG, pkg)
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var pkg: String
    private lateinit var mListener: OnListFragmentInteractionListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            pkg = arguments.getString(PKG)
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        var view: RecyclerView = inflater?.inflate(R.layout.fragment_item_list, container, false) as RecyclerView

        var context: Context = view.context
        view.layoutManager = LinearLayoutManager(context)
        view.adapter = RecyclerViewAdapter(ItemFactory.createFragmentItems(mListener.getActivityClasses(pkg)), mListener)

        return view
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnListFragmentInteractionListener) {
            mListener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnListFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
    }

    interface OnListFragmentInteractionListener {
        fun onListFragmentInteraction(item: ItemFactory.FragmentItem)

        fun getActivityClasses(pkg: String): List<Class<*>>
    }
}