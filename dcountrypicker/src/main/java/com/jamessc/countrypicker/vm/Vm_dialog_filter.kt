package com.jamessc.countrypicker.vm

import android.graphics.Color
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.jamessc.countrypicker.Country
import com.jamessc.countrypicker.util.ConstantsCountry
import com.jamessc.countrypicker.util.ConstantsSearch

class Vm_dialog_filter : ViewModel() {

    var searchVisibility = MutableLiveData<Boolean>()
    var searchHint = MutableLiveData<String>()
    var searchColor = MutableLiveData<Int>()
    var searchScrollType = MutableLiveData<String>()
    var sortSpin = arrayListOf<String>("Name", "Short Name", "Currency", "Short Currency", "Capital")
    var sortSpinFirst = MutableLiveData<Boolean>()
    var sortedType = MutableLiveData<Int>()
    var sortedVisiblity = MutableLiveData<Boolean>()
    var sortedPrioritize = MutableLiveData<List<String>>()
    var bgColor = MutableLiveData<Int>()
    var bgColorSubmit = MutableLiveData<Int>()
    var multiSelectionSubmit = MutableLiveData<Boolean>()
    var multiSelectionMin = MutableLiveData<Int>()
    var multiSelectionMax = MutableLiveData<Int>()
    var multiSelectionMsg = MutableLiveData<String>()
    var countryModels = MutableLiveData<List<Country>>()

    fun clickSubmit(){
        multiSelectionSubmit.value = true

    }

}