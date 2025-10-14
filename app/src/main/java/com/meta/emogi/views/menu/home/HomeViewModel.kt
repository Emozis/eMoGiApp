package com.meta.emogi.views.menu.home

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import com.meta.emogi.R
import com.meta.emogi.base.BaseViewModel
import com.meta.emogi.base.SingleLiveEvent

class HomeViewModel : BaseViewModel() {
    private val _Go2RankFrag = SingleLiveEvent<Unit>()

    fun Go2RankFrag() : LiveData<Unit> {
        return _Go2RankFrag
    }


    override fun onButtonClicked(v: View): Boolean {
        if(!super.onButtonClicked(v)) return false

        val btnResId = v.id

        if(btnResId == R.id.title_rank){
            Log.d("www", "onButtonClicked: ")
            _Go2RankFrag.call()
        }
        return true
    }
}