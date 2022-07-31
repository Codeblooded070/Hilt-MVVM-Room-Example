package com.example.datamaticstest.ui.fragment

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.datamaticstest.databinding.FragmentCakeDetailsBinding
import com.example.datamaticstest.model.CakeResponse
import com.example.datamaticstest.utils.UtilExtensions.isNetworkAvailable
import com.example.datamaticstest.viewmodel.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.ArrayList

@AndroidEntryPoint
class CakeDetailsFragment : Fragment() {

    private var donutName = ""
    private var topping: ArrayList<CakeResponse.CakeResponseItem.Topping> = ArrayList()

    private lateinit var viewModel: MainActivityViewModel
    private lateinit var mBinding: FragmentCakeDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentCakeDetailsBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)

        //check internet connection and show result
        if (context?.isNetworkAvailable() == true) {
            val bundle = arguments
            if (bundle != null) {
                donutName = bundle.getString("donut_name").toString()
                topping =
                    bundle.getParcelableArrayList<Parcelable>("toppings") as ArrayList<CakeResponse.CakeResponseItem.Topping>
            }
            setOnlineData()
        } else {
            val bundle = arguments
            if (bundle != null) {
                val donutId = bundle.getInt("donut_id")
                getDataOffline(donutId)
            }
        }
    }

    private fun setOnlineData() {
        val donutName = "Donut Name : $donutName"
        mBinding.tvCakeName.text = donutName
        val totalCount = "Topping Count : ${topping.size}"
        mBinding.tvTotalCount.text = totalCount

        var toppingText = "Type Of Donuts:"
        for (i in topping) {
            toppingText = "$toppingText \n ${i.type}"
        }
        mBinding.tvCakeDetails.text = toppingText
    }

    private fun getDataOffline(donutId: Int) {
        viewModel.getDonuts(donutId).observe(viewLifecycleOwner) {
            println("DonutDataSize : ${it.size}")
            val donutName = "Donut Name : ${it[0].donutName}"
            mBinding.tvCakeName.text = donutName
            val totalCount = "Topping Count : ${it[0].toppingCount}"
            mBinding.tvTotalCount.text = totalCount

            val toppingText = "Type Of Donuts : ${it[0].toppingName}"
            mBinding.tvCakeDetails.text = toppingText
        }
    }


}