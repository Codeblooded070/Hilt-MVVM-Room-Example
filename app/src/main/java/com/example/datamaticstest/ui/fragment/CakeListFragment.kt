package com.example.datamaticstest.ui.fragment

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.datamaticstest.R
import com.example.datamaticstest.adapter.CakeAdapter
import com.example.datamaticstest.adapter.CakeAdapterInOfflineMode
import com.example.datamaticstest.databinding.FragmentCakeListBinding
import com.example.datamaticstest.model.CakeResponse
import com.example.datamaticstest.model.CakeResponse.CakeResponseItem.Topping
import com.example.datamaticstest.room.Donut
import com.example.datamaticstest.utils.OnActionPerformed
import com.example.datamaticstest.utils.UtilExtensions.hide
import com.example.datamaticstest.utils.UtilExtensions.isNetworkAvailable
import com.example.datamaticstest.utils.UtilExtensions.show
import com.example.datamaticstest.viewmodel.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


@AndroidEntryPoint
class CakeListFragment : Fragment(), OnActionPerformed {

    private lateinit var viewModel: MainActivityViewModel
    private lateinit var mBinding: FragmentCakeListBinding
    private var onActionPerformed: OnActionPerformed? = null

    init {
        onActionPerformed = this
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentCakeListBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]

        if (context?.isNetworkAvailable() == true) {
            mBinding.tvNetworkMode.text = getString(R.string.online_mode)
            getOnlineResult()
        } else {
            mBinding.tvNetworkMode.text = getString(R.string.offline_mode)
            getOfflineResult()
        }

    }

    private fun getOnlineResult() {
        mBinding.pb.show()
        viewModel.loadData()
        viewModel.getLiveData().observe(viewLifecycleOwner) {
            mBinding.pb.hide()
            if (it.isNotEmpty()) {
                //set online data
                setList(it)

                //insert values in table show app can run in offline mode
                this.insertInRoomDatabase(cakeResponse = it)
            } else {
                mBinding.tvNetworkMode.text = getString(R.string.switch_online_mode)
            }
        }
    }

    private fun getOfflineResult() {
        viewModel.getAllDonuts().observe(viewLifecycleOwner) {
            val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            val adapter = CakeAdapterInOfflineMode(context, it, onActionPerformed)
            mBinding.rvCake.layoutManager = layoutManager
            mBinding.rvCake.adapter = adapter
        }
    }


    private fun setList(cakeResponse: CakeResponse) {
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val adapter = CakeAdapter(context, cakeResponse, onActionPerformed)
        mBinding.rvCake.layoutManager = layoutManager
        mBinding.rvCake.adapter = adapter
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun insertInRoomDatabase(cakeResponse: CakeResponse) {
        GlobalScope.launch {
            for (i in cakeResponse) {
                val toppingCount = i.topping.size
                val donut = Donut(
                    id = i.id,
                    donutName = i.name,
                    toppingCount = toppingCount,
                    toppingName = i.topping.toString()
                )
                viewModel.insertDonut(donut)
                //println("inserting the data : Donut Id: ${donut.id}")
            }
        }
    }

    override fun onSelect(id: Int, name: String, topping: List<Topping>) {
        //Toast.makeText(context, name, Toast.LENGTH_SHORT).show()
        val fragment = CakeDetailsFragment()
        val bundle = Bundle()
        bundle.putInt("donut_id", id)
        bundle.putString("donut_name", name)
        bundle.putParcelableArrayList(
            "toppings",
            topping as ArrayList<out Parcelable?>?
        )
        fragment.arguments = bundle
        activity?.supportFragmentManager?.beginTransaction()?.add(R.id.container, fragment)
            ?.addToBackStack(
                FragmentActivity::class.java.simpleName
            )?.commit()
    }

    override fun onSelectOffline(id: Int) {
        val fragment = CakeDetailsFragment()
        val bundle = Bundle()
        bundle.putInt("donut_id", id)
        fragment.arguments = bundle
        activity?.supportFragmentManager?.beginTransaction()?.add(R.id.container, fragment)
            ?.addToBackStack(
                FragmentActivity::class.java.simpleName
            )?.commit()
    }


}