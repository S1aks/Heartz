package com.s1aks.heartz.ui.main

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.s1aks.heartz.R
import com.s1aks.heartz.databinding.FragmentMainBinding
import com.s1aks.heartz.ui.base.BaseFragment

class MainFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {

    override fun readArguments(bundle: Bundle) {
    }

    override fun initView() {
    }

    override fun initListeners() {
//        findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
    }

    override fun initObservers() {
    }

}