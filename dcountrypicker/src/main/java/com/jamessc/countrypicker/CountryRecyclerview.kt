package com.jamessc.countrypicker

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jamessc.countrypicker.adapter.MultiButtonEnable
import com.jamessc.countrypicker.adapter.adapterCountryRecyclerview
import com.jamessc.countrypicker.adapter.adapter_DialogFilter
import com.jamessc.countrypicker.util.*

class CountryRecyclerview(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
    RecyclerView(context, attrs, defStyleAttr),
    OnSelectionListener, OnMultiSelectionListener {

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context) : this(context, null, 0)

    private var listenerSingle : OnSelectionListener = this
    private var listenerMulti : OnMultiSelectionListener = this

    private var adapt : adapterCountryRecyclerview

    init {
        layoutManager = llm_smooth(context, LinearLayoutManager.VERTICAL, false)
        adapt = adapterCountryRecyclerview(util_country(context).country_sequence, this, listenerSingle).apply {
            modelsFull = util_country(context).list_country as MutableList<Country>
            submitList(modelsFull)

        }

        adapter = adapt

    }

    //VISIBILITY
    fun setVisibleCheckbox(b : Boolean){
        (adapter as adapterCountryRecyclerview).cseq.realSeq(1).visible = b

    }

    fun setVisibleIcon(b : Boolean){
        (adapter as adapterCountryRecyclerview).cseq.realSeq(2).visible = b

    }

    fun setVisiblePrefix(b : Boolean){
        (adapter as adapterCountryRecyclerview).cseq.realSeq(3).visible = b

    }

    fun setVisibleName(b : Boolean){
        (adapter as adapterCountryRecyclerview).cseq.realSeq(4).visible = b

    }

    fun setVisibleInfo(b : Boolean){
        (adapter as adapterCountryRecyclerview).cseq.realSeq(5).visible = b

    }

    fun setVisibleNameShort(b : Boolean){
        (adapter as adapterCountryRecyclerview).cseq.realSeq(6).visible = b

    }

    fun setVisibleCurrency(b : Boolean){
        (adapter as adapterCountryRecyclerview).cseq.realSeq(7).visible = b

    }

    fun setVisibleCurrencyShort(b : Boolean){
        (adapter as adapterCountryRecyclerview).cseq.realSeq(8).visible = b

    }

    fun setVisibleCapital(b : Boolean){
        (adapter as adapterCountryRecyclerview).cseq.realSeq(9).visible = b

    }

    //POSITION
    fun setPositionCheckbox(pos : Int){
        (adapter as adapterCountryRecyclerview).cseq.swap(1, pos)

    }

    fun setPositionIcon(pos : Int){
        (adapter as adapterCountryRecyclerview).cseq.swap(2, pos)

    }

    fun setPositionPrefix(pos : Int){
        (adapter as adapterCountryRecyclerview).cseq.swap(3, pos)

    }

    fun setPositionName(pos : Int){
        (adapter as adapterCountryRecyclerview).cseq.swap(4, pos)

    }

    fun setPositionInfo(pos : Int){
        (adapter as adapterCountryRecyclerview).cseq.swap(5, pos)

    }

    fun setPositionNameShort(pos : Int){
        (adapter as adapterCountryRecyclerview).cseq.swap(6, pos)

    }

    fun setPositionCurrency(pos : Int){
        (adapter as adapterCountryRecyclerview).cseq.swap(7, pos)

    }

    fun setPositionCurrencyShort(pos : Int){
        (adapter as adapterCountryRecyclerview).cseq.swap(8, pos)

    }

    fun setPositionCapital(pos : Int){
        (adapter as adapterCountryRecyclerview).cseq.swap(9, pos)

    }

    //Weight
    fun setWeightCheckbox(weight : Float){
        (adapter as adapterCountryRecyclerview).cseq.realSeq(1).weight = weight

    }

    fun setWeightIcon(weight : Float){
        (adapter as adapterCountryRecyclerview).cseq.realSeq(2).weight = weight

    }

    fun setWeightPrefix(weight : Float){
        (adapter as adapterCountryRecyclerview).cseq.realSeq(3).weight = weight

    }

    fun setWeightName(weight : Float){
        (adapter as adapterCountryRecyclerview).cseq.realSeq(4).weight = weight

    }

    fun setWeightInfo(weight : Float){
        (adapter as adapterCountryRecyclerview).cseq.realSeq(5).weight = weight

    }

    fun setWeightNameShort(weight : Float){
        (adapter as adapterCountryRecyclerview).cseq.realSeq(6).weight = weight

    }

    fun setWeightCurrency(weight : Float){
        (adapter as adapterCountryRecyclerview).cseq.realSeq(7).weight = weight

    }

    fun setWeightCurrencyShort(weight : Float){
        (adapter as adapterCountryRecyclerview).cseq.realSeq(8).weight = weight

    }

    fun setWeightCapital(weight : Float){
        (adapter as adapterCountryRecyclerview).cseq.realSeq(9).weight = weight

    }

    //Spacing
    fun setSpacingCheckbox(weight : Float){
        (adapter as adapterCountryRecyclerview).cseq.realSeq(1).spaceLeft = weight

    }

    fun setSpacingIcon(weight : Float){
        (adapter as adapterCountryRecyclerview).cseq.realSeq(2).spaceLeft = weight

    }

    fun setSpacingPrefix(weight : Float){
        (adapter as adapterCountryRecyclerview).cseq.realSeq(3).spaceLeft = weight

    }

    fun setSpacingName(weight : Float){
        (adapter as adapterCountryRecyclerview).cseq.realSeq(4).spaceLeft = weight

    }

    fun setSpacingInfo(weight : Float){
        (adapter as adapterCountryRecyclerview).cseq.realSeq(5).spaceLeft = weight

    }

    fun setSpacingNameShort(weight : Float){
        (adapter as adapterCountryRecyclerview).cseq.realSeq(6).spaceLeft = weight

    }

    fun setSpacingCurrency(weight : Float){
        (adapter as adapterCountryRecyclerview).cseq.realSeq(7).spaceLeft = weight

    }

    fun setSpacingCurrencyShort(weight : Float){
        (adapter as adapterCountryRecyclerview).cseq.realSeq(8).spaceLeft = weight

    }

    fun setSpacingCapital(weight : Float){
        (adapter as adapterCountryRecyclerview).cseq.realSeq(9).spaceLeft = weight

    }

    //Info
    fun setInfoMessageAll(info : String){
        (adapter as adapterCountryRecyclerview).apply {
            modelsFull.onEach {
                it.info = info

            }

        }

    }

    fun setInfoMessageCustom(info : HashMap<String, String>){
        (adapter as adapterCountryRecyclerview).modelsFull.filterSetInfoName(info)

    }

    fun setInfoHiddenOnly(list : List<String>){
        (adapter as adapterCountryRecyclerview).apply {
            modelsFull.hiddenInfo(list, false)

        }

    }

    fun setInfoShowOnly(list : List<String>){
        (adapter as adapterCountryRecyclerview).apply {
            modelsFull.hiddenInfo(list, true)

        }

    }

    //Hidden
    fun setHiddenOnlyWithName(list : List<String>){
        (adapter as adapterCountryRecyclerview).apply {
            modelsFull = modelsFull.filterName(list, false) as MutableList<Country>

        }

    }

    fun setShowOnlyWithName(list : List<String>){
        (adapter as adapterCountryRecyclerview).apply {
            modelsFull = modelsFull.filterName(list, true) as MutableList<Country>

        }

    }

    //TextView Size
    fun setTextViewSizePrefix(size : Float){
        (adapter as adapterCountryRecyclerview).cseq.realSeq(3).tSize = size

    }

    fun setTextViewSizeName(size : Float){
        (adapter as adapterCountryRecyclerview).cseq.realSeq(4).tSize = size

    }

    fun setTextViewSizeNameShort(size : Float){
        (adapter as adapterCountryRecyclerview).cseq.realSeq(6).tSize = size

    }

    fun setTextViewSizeCurrency(size : Float){
        (adapter as adapterCountryRecyclerview).cseq.realSeq(7).tSize = size

    }

    fun setTextViewSizeCurrencyShort(size : Float){
        (adapter as adapterCountryRecyclerview).cseq.realSeq(8).tSize = size

    }

    fun setTextViewSizeCapital(size : Float){
        (adapter as adapterCountryRecyclerview).cseq.realSeq(9).tSize = size

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
        (adapter as adapterCountryRecyclerview).apply {
            val temp = modelsFull.filterNameListOnlyRLI(list)

            if(temp.isNotEmpty()){
                temp.forEach {
                    (adapter as adapterCountryRecyclerview).modelsFull[it].specTVP.size = size

                }

            }

        }

    }

    fun setTextViewNameSizeWithName(list : List<String>, size : Float){
        (adapter as adapterCountryRecyclerview).apply {
            val temp = modelsFull.filterNameListOnlyRLI(list)

            if(temp.isNotEmpty()){
                temp.forEach {
                    (adapter as adapterCountryRecyclerview).modelsFull[it].specTVN.size = size

                }

            }

        }

    }

    fun setTextViewNameShortSizeWithName(list : List<String>, size : Float){
        (adapter as adapterCountryRecyclerview).apply {
            val temp = modelsFull.filterNameListOnlyRLI(list)

            if(temp.isNotEmpty()){
                temp.forEach {
                    (adapter as adapterCountryRecyclerview).modelsFull[it].specTVSN.size = size

                }

            }

        }

    }

    fun setTextViewCurrencySizeWithName(list : List<String>, size : Float){
        (adapter as adapterCountryRecyclerview).apply {
            val temp = modelsFull.filterNameListOnlyRLI(list)

            if(temp.isNotEmpty()){
                temp.forEach {
                    (adapter as adapterCountryRecyclerview).modelsFull[it].specTVCur.size = size

                }

            }

        }

    }

    fun setTextViewCurrencyShortSizeWithName(list : List<String>, size : Float){
        (adapter as adapterCountryRecyclerview).apply {
            val temp = modelsFull.filterNameListOnlyRLI(list)

            if(temp.isNotEmpty()){
                temp.forEach {
                    (adapter as adapterCountryRecyclerview).modelsFull[it].specTVSCur.size = size

                }

            }

        }

    }

    fun setTextViewCapitalSizeWithName(list : List<String>, size : Float){
        (adapter as adapterCountryRecyclerview).apply {
            val temp = modelsFull.filterNameListOnlyRLI(list)

            if(temp.isNotEmpty()){
                temp.forEach {
                    (adapter as adapterCountryRecyclerview).modelsFull[it].specTVCap.size = size

                }

            }

        }

    }

    //TextView Color
    fun setTextViewColorPrefix(color : Int){
        (adapter as adapterCountryRecyclerview).cseq.realSeq(3).tColor = color

    }

    fun setTextViewColorName(color : Int){
        (adapter as adapterCountryRecyclerview).cseq.realSeq(4).tColor = color

    }

    fun setTextViewColorNameShort(color : Int){
        (adapter as adapterCountryRecyclerview).cseq.realSeq(6).tColor = color

    }

    fun setTextViewColorCurrency(color : Int){
        (adapter as adapterCountryRecyclerview).cseq.realSeq(7).tColor = color

    }

    fun setTextViewColorCurrencyShort(color : Int){
        (adapter as adapterCountryRecyclerview).cseq.realSeq(8).tColor = color

    }

    fun setTextViewColorCapital(color : Int){
        (adapter as adapterCountryRecyclerview).cseq.realSeq(9).tColor = color

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
        (adapter as adapterCountryRecyclerview).apply {
            val temp = modelsFull.filterNameListOnlyRLI(list)

            if(temp.isNotEmpty()){
                temp.forEach {
                    (adapter as adapterCountryRecyclerview).modelsFull[it].specTVP.color = color

                }

            }

        }

    }

    fun setTextViewNameColorWithName(list : List<String>, color : Int){
        (adapter as adapterCountryRecyclerview).apply {
            val temp = modelsFull.filterNameListOnlyRLI(list)

            if(temp.isNotEmpty()){
                temp.forEach {
                    (adapter as adapterCountryRecyclerview).modelsFull[it].specTVN.color = color

                }

            }

        }

    }

    fun setTextViewNameShortColorWithName(list : List<String>, color : Int){
        (adapter as adapterCountryRecyclerview).apply {
            val temp = modelsFull.filterNameListOnlyRLI(list)

            if(temp.isNotEmpty()){
                temp.forEach {
                    (adapter as adapterCountryRecyclerview).modelsFull[it].specTVSN.color = color

                }

            }

        }

    }

    fun setTextViewCurrencyColorWithName(list : List<String>, color : Int){
        (adapter as adapterCountryRecyclerview).apply {
            val temp = modelsFull.filterNameListOnlyRLI(list)

            if(temp.isNotEmpty()){
                temp.forEach {
                    (adapter as adapterCountryRecyclerview).modelsFull[it].specTVCur.color = color

                }

            }

        }

    }

    fun setTextViewCurrencyShortColorWithName(list : List<String>, color : Int){
        (adapter as adapterCountryRecyclerview).apply {
            val temp = modelsFull.filterNameListOnlyRLI(list)

            if(temp.isNotEmpty()){
                temp.forEach {
                    (adapter as adapterCountryRecyclerview).modelsFull[it].specTVSCur.color = color

                }

            }

        }

    }

    fun setTextViewCapitalColorWithName(list : List<String>, color : Int){
        (adapter as adapterCountryRecyclerview).apply {
            val temp = modelsFull.filterNameListOnlyRLI(list)

            if(temp.isNotEmpty()){
                temp.forEach {
                    (adapter as adapterCountryRecyclerview).modelsFull[it].specTVCap.color = color

                }

            }

        }

    }

    //TextView Bold
    fun setTextViewBoldPrefix(b : Boolean){
        (adapter as adapterCountryRecyclerview).cseq.realSeq(3).tBold = b

    }

    fun setTextViewBoldName(b : Boolean){
        (adapter as adapterCountryRecyclerview).cseq.realSeq(4).tBold = b

    }

    fun setTextViewBoldNameShort(b : Boolean){
        (adapter as adapterCountryRecyclerview).cseq.realSeq(6).tBold = b

    }

    fun setTextViewBoldCurrency(b : Boolean){
        (adapter as adapterCountryRecyclerview).cseq.realSeq(7).tBold = b

    }

    fun setTextViewBoldCurrencyShort(b : Boolean){
        (adapter as adapterCountryRecyclerview).cseq.realSeq(8).tBold = b

    }

    fun setTextViewBoldCapital(b : Boolean){
        (adapter as adapterCountryRecyclerview).cseq.realSeq(9).tBold = b

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
        (adapter as adapterCountryRecyclerview).apply {
            val temp = modelsFull.filterNameListOnlyRLI(list)

            if(temp.isNotEmpty()){
                temp.forEach {
                    (adapter as adapterCountryRecyclerview).modelsFull[it].specTVP.bold = bold

                }

            }

        }

    }

    fun setTextViewNameBoldWithName(list : List<String>, bold : Boolean){
        (adapter as adapterCountryRecyclerview).apply {
            val temp = modelsFull.filterNameListOnlyRLI(list)

            if(temp.isNotEmpty()){
                temp.forEach {
                    (adapter as adapterCountryRecyclerview).modelsFull[it].specTVN.bold = bold

                }

            }

        }

    }

    fun setTextViewNameShortBoldWithName(list : List<String>, bold : Boolean){
        (adapter as adapterCountryRecyclerview).apply {
            val temp = modelsFull.filterNameListOnlyRLI(list)

            if(temp.isNotEmpty()){
                temp.forEach {
                    (adapter as adapterCountryRecyclerview).modelsFull[it].specTVSN.bold = bold

                }

            }

        }

    }

    fun setTextViewCurrencyBoldWithName(list : List<String>, bold : Boolean){
        (adapter as adapterCountryRecyclerview).apply {
            val temp = modelsFull.filterNameListOnlyRLI(list)

            if(temp.isNotEmpty()){
                temp.forEach {
                    (adapter as adapterCountryRecyclerview).modelsFull[it].specTVCur.bold = bold

                }

            }

        }

    }

    fun setTextViewCurrencyShortBoldWithName(list : List<String>, bold : Boolean){
        (adapter as adapterCountryRecyclerview).apply {
            val temp = modelsFull.filterNameListOnlyRLI(list)

            if(temp.isNotEmpty()){
                temp.forEach {
                    (adapter as adapterCountryRecyclerview).modelsFull[it].specTVSCur.bold = bold

                }

            }

        }

    }

    fun setTextViewCapitalBoldWithName(list : List<String>, bold : Boolean){
        (adapter as adapterCountryRecyclerview).apply {
            val temp = modelsFull.filterNameListOnlyRLI(list)

            if(temp.isNotEmpty()){
                temp.forEach {
                    (adapter as adapterCountryRecyclerview).modelsFull[it].specTVCap.bold = bold

                }

            }

        }

    }

    //Sort
    fun setSortByName(){
        (adapter as adapterCountryRecyclerview).apply {
            SortedType = 4

        }

        startSort()

    }

    fun setSortByNameShort(){
        (adapter as adapterCountryRecyclerview).apply {
            SortedType = 6

        }

        startSort()

    }

    fun setSortByCurrency(){
        (adapter as adapterCountryRecyclerview).apply {
            SortedType = 7

        }

        startSort()

    }

    fun setSortByCurrencyShort(){
        (adapter as adapterCountryRecyclerview).apply {
            SortedType = 8

        }

        startSort()

    }

    fun setSortByCapital(){
        (adapter as adapterCountryRecyclerview).apply {
            SortedType = 9

        }

        startSort()

    }

    fun setSortPrioritizeByName(list : ArrayList<String>){
        (adapter as adapterCountryRecyclerview).apply {
            SortedPrioritize = list

        }

        startSort()

    }

    private fun startSort(){
        (adapter as adapterCountryRecyclerview).apply {
            modelsFull.sortedBy(SortedPrioritize, SortedType)
            submitList(modelsFull)

        }

    }

    //BG
    fun setBgColor(c: Int){
        (adapter as adapterCountryRecyclerview).apply {
            BgColor = c

        }

    }

    fun setBackgroundListOddColor(c: Int){
        (adapter as adapterCountryRecyclerview).oddColor = c

    }

    fun setBackgroundListEvenColor(c: Int){
        (adapter as adapterCountryRecyclerview).evenColor = c

    }

    override fun onResult(c: List<Country>) {

    }

    override fun onResult(c: Country) {

    }

    //Selection
    fun setOnSelectionListener(watcher : OnSelectionListener) {
        listenerSingle = watcher

    }

    fun getMultiSelected() : List<Country> {
        return adapt.currentList.filter { it.isSelected }

    }

}