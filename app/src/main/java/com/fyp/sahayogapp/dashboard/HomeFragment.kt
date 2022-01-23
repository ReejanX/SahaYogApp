package com.fyp.sahayogapp.dashboard

import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.fyp.sahayogapp.R
import com.fyp.sahayogapp.custom.CustomTextView
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.fyp.sahayogapp.dashboard.adapters.RequestListAdapter
import com.fyp.sahayogapp.dashboard.model.DonationRequestModel
import com.fyp.sahayogapp.dashboard.viewModel.RequestViewModel


class HomeFragment : Fragment() {
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var locationBtn : Button
    lateinit var locationTv : CustomTextView
    lateinit var geocoder: Geocoder
    lateinit var userListRecyclerView: RecyclerView
    lateinit var  requestListAdapter: RequestListAdapter
    lateinit var requestViewModel :RequestViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity)
        locationBtn = view.findViewById<Button>(R.id.locationBtn)
        locationTv = view.findViewById(R.id.locationTV)
        userListRecyclerView = view.findViewById(R.id.userListRecycler)
        requestListAdapter = RequestListAdapter()

        locationBtn.setOnClickListener {
            fetchLocation()
        }
        view.findViewById<Button>(R.id.getRequestBtn).setOnClickListener {
             initViewModel()   }
        initUserListRecycler()
//        initViewModel()
    }
    fun initUserListRecycler (){
        userListRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = requestListAdapter
        }
    }

    fun initViewModel(){

       requestViewModel= ViewModelProvider(this).get(RequestViewModel::class.java)
        requestViewModel.getDonationList()

        requestViewModel.getDonationListObserver().observe(requireActivity(), Observer<List<DonationRequestModel>> {
            if (it==null){

                Toast.makeText(requireContext(), "No results Found", Toast.LENGTH_SHORT).show()
            }else{

                requestListAdapter.userList = it.toMutableList()
                requestListAdapter.notifyDataSetChanged()

            }
        })

    }


    private fun fetchLocation(){
       if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
           Toast.makeText(requireContext(), "permission granted", Toast.LENGTH_SHORT).show()

           val task = fusedLocationProviderClient.lastLocation
           task.addOnSuccessListener {
               if(it!=null){
                   Toast.makeText(requireContext(),"${it.latitude},${it.longitude}",Toast.LENGTH_SHORT).show()
                   Log.d("LOC","${it.latitude},${it.longitude}")
                   locationTv.text= "${it.latitude},${it.longitude}"
//                   val address = getAddress(it.latitude,it.longitude)
//                   locationTv.text = "Address: $address"
               }
           }
        } else {
           Toast.makeText(requireContext(), "permission notgranted", Toast.LENGTH_SHORT).show()
            return
        }

    }
//    private fun getAddress(lat: Double, lng: Double): String {
//        val geocoder = Geocoder(requireContext())
//        val list = geocoder.getFromLocation(lat, lng, 1)
//        return list[0].getAddressLine(0)
//    }


}