package com.example.appersonaltrainer.view_model

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.appersonaltrainer.fragments.RecentsFragment

class RecentsViewModelFactory (
    private val context: Context,
    private val recentsFragment: RecentsFragment
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return RecentsViewModel(context, recentsFragment) as T
    }
}
