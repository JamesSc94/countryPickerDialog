package com.example.mylibrary

import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mylibrary.adapter.MultiButtonEnable
import com.example.mylibrary.adapter.adapter_DialogFilter
import com.example.mylibrary.databinding.DialogFilterBinding
import com.example.mylibrary.util.*
import com.example.mylibrary.vm.Vm_dialog_filter

interface OnMultiSelectionListener {
    fun onResult(c : List<Country>)

}

interface OnSelectionListener {
    fun onResult(c : Country)

}

class Dialog_filter : DialogFragment(), MultiButtonEnable,
    OnSelectionListener, OnMultiSelectionListener {

    private lateinit var vm : Vm_dialog_filter
    private lateinit var binding: DialogFilterBinding
    private lateinit var adapter_rv: adapter_DialogFilter

    private var listenerSingle : OnSelectionListener = this
    private var listenerMulti : OnMultiSelectionListener = this

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_filter, container, false)
        binding.lifecycleOwner = this

        vm = ViewModelProvider(this).get(Vm_dialog_filter::class.java)
        binding.vm = vm

        binding.dialogFilterRv.apply {
            adapter_rv = adapter_DialogFilter(util_country(context).country_sequence, this@Dialog_filter, listenerSingle)
            vm.countryModels.value = util_country(context).list_country
//            adapter_rv.submitList(vm.countryModels.value)
//            adapter = adapter_rv.apply {
//                modelsFull = currentList
//
//            }

            adapter = adapter_rv.apply {
                modelsFull = vm.countryModels.value as MutableList<Country>

            }

        }

        binding.dialogFilterSubmit.setOnClickListener(View.OnClickListener {  })

        vm.multiSelectionSubmit.observe(this, Observer {
            if(it){
                vm.multiSelectionSubmit.value = false

                val temp = adapter_rv.currentList.filterCheckbox()
                if(temp.size >= vm.multiSelectionMin && temp.size <= vm.multiSelectionMax){
                    listenerMulti.onResult(temp)
                    dismiss()

                }else{
                    Toast.makeText(context, vm.multiSelectionMsg, Toast.LENGTH_SHORT).show()

                }

            }

        })

        binding.dialogFilterSearch.afterTextChanged {
            adapter_rv.filter.filter(it)

        }

        binding.dialogFilterSpinsort.apply {
            val aa = ArrayAdapter<String>(context, R.layout.spinner_tv, vm.sortSpin)
            aa.setDropDownViewResource(R.layout.spinner_tv)

            adapter = aa

            onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

                override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                    vm.sortedType = when(pos){
                        0 -> 4
                        1 -> 6
                        2 -> 7
                        3 -> 8
                        else -> 9

                    }

                    startSort()

                }

            }

        }

        when(resources.configuration.uiMode.and(Configuration.UI_MODE_NIGHT_MASK)){
            Configuration.UI_MODE_NIGHT_YES -> adapter_rv.cseq.tvToColor(true)
            Configuration.UI_MODE_NIGHT_NO -> adapter_rv.cseq.tvToColor(false)
        }

        var hm = HashMap<String, String>()
        hm["Malaysia"] = "Differenttttttt"
//
//        setVisibleCheckbox(true)
        setVisibleName(true)
        setVisibleIcon(true)
        setVisiblePrefix(true)
//        setVisibleInfo(true)
//        setVisibleNameShort(true)
//        setVisibleCurrency(true)
//        setVisibleCurrencyShort(true)
//        setVisibleCapital(true)
//        setInfoMessageAll("testtttttiiinngggg")
//        setInfoHidden(arrayListOf("Algeria"))
//        setInfoShow(arrayListOf("Algeria"))
//        setInfoMessageCustom(hm)
//        setFilterByNameShort()
//        setFilterByAll()
//        setTextViewSizeName(20f)
//        setTextViewNameSizeWithName(arrayListOf("Malaysia", "Spain"), 25f)
//        setTextViewPrefixSizeWithName(arrayListOf("Malaysia", "Spain"), 30f)
//        setTextViewNameColorWithName(arrayListOf("Malaysia", "Spain"), Color.BLUE)
//        setTextViewCapitalColorWithName(arrayListOf("Spain"), Color.GREEN)
//        setTextViewNameBoldWithName(arrayListOf("Malaysia", "Spain"), true)
//        setTextViewColorName(Color.RED)
//        setTextViewBoldAll(true)
//        setTextViewCapitalBoldWithName(arrayListOf("Malaysia", "Spain"), true)
//        setTextViewNameShortSizeWithName(arrayListOf("Singapore"), 25f)
//        setHiddenOnlyWithName(arrayListOf("Malaysia"))
//        setShowOnlyWithName(arrayListOf("Malaysia"))
//        setWeightIcon(1f)
//        setPositionName(1)
//        setWeightName(0.9f)
//        setSpacingName(0.2f)
//        setCheckboxSelectionMin(3)
//        setCheckboxSelectionMax(5)
//        setCheckboxSelectionMsg("test again")
//        setTextViewCurrencySizeWithName(arrayListOf("Philippine"), 25f)
//
//        setSearchVisiblity(false)
//        setSearchHint("New Search")
//        setSearchTextColor(Color.RED)
//        setSortByCapital()
//        setSortPrioritizeByName(arrayListOf("Malaysia","Singapore"))
//
//        setBackgroundColor(Color.GRAY)
//        setSortVisibility(false)

//        setBackgroundColorButton(R.drawable.button_submit_dark)
//        setBackgroundListOddColor(Color.GREEN)
//        setBackgroundListEvenColor(Color.YELLOW)
        build()

        return binding.root

    }

    override fun onStart() {
        super.onStart()

        dialog?.let {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            dialog?.window?.setLayout(width, height)

        }

    }

    override fun multiChanged() {
        if(adapter_rv.cseq.size > 0){
            if(adapter_rv.cseq[0].visible){
                val temp = adapter_rv.currentList.filterCheckbox()

                binding.dialogFilterSubmit.isEnabled = temp.size >= vm.multiSelectionMin

            }

        }

    }

    //VISIBILITY
    fun setVisibleCheckbox(b : Boolean){
        adapter_rv.cseq.realSeq(1).visible = b

        if(b) binding.dialogFilterSubmit.visibility = VISIBLE else GONE

    }

    fun setVisibleIcon(b : Boolean){
        adapter_rv.cseq.realSeq(2).visible = b

    }

    fun setVisiblePrefix(b : Boolean){
        adapter_rv.cseq.realSeq(3).visible = b

    }

    fun setVisibleName(b : Boolean){
        adapter_rv.cseq.realSeq(4).visible = b

    }

    fun setVisibleInfo(b : Boolean){
        adapter_rv.cseq.realSeq(5).visible = b

    }

    fun setVisibleNameShort(b : Boolean){
        adapter_rv.cseq.realSeq(6).visible = b

    }

    fun setVisibleCurrency(b : Boolean){
        adapter_rv.cseq.realSeq(7).visible = b

    }

    fun setVisibleCurrencyShort(b : Boolean){
        adapter_rv.cseq.realSeq(8).visible = b

    }

    fun setVisibleCapital(b : Boolean){
        adapter_rv.cseq.realSeq(9).visible = b

    }

    //POSITION
    fun setPositionCheckbox(pos : Int){
        adapter_rv.cseq.swap(1, pos)

    }

    fun setPositionIcon(pos : Int){
        adapter_rv.cseq.swap(2, pos)

    }

    fun setPositionPrefix(pos : Int){
        adapter_rv.cseq.swap(3, pos)

    }

    fun setPositionName(pos : Int){
        adapter_rv.cseq.swap(4, pos)

    }

    fun setPositionInfo(pos : Int){
        adapter_rv.cseq.swap(5, pos)

    }

    fun setPositionNameShort(pos : Int){
        adapter_rv.cseq.swap(6, pos)

    }

    fun setPositionCurrency(pos : Int){
        adapter_rv.cseq.swap(7, pos)

    }

    fun setPositionCurrencyShort(pos : Int){
        adapter_rv.cseq.swap(8, pos)

    }

    fun setPositionCapital(pos : Int){
        adapter_rv.cseq.swap(9, pos)

    }

    //Weight
    fun setWeightCheckbox(weight : Float){
        adapter_rv.cseq.realSeq(1).weight = weight

    }

    fun setWeightIcon(weight : Float){
        adapter_rv.cseq.realSeq(2).weight = weight

    }

    fun setWeightPrefix(weight : Float){
        adapter_rv.cseq.realSeq(3).weight = weight

    }

    fun setWeightName(weight : Float){
        adapter_rv.cseq.realSeq(4).weight = weight

    }

    fun setWeightInfo(weight : Float){
        adapter_rv.cseq.realSeq(5).weight = weight

    }

    fun setWeightNameShort(weight : Float){
        adapter_rv.cseq.realSeq(6).weight = weight

    }

    fun setWeightCurrency(weight : Float){
        adapter_rv.cseq.realSeq(7).weight = weight

    }

    fun setWeightCurrencyShort(weight : Float){
        adapter_rv.cseq.realSeq(8).weight = weight

    }

    fun setWeightCapital(weight : Float){
        adapter_rv.cseq.realSeq(9).weight = weight

    }

    //Spacing
    fun setSpacingCheckbox(weight : Float){
        adapter_rv.cseq.realSeq(1).spaceLeft = weight

    }

    fun setSpacingIcon(weight : Float){
        adapter_rv.cseq.realSeq(2).spaceLeft = weight

    }

    fun setSpacingPrefix(weight : Float){
        adapter_rv.cseq.realSeq(3).spaceLeft = weight

    }

    fun setSpacingName(weight : Float){
        adapter_rv.cseq.realSeq(4).spaceLeft = weight

    }

    fun setSpacingInfo(weight : Float){
        adapter_rv.cseq.realSeq(5).spaceLeft = weight

    }

    fun setSpacingNameShort(weight : Float){
        adapter_rv.cseq.realSeq(6).spaceLeft = weight

    }

    fun setSpacingCurrency(weight : Float){
        adapter_rv.cseq.realSeq(7).spaceLeft = weight

    }

    fun setSpacingCurrencyShort(weight : Float){
        adapter_rv.cseq.realSeq(8).spaceLeft = weight

    }

    fun setSpacingCapital(weight : Float){
        adapter_rv.cseq.realSeq(9).spaceLeft = weight

    }

    //Multi
    fun setCheckboxSelectionMin(min : Int){
        vm.multiSelectionMin = if(min > 0) min else 1

    }

    fun setCheckboxSelectionMax(max : Int){
        vm.multiSelectionMax = if(max > 0) max else 1

    }

    fun setCheckboxSelectionMsg(msg : String){
        vm.multiSelectionMsg = msg

    }

    //Info
    fun setInfoMessageAll(info : String){
        adapter_rv.apply {
            modelsFull = modelsFull.onEach {
                it.info = info
            }

//            submitList(modelsFull)

        }

    }

    fun setInfoMessageCustom(info : HashMap<String, String>){
        adapter_rv.modelsFull.filterSetInfoName(info)

    }

    fun setInfoHidden(list : List<String>){
        adapter_rv.apply {
            modelsFull.hiddenInfo(list, false)
//            submitList(modelsFull)

        }

    }

    fun setInfoShow(list : List<String>){
        adapter_rv.apply {
            modelsFull.hiddenInfo(list, true)
            submitList(modelsFull)

        }

    }

    //Hidden
    fun setHiddenOnlyWithName(list : List<String>){
        adapter_rv.apply {
            modelsFull = modelsFull.filterName(list, false) as MutableList<Country>
//            submitList(modelsFull)

        }

    }

    fun setShowOnlyWithName(list : List<String>){
        adapter_rv.apply {
            modelsFull = modelsFull.filterName(list, true) as MutableList<Country>
//            submitList(modelsFull)

        }

    }

    //Selection
    fun setOnSelectionListener(watcher : OnSelectionListener) {
        listenerSingle = watcher

    }

    fun setOnMultiSelectionListener(watcher : OnMultiSelectionListener) {
        listenerMulti = watcher

    }

    //Filter By
    fun setFilterByName(){
        adapter_rv.filterType = "name"

    }

    fun setFilterByNameShort(){
        adapter_rv.filterType = "sname"

    }

    fun setFilterByPrefix(){
        adapter_rv.filterType = "prefix"

    }

    fun setFilterCurreny(){
        adapter_rv.filterType = "currency"

    }

    fun setFilterByCurrencyShort(){
        adapter_rv.filterType = "scurrency"

    }

    fun setFilterCapital(){
        adapter_rv.filterType = "capital"

    }

    fun setFilterByAll(){
        adapter_rv.filterType = "all"

    }

    //TextView Size
    fun setTextViewSizePrefix(size : Float){
        adapter_rv.cseq.realSeq(3).tSize = size

    }

    fun setTextViewSizeName(size : Float){
        adapter_rv.cseq.realSeq(4).tSize = size

    }

    fun setTextViewSizeNameShort(size : Float){
        adapter_rv.cseq.realSeq(6).tSize = size

    }

    fun setTextViewSizeCurrency(size : Float){
        adapter_rv.cseq.realSeq(7).tSize = size

    }

    fun setTextViewSizeCurrencyShort(size : Float){
        adapter_rv.cseq.realSeq(8).tSize = size

    }

    fun setTextViewSizeCapital(size : Float){
        adapter_rv.cseq.realSeq(9).tSize = size

    }

    fun setTextViewSizeAll(size : Float){
        setTextViewSizePrefix(size)
        setTextViewSizeName(size)
        setTextViewSizeNameShort(size)
        setTextViewSizeCurrency(size)
        setTextViewSizeCurrencyShort(size)
        setTextViewSizeCapital(size)

    }

    fun setTextViewPrefixSizeWithName(list : List<String>, size : Float){
        adapter_rv.apply {
            val temp = modelsFull.filterNameListOnlyRLI(list)

            if(temp.isNotEmpty()){
                temp.forEach {
                    adapter_rv.modelsFull[it].specTVP.size = size

                }

            }

//            submitList(modelsFull)

        }

    }

    fun setTextViewNameSizeWithName(list : List<String>, size : Float){
        adapter_rv.apply {
            val temp = modelsFull.filterNameListOnlyRLI(list)

            if(temp.isNotEmpty()){
                temp.forEach {
                    adapter_rv.modelsFull[it].specTVN.size = size

                }

            }

//            submitList(modelsFull)

        }

    }

    fun setTextViewNameShortSizeWithName(list : List<String>, size : Float){
        adapter_rv.apply {
            val temp = modelsFull.filterNameListOnlyRLI(list)

            if(temp.isNotEmpty()){
                temp.forEach {
                    adapter_rv.modelsFull[it].specTVSN.size = size

                }

            }

//            submitList(modelsFull)

        }

    }

    fun setTextViewCurrencySizeWithName(list : List<String>, size : Float){
        adapter_rv.apply {
            val temp = modelsFull.filterNameListOnlyRLI(list)

            if(temp.isNotEmpty()){
                temp.forEach {
                    adapter_rv.modelsFull[it].specTVCur.size = size

                }

            }

//            submitList(modelsFull)

        }

    }

    fun setTextViewCurrencyShortSizeWithName(list : List<String>, size : Float){
        adapter_rv.apply {
            val temp = modelsFull.filterNameListOnlyRLI(list)

            if(temp.isNotEmpty()){
                temp.forEach {
                    adapter_rv.modelsFull[it].specTVSCur.size = size

                }

            }

//            submitList(modelsFull)

        }

    }

    fun setTextViewCapitalSizeWithName(list : List<String>, size : Float){
        adapter_rv.apply {
            val temp = modelsFull.filterNameListOnlyRLI(list)

            if(temp.isNotEmpty()){
                temp.forEach {
                    adapter_rv.modelsFull[it].specTVCap.size = size

                }

            }

//            submitList(modelsFull)

        }

    }

    //TextView Color
    fun setTextViewColorPrefix(color : Int){
        adapter_rv.cseq.realSeq(3).tColor = color

    }

    fun setTextViewColorName(color : Int){
        adapter_rv.cseq.realSeq(4).tColor = color

    }

    fun setTextViewColorNameShort(color : Int){
        adapter_rv.cseq.realSeq(6).tColor = color

    }

    fun setTextViewColorCurrency(color : Int){
        adapter_rv.cseq.realSeq(7).tColor = color

    }

    fun setTextViewColorCurrencyShort(color : Int){
        adapter_rv.cseq.realSeq(8).tColor = color

    }

    fun setTextViewColorCapital(color : Int){
        adapter_rv.cseq.realSeq(9).tColor = color

    }

    fun setTextViewColorAll(color : Int){
        setTextViewColorPrefix(color)
        setTextViewColorName(color)
        setTextViewColorNameShort(color)
        setTextViewColorCurrency(color)
        setTextViewColorCurrencyShort(color)
        setTextViewColorCapital(color)

    }

    fun setTextViewPrefixColorWithName(list : List<String>, color : Int){
        adapter_rv.apply {
            val temp = modelsFull.filterNameListOnlyRLI(list)

            if(temp.isNotEmpty()){
                temp.forEach {
                    adapter_rv.modelsFull[it].specTVP.color = color

                }

            }

//            submitList(modelsFull)

        }

    }

    fun setTextViewNameColorWithName(list : List<String>, color : Int){
        adapter_rv.apply {
            val temp = modelsFull.filterNameListOnlyRLI(list)

            if(temp.isNotEmpty()){
                temp.forEach {
                    adapter_rv.modelsFull[it].specTVN.color = color

                }

            }

//            submitList(modelsFull)

        }

    }

    fun setTextViewNameShortColorWithName(list : List<String>, color : Int){
        adapter_rv.apply {
            val temp = modelsFull.filterNameListOnlyRLI(list)

            if(temp.isNotEmpty()){
                temp.forEach {
                    adapter_rv.modelsFull[it].specTVSN.color = color

                }

            }

//            submitList(modelsFull)

        }

    }

    fun setTextViewCurrencyColorWithName(list : List<String>, color : Int){
        adapter_rv.apply {
            val temp = modelsFull.filterNameListOnlyRLI(list)

            if(temp.isNotEmpty()){
                temp.forEach {
                    adapter_rv.modelsFull[it].specTVCur.color = color

                }

            }

//            submitList(modelsFull)

        }

    }

    fun setTextViewCurrencyShortColorWithName(list : List<String>, color : Int){
        adapter_rv.apply {
            val temp = modelsFull.filterNameListOnlyRLI(list)

            if(temp.isNotEmpty()){
                temp.forEach {
                    adapter_rv.modelsFull[it].specTVSCur.color = color

                }

            }

//            submitList(modelsFull)

        }

    }

    fun setTextViewCapitalColorWithName(list : List<String>, color : Int){
        adapter_rv.apply {
            val temp = modelsFull.filterNameListOnlyRLI(list)

            if(temp.isNotEmpty()){
                temp.forEach {
                    adapter_rv.modelsFull[it].specTVCap.color = color

                }

            }

//            submitList(modelsFull)

        }

    }

    //TextView Bold
    fun setTextViewBoldPrefix(b : Boolean){
        adapter_rv.cseq.realSeq(3).tBold = b

    }

    fun setTextViewBoldName(b : Boolean){
        adapter_rv.cseq.realSeq(4).tBold = b

    }

    fun setTextViewBoldNameShort(b : Boolean){
        adapter_rv.cseq.realSeq(6).tBold = b

    }

    fun setTextViewBoldCurrency(b : Boolean){
        adapter_rv.cseq.realSeq(7).tBold = b

    }

    fun setTextViewBoldCurrencyShort(b : Boolean){
        adapter_rv.cseq.realSeq(8).tBold = b

    }

    fun setTextViewBoldCapital(b : Boolean){
        adapter_rv.cseq.realSeq(9).tBold = b

    }

    fun setTextViewBoldAll(b : Boolean){
        setTextViewBoldPrefix(b)
        setTextViewBoldName(b)
        setTextViewBoldNameShort(b)
        setTextViewBoldCurrency(b)
        setTextViewBoldCurrencyShort(b)
        setTextViewBoldCapital(b)

    }

    fun setTextViewPrefixBoldWithName(list : List<String>, bold : Boolean){
        adapter_rv.apply {
            val temp = modelsFull.filterNameListOnlyRLI(list)

            if(temp.isNotEmpty()){
                temp.forEach {
                    adapter_rv.modelsFull[it].specTVP.bold = bold

                }

            }

//            submitList(modelsFull)

        }

    }

    fun setTextViewNameBoldWithName(list : List<String>, bold : Boolean){
        adapter_rv.apply {
            val temp = modelsFull.filterNameListOnlyRLI(list)

            if(temp.isNotEmpty()){
                temp.forEach {
                    adapter_rv.modelsFull[it].specTVN.bold = bold

                }

            }

//            submitList(modelsFull)

        }

    }

    fun setTextViewNameShortBoldWithName(list : List<String>, bold : Boolean){
        adapter_rv.apply {
            val temp = modelsFull.filterNameListOnlyRLI(list)

            if(temp.isNotEmpty()){
                temp.forEach {
                    adapter_rv.modelsFull[it].specTVSN.bold = bold

                }

            }

//            submitList(modelsFull)

        }

    }

    fun setTextViewCurrencyBoldWithName(list : List<String>, bold : Boolean){
        adapter_rv.apply {
            val temp = modelsFull.filterNameListOnlyRLI(list)

            if(temp.isNotEmpty()){
                temp.forEach {
                    adapter_rv.modelsFull[it].specTVCur.bold = bold

                }

            }

//            submitList(modelsFull)

        }

    }

    fun setTextViewCurrencyShortBoldWithName(list : List<String>, bold : Boolean){
        adapter_rv.apply {
            val temp = modelsFull.filterNameListOnlyRLI(list)

            if(temp.isNotEmpty()){
                temp.forEach {
                    adapter_rv.modelsFull[it].specTVSCur.bold = bold

                }

            }

//            submitList(modelsFull)

        }

    }

    fun setTextViewCapitalBoldWithName(list : List<String>, bold : Boolean){
        adapter_rv.apply {
            val temp = modelsFull.filterNameListOnlyRLI(list)

            if(temp.isNotEmpty()){
                temp.forEach {
                    adapter_rv.modelsFull[it].specTVCap.bold = bold

                }

            }

//            submitList(modelsFull)

        }

    }

    //Sort
    fun setSortByName(){
        vm.sortedType = 4

        startSort()

    }

    fun setSortByNameShort(){
        vm.sortedType = 6

        startSort()

    }

    fun setSortByCurrency(){
        vm.sortedType = 7

        startSort()

    }

    fun setSortByCurrencyShort(){
        vm.sortedType = 8

        startSort()

    }

    fun setSortByCapital(){
        vm.sortedType = 9

        startSort()

    }

    fun setSortPrioritizeByName(list : ArrayList<String>){
        vm.sortedPrioritize = list

        startSort()

    }

    private fun startSort(){
        adapter_rv.apply {
            val temp = modelsFull.sortedBy(vm.sortedPrioritize, vm.sortedType)

            modelsFull = temp
//            submitList(modelsFull)

        }

    }

    //Search
    fun setSearchVisiblity(b: Boolean){
        vm.searchVisibility = b

        binding.dialogFilterSearch.visibility = if(b) VISIBLE else GONE

    }

    fun setSearchHint(s: String){
        vm.searchHint = s

        binding.dialogFilterSearch.hint = s

    }

    fun setSearchTextColor(c: Int){
        vm.searchColor = c

        binding.dialogFilterSearch.setTextColor(c)

    }

    //Spinner
    fun setSortVisibility(b: Boolean){
        binding.dialogFilterSortLl.visibility = if(b) VISIBLE else GONE

    }

    //BG
    fun setBackgroundColor(c: Int){
        binding.dialogFilterCl.setBackgroundColor(c)

    }

    fun setBackgroundColorButton(d: Int){
        binding.dialogFilterSubmit.setBackgroundResource(d)

    }

    fun setBackgroundListOddColor(c: Int){
        adapter_rv.oddColor = c

    }

    fun setBackgroundListEvenColor(c: Int){
        adapter_rv.evenColor = c

    }

    //Result
    override fun onResult(c: Country) {

    }

    override fun onResult(c: List<Country>) {

    }

    fun build(){
        adapter_rv.submitList(vm.countryModels.value)
//        adapter_rv.notifyDataSetChanged()

    }

}