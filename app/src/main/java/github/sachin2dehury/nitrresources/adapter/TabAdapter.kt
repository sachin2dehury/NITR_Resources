package github.sachin2dehury.nitrresources.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import github.sachin2dehury.nitrresources.component.AppCore.pageList
import github.sachin2dehury.nitrresources.fragment.PageFragment
import github.sachin2dehury.nitrresources.fragment.TabFragment

class TabAdapter(fragment: TabFragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return pageList.size
    }

    override fun createFragment(position: Int): Fragment {
        return PageFragment(position)
    }
}