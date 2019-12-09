package com.example.appersonaltrainer.view_model

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.appersonaltrainer.fragments.HomepageFragment

class HomepageViewModelFactory (
    private val context: Context,
    private val homepageFragment: HomepageFragment
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomepageViewModel(context, homepageFragment) as T
    }
}
