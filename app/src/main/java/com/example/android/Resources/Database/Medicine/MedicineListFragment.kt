package com.example.android.Resources.Database.Medicine

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.R

class MedicineListFragment : Fragment() {
    private lateinit var medicineListAdapter: MedicineListAdapter
    private lateinit var medicineViewModel: AndroidViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.content_medicine, container, false)
        initView(view)
        return view
    }

    private fun initData() {
        medicineViewModel = ViewModelProvider(this).get(MedicinesViewModel::class.java)
        (medicineViewModel as MedicinesViewModel).medicineList.observe(this, Observer{
            medicines: List<Medicine> ->
            medicineListAdapter.setMedicineList(medicines)
        })
    }

    private fun initView(view: View) {
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerview_medicines)
        medicineListAdapter = MedicineListAdapter(requireContext())
        recyclerView.adapter = medicineListAdapter
        recyclerView.layoutManager = LinearLayoutManager(context)
    }

    companion object {
        fun newInstance(): MedicineListFragment = MedicineListFragment()
    }
}