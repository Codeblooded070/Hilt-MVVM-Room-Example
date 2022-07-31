package com.example.datamaticstest.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.datamaticstest.R
import com.example.datamaticstest.databinding.ActivityMainBinding
import com.example.datamaticstest.ui.fragment.CakeListFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        openFragment(CakeListFragment(), false)
    }

    override fun onBackPressed() {
        //println("backStackCount : ${supportFragmentManager.backStackEntryCount}")
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            finish()
        }
    }

    fun openFragment(
        fragment: Fragment,
        addToBackStack: Boolean
    ) {
        if (addToBackStack) {
            supportFragmentManager.beginTransaction().add(R.id.container, fragment)
                .addToBackStack(
                    FragmentActivity::class.java.simpleName
                ).commit()
        } else {
            supportFragmentManager.beginTransaction().add(R.id.container, fragment)
                .commit()
        }

    }
}