package com.example.flo

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class BannerVPAdapter(fragment: Fragment) :FragmentStateAdapter(fragment){
    //이 클래스 내에서만 사용->private
    private val fragmentList:ArrayList<Fragment> =ArrayList()


    override fun getItemCount(): Int= fragmentList.size

    override fun createFragment(position: Int): Fragment=fragmentList[position]//0,1,2,3

    fun addFragment(fragment:Fragment){
        fragmentList.add(fragment)
        notifyItemInserted(fragmentList.size-1)
    }

}