package com.s1aks.heartz.ui.main

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.s1aks.heartz.data.HeartData
import com.s1aks.heartz.databinding.FragmentMainBinding
import com.s1aks.heartz.ui.base.BaseFragment

class MainFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate),
    OnItemClickListener, OnSaveClickListener {
    private var mainViewModel: MainViewModel? = null
    private var adapter: MainAdapter? = null
    private val addDataDialog by lazy {
        AddDataDialogFragment(this)
    }

    override fun readArguments(bundle: Bundle) {
    }

    override fun initView() {
        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        adapter = MainAdapter(requireContext(), this)
        binding.recycler.adapter = adapter
    }

    override fun initListeners() {
        binding.fab.setOnClickListener {
            addDataDialog.show(requireActivity().supportFragmentManager, "")
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
        }
//        findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun initObservers() {
        mainViewModel?.data?.observe(viewLifecycleOwner) {
            adapter?.setData(it)
        }
        mainViewModel?.getData()
    }

    override fun onItemClick(position: Int) {
        // TODO: Open item
    }

    override fun destroy() {
        mainViewModel = null
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onSaveClicked(topPressure: String, lowPressure: String, pulse: String) {
        mainViewModel?.saveData(
            HeartData(
                null,
                "",
                System.currentTimeMillis().toString(),
                topPressure.toInt(),
                lowPressure.toInt(),
                pulse.toInt()
            )
        )
    }
}