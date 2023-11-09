package com.moronlu18.invoice.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.moronlu18.invoice.R
import com.moronlu18.invoice.databinding.FragmentSplashBinding

/**
 * A simple [Fragment] subclass.
 * Use the [SplashFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SplashFragment : Fragment() {
private  var _binding: FragmentSplashBinding?=null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding= FragmentSplashBinding.inflate(inflater, container, false)
        return  binding.root
    }

    override fun onStart() {
        super.onStart()
        var r = Runnable {
            findNavController().navigate(R.id.action_splashFragment_to_mainFragment)
        }
        Handler(Looper.getMainLooper()).postDelayed(r,2000)
    }
}