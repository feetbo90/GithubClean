package com.example.githubappclean.core.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.githubappclean.follows.FollowsFragment

class SectionPageAdapter(activity: AppCompatActivity, private val username: String) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 1
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun createFragment(position: Int): Fragment {
        return FollowsFragment.newInstance(position + 1, username)
    }

}