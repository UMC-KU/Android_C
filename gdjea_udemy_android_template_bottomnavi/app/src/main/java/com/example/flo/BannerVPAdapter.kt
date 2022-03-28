package com.example.flo

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class BannerVPAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    private val fragmentlist : ArrayList<Fragment> = ArrayList() //여러가지 프레그먼트들을 담아둘 공간 생성

    override fun getItemCount(): Int = fragmentlist.size //몇 개를 가져올 것이냐, fragmentlist의 size만큼 가져오겠다

    override fun createFragment(position: Int): Fragment = fragmentlist[position]

    fun addFragment(fragment: Fragment) { //처음에 비어있을 fragmentlist에 HomeFrgment에서 프레그먼트를 추가하기 위함
        fragmentlist.add(fragment)
        notifyItemInserted(fragmentlist.size - 1) //list안에 새로운 값이 추가되었을 때 뷰페이저에 이를 알림, 새로운 값이 추가되는 곳은 가장 끝이기 때문. 새로 추가된 것들도 같이 보여줘라
    }
}