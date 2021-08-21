package com.example.android.Resources.Database.Dosage

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.R
import com.example.android.Resources.Database.Medicine.Dosage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DosageListFragment(id: Long) : Fragment() {
    private lateinit var dosageListAdapter: DosageListAdapter
    private lateinit var dosageViewModel: AndroidViewModel
    private val medId = id

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.content_dosage, container, false)
        initView(view)
        return view
    }

    private fun initData() {
        dosageViewModel = ViewModelProvider(this).get(DosageViewModel::class.java)
        (dosageViewModel as DosageViewModel).dosageList.observe(this, Observer{
                dosages: List<Dosage> ->
            dosageListAdapter.setDosageList(dosages, medId)
        })
    }

    private fun initView(view: View) {
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerview_dosages)
        dosageListAdapter = DosageListAdapter(requireContext())
        recyclerView.adapter = dosageListAdapter
        recyclerView.layoutManager = LinearLayoutManager(context)
    }

    companion object {
        fun newInstance(id: Long): DosageListFragment = DosageListFragment(id)
    }
}