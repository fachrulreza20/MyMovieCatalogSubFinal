package com.example.mymoviecatalogsubfinal.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.example.mymoviecatalogsubfinal.fragment.FavMovieFragment
import com.example.mymoviecatalogsubfinal.fragment.FavTVFragment



class MyPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                FavMovieFragment()
            }
            else -> {
                return FavTVFragment()
            }
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 -> "Favorit Movie"
            else -> {
                return "Favorit TV Show";
            }
        }
    }
}