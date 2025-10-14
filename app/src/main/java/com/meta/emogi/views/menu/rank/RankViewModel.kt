package com.meta.emogi.views.menu.rank

import android.view.View
import androidx.lifecycle.LiveData
import com.meta.emogi.R
import com.meta.emogi.base.BaseViewModel
import com.meta.emogi.base.SingleLiveEvent

class RankViewModel : BaseViewModel() {

    private val _Go2HomeFrag = SingleLiveEvent<Unit>()

    fun Go2HomeFrag() : LiveData<Unit>{
        return _Go2HomeFrag
    }


    override fun onButtonClicked(v: View): Boolean {
        if(!super.onButtonClicked(v)) return false

        val btnResId = v.id

        if(btnResId == R.id.title_home){
            _Go2HomeFrag.call()
        }
        return true
    }
}