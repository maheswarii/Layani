package com.yps.layani.admin.ui.home

import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.yps.layani.admin.ui.detail.DetailInfoActivity
import com.yps.layani.admin.adapter.ComplaintAdapter
import com.yps.layani.admin.adapter.ListInfoAdapter
import com.yps.layani.admin.datalocal.InfoData
import com.yps.layani.admin.model.Complaint
import com.yps.layani.admin.model.Information
import com.yps.layani.admin.ui.detail.DetailComplaintActivity
import com.yps.layani.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: ComplaintAdapter
   private lateinit var homeViewModel: HomeViewModel

    private var listInfo: ArrayList<Information> = arrayListOf()
    //private var listKomplain: ArrayList<Complaint> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvInfo.setHasFixedSize(true)
        listInfo.addAll(InfoData.listData)
        showRecyclerInfoList()

        adapter = ComplaintAdapter()
        adapter.notifyDataSetChanged()

        binding.rvComplaint.layoutManager = LinearLayoutManager(context)
        binding.rvComplaint.adapter = adapter

        homeViewModel = ViewModelProvider(this@HomeFragment, ViewModelFactory(context?.applicationContext as Application)).get(HomeViewModel::class.java)
        homeViewModel.loadSearchUser().observe(viewLifecycleOwner, { loadUsers ->
            if (loadUsers != null) {
                adapter.setData(loadUsers)
                showLoading(false)
            }
        })

        adapter.setOnItemClickCallback(object : ComplaintAdapter.OnItemClickCallback {

            override fun onItemClicked(data: Complaint) {
                val intent = Intent(context, DetailComplaintActivity::class.java)
                startActivity(intent)
            }

        })
    }

    private fun showRecyclerInfoList() {
        binding.rvInfo.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val listInfoAdapter = ListInfoAdapter(listInfo)
        binding.rvInfo.adapter = listInfoAdapter

        listInfoAdapter.setOnItemClickCallback(object : ListInfoAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Information) {
                showSelectedEvent(data)
            }
        })
    }

    private fun showSelectedEvent(information: Information) {
        val intent = Intent(context, DetailInfoActivity::class.java).apply {
            putExtra(DetailInfoActivity.EXTRA_INFO, information)
        }
        startActivity(intent)
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}



//binding.rvComplaint.setHasFixedSize(true)
//listKomplain.addAll(KomplainData.listDataKomplain)
//showRecycleComplaintList()

//private fun showRecycleComplaintList() {
//    binding.rvComplaint.layoutManager =
//        LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
//    val listKomplaindapter = ComplaintAdapter(listKomplain)
//    binding.rvComplaint.adapter = listKomplaindapter
//
//    listKomplaindapter.setOnItemClickCallback(object : ComplaintAdapter.OnItemClickCallback {
//        override fun onItemClicked(data: Complaint) {
//            showSelectedKomplain(data)
//        }
//    })
//}
//
//private fun showSelectedKomplain(complaint: Complaint) {
//    val intent = Intent(context, DetailComplaintActivity::class.java)
//    startActivity(intent)
//}
