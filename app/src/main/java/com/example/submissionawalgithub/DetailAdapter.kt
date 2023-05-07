package com.example.submissionawalgithub

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class DetailAdapter(
    fragmentActivity: FragmentActivity,
    private val fragment: MutableList<Fragment>
) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = fragment.size

    override fun createFragment(position: Int): Fragment = fragment[position]
}