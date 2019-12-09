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
import com.example.appersonaltrainer.view_model.HomepageViewModel
import com.example.appersonaltrainer.view_model.HomepageViewModelFactory
import kotlinx.android.synthetic.main.fragment_homepage.*

class HomepageFragment : Fragment() {
    lateinit var homepageViewModel: HomepageViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        homepageViewModel =
            ViewModelProviders.of(
                this,
                HomepageViewModelFactory(activity!!.applicationContext, this)
            )
                .get(HomepageViewModel::class.java)
        homepageViewModel.loadSeriesOfUser()

        val root = inflater.inflate(R.layout.fragment_homepage, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupButtons()
    }

    private fun setupButtons() {
        setupButtonToCreateSeries()
    }

    private fun setupButtonToCreateSeries() {
        create_series_button.setOnClickListener {
            changeToCreateSeriesActivity()
        }
    }

    private fun changeToCreateSeriesActivity() {
        val intent = Intent(activity, CreateSeriesActivity::class.java)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        homepageViewModel.loadSeriesOfUser()
    }
}
