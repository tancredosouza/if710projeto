package com.example.appersonaltrainer.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.appersonaltrainer.R
import com.example.appersonaltrainer.activities.CreateSeriesActivity
import com.example.appersonaltrainer.view_model.RecentsViewModel
import com.example.appersonaltrainer.view_model.RecentsViewModelFactory

class RecentsFragment : Fragment() {
    lateinit var recentsViewModel: RecentsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        recentsViewModel =
            ViewModelProviders.of(
                this,
                RecentsViewModelFactory(activity!!.applicationContext, this)
            )
                .get(RecentsViewModel::class.java)
        recentsViewModel.loadSeriesHistoryOfUser()

        val root = inflater.inflate(R.layout.fragment_recents, container, false)
        return root
    }
}
