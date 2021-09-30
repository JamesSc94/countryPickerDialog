package com.example.mylibrary.vm

import android.graphics.Color
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mylibrary.Country
import com.example.mylibrary.util.ConstantsCountry
import com.example.mylibrary.util.ConstantsSearch

class Vm_dialog_filter : ViewModel() {

    var searchVisibility = true
    var searchHint = ConstantsSearch.HINT
    var searchColor = Color.BLACK
    var sortSpin = arrayListOf<String>("Name", "Short Name", "Currency", "Short Currency", "Capital")
    var sortedType = 3
    var sortedPrioritize = arrayListOf<String>()
    var multiSelectionSubmit = MutableLiveData<Boolean>()
    var multiSelectionMin = 1
    var multiSelectionMax = 100
    var multiSelectionMsg = ConstantsCountry.MSGMULTIError
    var countryModels = MutableLiveData<List<Country>>()

    fun clickSubmit(){
        multiSelectionSubmit.value = true

    }

}