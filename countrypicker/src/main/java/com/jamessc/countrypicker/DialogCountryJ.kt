package com.jamessc.countrypicker

import android.content.Context
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
import androidx.recyclerview.widget.LinearLayoutManager
import com.jamessc.countrypicker.adapter.MultiButtonEnable
import com.jamessc.countrypicker.adapter.adapter_DialogFilter
import com.jamessc.countrypicker.databinding.DialogFilterBinding
import com.jamessc.countrypicker.util.*
import com.jamessc.countrypicker.vm.Vm_dialog_filter

interface OnMultiSelectionListener {
    fun onResult(c : List<Country>)

}

interface OnSelectionListener {
    fun onResult(c : Country)

}

class DialogCountryJ(ctx : Context) : DialogFragment(), MultiButtonEnable,
    OnSelectionListener, OnMultiSelectionListener {

    private lateinit var vm : Vm_dialog_filter
    private lateinit var binding: DialogFilterBinding
    private var adapter_rv: adapter_DialogFilter

    private var tempMultiSelectionMin = 1
    private var tempMultiSelectionMax = 100
    private var tempMultiSelectionMsg = ConstantsCountry.MSGMULTIError
    private var tempSearchScrollType = ""
    private var tempSortedType = 3
    private var tempSortedPrioritize = arrayListOf<String>()
    private var tempSortedVisiblity = false
    private var tempSearchVisibility = true
    private var tempSearchHint = ConstantsSearch.HINT
    private var tempSearchColor = Color.BLACK
    private var tempBgColor = Color.TRANSPARENT
    private var tempBgColorSubmit = R.drawable.button_submit

    private var listenerSingle : OnSelectionListener = this
    private var listenerMulti : OnMultiSelectionListener = this

    init {
        adapter_rv = adapter_DialogFilter(util_country(ctx).country_sequence, this@DialogCountryJ, listenerSingle).apply {
            modelsFull = util_country(ctx).list_country as MutableList<Country>

        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_filter, container, false)
        binding.lifecycleOwner = this

        vm = ViewModelProvider(this).get(Vm_dialog_filter::class.java)
        binding.vm = vm

        binding.dialogFilterRv.layoutManager =
            context?.let { llm_smooth(it, LinearLayoutManager.VERTICAL, false) }

        binding.dialogFilterRv.apply {
//            adapter_rv = adapter_DialogFilter(util_country(context).country_sequence, this@DialogCountryJ, listenerSingle)
//            vm.countryModels.value = util_country(context).list_country
//            adapter_rv.submitList(vm.countryModels.value)
//            adapter = adapter_rv.apply {
//                modelsFull = currentList
//
//            }

            adapter = adapter_rv.apply {
                vm.countryModels.value = modelsFull
//                modelsFull = vm.countryModels.value as MutableList<Country>

            }

        }

        binding.dialogFilterSubmit.setOnClickListener(View.OnClickListener {  })

        vm.multiSelectionSubmit.observe(this, Observer {
            if(it){
                vm.multiSelectionSubmit.value = false

                val temp = adapter_rv.currentList.filterCheckbox()
                if(temp.size >= vm.multiSelectionMin.value!! && temp.size <= vm.multiSelectionMax.value!!){
                    listenerMulti.onResult(temp)
                    dismiss()

                }else{
                    Toast.makeText(context, vm.multiSelectionMsg.value, Toast.LENGTH_SHORT).show()

                }

            }

        })

        binding.dialogFilterSearch.afterTextChanged {

            if(vm.searchScrollType.value!!.isNotEmpty()){

                val temp : List<Int> = when(vm.searchScrollType.value){
                    "name" -> adapter_rv.modelsFull.withIndex().filter { name -> name.value.name.startsWith(it, true) }.map { ind -> ind.index }
                    "sname" -> adapter_rv.modelsFull.withIndex().filter { sname -> sname.value.sname.startsWith(it, true) }.map { ind -> ind.index }
                    "prefix" -> adapter_rv.modelsFull.withIndex().filter { prefix -> prefix.value.prefix.startsWith(it, true) }.map { ind -> ind.index }
                    "currency" -> adapter_rv.modelsFull.withIndex().filter { currency -> currency.value.currency.startsWith(it, true) }.map { ind -> ind.index }
                    "scurreny" -> adapter_rv.modelsFull.withIndex().filter { scurrency -> scurrency.value.scurrency.startsWith(it, true) }.map { ind -> ind.index }
                    else -> adapter_rv.modelsFull.withIndex().filter { capital -> capital.value.capital.startsWith(it, true) }.map { ind -> ind.index }

                }

                if(temp.isNotEmpty()){
                    binding.dialogFilterRv.smoothScrollToPosition(temp[0])

                }

            }else{
                adapter_rv.filter.filter(it)

            }

        }

        binding.dialogFilterSpinsort.apply {
            val aa = ArrayAdapter<String>(context, R.layout.spinner_tv, vm.sortSpin)
            aa.setDropDownViewResource(R.layout.spinner_tv)

            adapter = aa
            vm.sortSpinFirst.value = true

            setSelection(when(vm.sortedType.value){
                4 -> 0
                6 -> 1
                7 -> 2
                8 -> 3
                else -> 4
            })

            onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

                override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                    vm.sortedType.value = when(pos){
                        0 -> 4
                        1 -> 6
                        2 -> 7
                        3 -> 8
                        else -> 9

                    }

                    if(vm.sortSpinFirst.value != true){
                        startSort()

                    }else{
                        vm.sortSpinFirst.value = false

                    }

                }

            }

        }

        when(resources.configuration.uiMode.and(Configuration.UI_MODE_NIGHT_MASK)){
            Configuration.UI_MODE_NIGHT_YES -> adapter_rv.cseq.tvToColor(true)
            Configuration.UI_MODE_NIGHT_NO -> adapter_rv.cseq.tvToColor(false)

        }

        initBinding()

        adapter_rv.submitList(vm.countryModels.value)

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

                binding.dialogFilterSubmit.isEnabled = temp.size >= vm.multiSelectionMin.value!!

            }

        }

    }

    //VISIBILITY
    fun setVisibleCheckbox(b : Boolean){
        adapter_rv.cseq.realSeq(1).visible = b

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
        if(::vm.isInitialized) vm.multiSelectionMin.value = if(min > 0) min else 1
        else tempMultiSelectionMin = if(min > 0) min else 1

    }

    fun setCheckboxSelectionMax(max : Int){
        if(::vm.isInitialized) vm.multiSelectionMax.value = if(max > 0) max else 1
        else tempMultiSelectionMax = if(max > 0) max else 1

    }

    fun setCheckboxSelectionMsg(msg : String){
        if(::vm.isInitialized) vm.multiSelectionMsg.value = msg
        else tempMultiSelectionMsg = msg

    }

    //Info
    fun setInfoMessageAll(info : String){
        adapter_rv.apply {
            modelsFull.onEach {
                it.info = info

            }

        }

    }

    fun setInfoMessageCustom(info : HashMap<String, String>){
        adapter_rv.modelsFull.filterSetInfoName(info)

    }

    fun setInfoHiddenOnly(list : List<String>){
        adapter_rv.apply {
            modelsFull.hiddenInfo(list, false)

        }

    }

    fun setInfoShowOnly(list : List<String>){
        adapter_rv.apply {
            modelsFull.hiddenInfo(list, true)
//            submitList(modelsFull)

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
//    fun setOnSelectionListener(watcher : OnSelectionListener) {
//        listenerSingle = watcher
//
//    }
//
//    fun setOnMultiSelectionListener(watcher : OnMultiSelectionListener) {
//        listenerMulti = watcher
//
//    }

    //Filter By
    fun setFilterByName(){
        tempSearchScrollType = ""
//        vm.searchScrollType = ""
        adapter_rv.filterType = "name"

    }

    fun setFilterByNameShort(){
        tempSearchScrollType = ""
//        vm.searchScrollType = ""
        adapter_rv.filterType = "sname"

    }

    fun setFilterByPrefix(){
        tempSearchScrollType = ""
//        vm.searchScrollType = ""
        adapter_rv.filterType = "prefix"

    }

    fun setFilterByCurreny(){
        tempSearchScrollType = ""
//        vm.searchScrollType = ""
        adapter_rv.filterType = "currency"

    }

    fun setFilterByCurrencyShort(){
        tempSearchScrollType = ""
//        vm.searchScrollType = ""
        adapter_rv.filterType = "scurrency"

    }

    fun setFilterByCapital(){
        tempSearchScrollType = ""
//        vm.searchScrollType = ""
        adapter_rv.filterType = "capital"

    }

    fun setFilterByAll(){
        tempSearchScrollType = ""
//        vm.searchScrollType = ""
        adapter_rv.filterType = "all"

    }

    fun setFilterScrollByName(){
        tempSearchScrollType = "name"
//        vm.searchScrollType = "name"

    }

    fun setFilterScrollByNameShort(){
        tempSearchScrollType = "sname"
//        vm.searchScrollType = "sname"

    }

    fun setFilterScrollByPrefix(){
        tempSearchScrollType = "prefix"
//        vm.searchScrollType = "prefix"

    }

    fun setFilterScrollByCurreny(){
        tempSearchScrollType = "currency"
//        vm.searchScrollType = "currency"

    }

    fun setFilterScrollByCurrencyShort(){
        tempSearchScrollType = "scurrency"
//        vm.searchScrollType = "scurrency"

    }

    fun setFilterScrollByCapital(){
        tempSearchScrollType = "capital"
//        vm.searchScrollType = "capital"

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
        if(::vm.isInitialized) vm.sortedType.value = 4
        else tempSortedType = 4

        startSort()

    }

    fun setSortByNameShort(){
        if(::vm.isInitialized) vm.sortedType.value = 6
        else tempSortedType = 6

        startSort()

    }

    fun setSortByCurrency(){
        if(::vm.isInitialized) vm.sortedType.value = 7
        else tempSortedType = 7

        startSort()

    }

    fun setSortByCurrencyShort(){
        if(::vm.isInitialized) vm.sortedType.value = 8
        else tempSortedType = 8

        startSort()

    }

    fun setSortByCapital(){
        if(::vm.isInitialized) vm.sortedType.value = 9
        else tempSortedType = 9

        startSort()

    }

    fun setSortPrioritizeByName(list : ArrayList<String>){
        if(::vm.isInitialized) vm.sortedPrioritize.value = list
        else tempSortedPrioritize = list

        startSort()

    }

    private fun startSort(){
        adapter_rv.apply {
            val temp = if(::vm.isInitialized) modelsFull.sortedBy(vm.sortedPrioritize.value!!, vm.sortedType.value!!)
            else modelsFull.sortedBy(tempSortedPrioritize, tempSortedType)

            modelsFull = temp

            if(::vm.isInitialized) submitList(modelsFull)

        }

    }

    //Search
    fun setSearchVisiblity(b: Boolean){
        if(::vm.isInitialized) vm.searchVisibility.value = b
        else tempSearchVisibility = b

    }

    fun setSearchHint(s: String){
        if(::vm.isInitialized) vm.searchHint.value = s
        else tempSearchHint = s

    }

    fun setSearchTextColor(c: Int){
        if(::vm.isInitialized) vm.searchColor.value = c
        else tempSearchColor = c

    }

    //Spinner
    fun setSortVisibility(b: Boolean){
        if(::vm.isInitialized) vm.sortedVisiblity.value = b
        else tempSortedVisiblity = b

    }

    //BG
    fun setBackgroundColor(c: Int){
        if(::vm.isInitialized) vm.bgColor.value = c
        else tempBgColor = c

    }

    fun setBackgroundColorButton(d: Int){
        if(::vm.isInitialized) vm.bgColorSubmit.value = d
        else tempBgColorSubmit = d

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

    //initBinding
    private fun initBinding(){
        vm.multiSelectionMin.value = tempMultiSelectionMin
        vm.multiSelectionMax.value = tempMultiSelectionMax
        vm.multiSelectionMsg.value = tempMultiSelectionMsg
        vm.searchScrollType.value = tempSearchScrollType
        vm.sortedType.value = tempSortedType
        vm.sortedPrioritize.value = tempSortedPrioritize
        vm.sortedVisiblity.value = tempSortedVisiblity
        vm.searchVisibility.value = tempSearchVisibility
        vm.searchHint.value = tempSearchHint
        vm.searchColor.value = tempSearchColor
        vm.bgColor.value = tempBgColor
        vm.bgColorSubmit.value = tempBgColorSubmit

        binding.dialogFilterSubmit.visibility = if(adapter_rv.cseq.realSeq(1).visible) VISIBLE else GONE
        binding.dialogFilterSearch.visibility = if(vm.searchVisibility.value!!) VISIBLE else GONE
        binding.dialogFilterSearch.hint = vm.searchHint.value
        binding.dialogFilterSearch.setTextColor(vm.searchColor.value!!)
        binding.dialogFilterSortLl.visibility = if(vm.sortedVisiblity.value!!) VISIBLE else GONE
        binding.dialogFilterCl.setBackgroundColor(vm.bgColor.value!!)
        binding.dialogFilterSubmit.setBackgroundResource(vm.bgColorSubmit.value!!)

    }

}