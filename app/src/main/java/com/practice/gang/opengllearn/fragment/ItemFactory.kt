package com.practice.gang.opengllearn.fragment

/**
 * Created by gang on 2018/6/12.
 */
class ItemFactory {
    companion object {
        fun createFragmentItems(classes: List<Class<*>>): List<FragmentItem> {
            val items: MutableList<FragmentItem> = mutableListOf<FragmentItem>()
            for (item in classes) {
                items.add(FragmentItem(item.simpleName, item))
            }
            val list: List<FragmentItem> = items
            return list
        }
    }

    class FragmentItem(var title: String, var clazz: Class<*>) {

        fun getActivityClass(): Class<*> {
            return this.clazz
        }
    }
}