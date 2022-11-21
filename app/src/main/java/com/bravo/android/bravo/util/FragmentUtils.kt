package com.bravo.android.bravo.util

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

class FragmentUtils(
    private val containerId: Int,
    private val fm: FragmentManager,
    private var fragments: Array<out Fragment>
) {
    var currentFragment: Fragment? = null
    var currentIndex: Int = 0
    val fragmentCount: Int = fragments.size

    private fun getFragmentByPosition(position: Int, args: Bundle?): Fragment {
        if (position < 0 || position >= fragmentCount)
            throw IllegalArgumentException("position invalid")
        return fragments[position]
    }

    fun removeFragment(fragment : Fragment){
        val ft = fm.beginTransaction()
        ft.remove(fragment)
        ft.commit()
    }

    fun setCurrentFragmentByPosition(position: Int) =
        setCurrentFragmentByPosition(position, Bundle())

    fun setCurrentFragmentByPosition(position: Int, args: Bundle?) {
        Log.e("asdf", "position $position called")
        try {
            var args = args
            currentIndex = position
            val ft = fm.beginTransaction()

            val tag = getFragmentTagByPosition(position)
            var fragment: Fragment? = fm.findFragmentByTag(tag)
            if (fragment == null) {
                Log.e("asdf", "null")
                if (args == null) args = Bundle()
                fragment = getFragmentByPosition(position, args)
                ft.add(containerId, fragment, tag)
            } else {
                Log.e("asdf", "notnull")
                if (currentFragment !== fragment) {
                    ft.show(fragment)
                }
            }
            postUpdateFragment(fm, ft, fragment, position)
            currentFragment = fragment
        } catch (e: Exception) {
            Log.e("asdf", e.toString())
            // Current Fragment Not initialized
            e.printStackTrace()
        }
    }

    private fun postUpdateFragment(
        fm: FragmentManager,
        ft: FragmentTransaction, fragment: Fragment,
        position: Int
    ) {
        Log.e("asdf", "postUpdateFragment")
        hideFragmentExcept(fm, ft, position)
        fragment.setMenuVisibility(true)
        fragment.userVisibleHint = true
        ft.commitNowAllowingStateLoss()
    }

    private fun hideFragmentExcept(
        fm: FragmentManager,
        ft: FragmentTransaction,
        position: Int
    ) {
        var fragment: Fragment? = null
        for (i in 0 until fragmentCount) {
            if (i != position) {
                fragment = fm.findFragmentByTag(getFragmentTagByPosition(i))
                if (fragment != null) {
                    ft.hide(fragment)
                    // todo
                    // fragment.setMenuVisibility(false);
                    fragment.userVisibleHint = false
                }
            }
        }
    }

    private fun getFragmentTagByPosition(position: Int): String {
        if (position < 0 || position >= fragmentCount)
            throw IllegalArgumentException("position is invalid.")

        return position.toString() + fragments[position].javaClass.simpleName
    }
}