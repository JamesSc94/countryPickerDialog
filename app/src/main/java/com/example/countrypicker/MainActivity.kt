package com.example.countrypicker

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jamessc.countrypicker.DialogCountryJ

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Visible
        val d = DialogCountryJ(this)
        d.setVisibleCheckbox(true)
        d.setVisiblePrefix(true)
        d.setVisibleIcon(true)
        d.setVisibleName(true)
        d.setVisibleNameShort(true)
        d.setVisibleCurrency(true)
        d.setVisibleCurrencyShort(true)
        d.setVisibleCapital(true)
        d.setVisibleInfo(true)

        //Position
//        d.setPositionCheckbox(1)
//        d.setPositionPrefix(1)
//        d.setPositionIcon(1)
//        d.setPositionName(1)
//        d.setPositionNameShort(1)
//        d.setPositionCurrency(1)
//        d.setPositionCurrencyShort(1)
        d.setPositionCapital(1)

        //Weight
//        d.setWeightCheckbox(1f)
//        d.setWeightPrefix(1f)
//        d.setWeightIcon(1f)
//        d.setWeightName(1f)
//        d.setWeightNameShort(1f)
//        d.setWeightCurrency(1f)
//        d.setWeightCurrencyShort(1f)
//        d.setWeightCapital(1f)
//        d.setWeightInfo(1f)

        //Spacing
//        d.setSpacingCheckbox(0.2f)
//        d.setSpacingPrefix(0.2f)
//        d.setSpacingIcon(0.1f)
//        d.setSpacingName(0.3f)
//        d.setSpacingNameShort(0.2f)
//        d.setSpacingCurrency(0.3f)
//        d.setSpacingCurrencyShort(0.1f)
//        d.setSpacingCapital(0.5f)
//        d.setSpacingInfo(0.2f)

        //Multi
        d.setCheckboxSelectionMax(3)
        d.setCheckboxSelectionMin(6)
        d.setCheckboxSelectionMsg("aaa")

        //Info
        val hidden = arrayListOf<String>("Thailand", "")
        val show = arrayListOf<String>("Singapore", "Malaysia")
        val showTemp = arrayListOf<String>("Afghanistan")
        val showSize = arrayListOf<String>("Malaysia")

        val hm = HashMap<String, String>()
        hm["Malaysia"] = "malaysia individual"
        hm["Indonesia"] = "indonesia individualllllllllll lllll l ll lll l l l l ll l ll ll lll l ll l l l l ll"
        d.setInfoMessageAll("All msg")
        d.setInfoMessageCustom(hm)
        d.setInfoHiddenOnly(hidden)
        d.setInfoShowOnly(showTemp)

        //Hidden
//        d.setHiddenOnlyWithName(hidden)
//        d.setShowOnlyWithName(show)

        //Filter
//        d.setFilterByName()
//        d.setFilterByNameShort()
//        d.setFilterByPrefix()
//        d.setFilterByCurreny()
//        d.setFilterByCurrencyShort()
//        d.setFilterByCapital()
        d.setFilterByAll()
//        d.setFilterScrollByName()
//        d.setFilterScrollByNameShort()
//        d.setFilterScrollByPrefix()
//        d.setFilterScrollByCurreny()
//        d.setFilterScrollByCurrencyShort()
//        d.setFilterScrollByCapital()

        //TextView
        d.setTextViewSizeName(20f)
        d.setTextViewSizeNameShort(12f)
        d.setTextViewSizePrefix(25f)
        d.setTextViewSizeCurrency(18f)
        d.setTextViewSizeCurrencyShort(30f)
        d.setTextViewSizeCapital(10f)
        d.setTextViewSizeAll(10f)

        d.setTextViewBoldName(true)
        d.setTextViewBoldNameShort(false)
        d.setTextViewBoldPrefix(true)
        d.setTextViewBoldCurrency(false)
        d.setTextViewBoldCurrencyShort(false)
        d.setTextViewBoldCapital(false)
        d.setTextViewBoldAll(true)

        d.setTextViewColorName(Color.BLUE)
        d.setTextViewColorNameShort(Color.BLUE)
        d.setTextViewColorPrefix(Color.BLUE)
        d.setTextViewColorCurrency(Color.BLUE)
        d.setTextViewColorCurrencyShort(Color.BLUE)
        d.setTextViewColorCapital(Color.BLUE)
        d.setTextViewColorAll(Color.BLUE)

        //TextView Custom
        d.setTextViewPrefixSizeWithName(showSize, 15f)
        d.setTextViewNameSizeWithName(showSize, 30f)
        d.setTextViewNameShortSizeWithName(showSize, 25f)
        d.setTextViewCurrencySizeWithName(showSize, 10f)
        d.setTextViewCurrencyShortSizeWithName(showSize, 5f)
        d.setTextViewCapitalSizeWithName(showSize, 20f)

        d.setTextViewPrefixBoldWithName(show, true)
        d.setTextViewNameBoldWithName(show, true)
        d.setTextViewNameShortBoldWithName(show, true)
        d.setTextViewCurrencyBoldWithName(show, true)
        d.setTextViewCurrencyShortBoldWithName(show, true)
        d.setTextViewCapitalBoldWithName(show, true)

        d.setTextViewPrefixColorWithName(show, Color.RED)
        d.setTextViewNameColorWithName(show, Color.RED)
        d.setTextViewNameShortColorWithName(show, Color.RED)
        d.setTextViewCurrencyColorWithName(show, Color.RED)
        d.setTextViewCurrencyShortColorWithName(show, Color.RED)
        d.setTextViewCapitalColorWithName(show, Color.RED)

        //Sort
        d.setSortByName()
        d.setSortByNameShort()
        d.setSortByCurrency()
        d.setSortByCurrencyShort()
        d.setSortByCapital()
        d.setSortPrioritizeByName(show)

        //Search
        d.setSearchVisiblity(true)
        d.setSearchHint("aaaaaa")
        d.setSearchTextColor(Color.BLUE)

        //Spinner
        d.setSortVisibility(true)

        //BG
        d.setBackgroundColor(Color.GRAY)
        d.setBackgroundColorButton(R.drawable.button_submit)
        d.setBackgroundListOddColor(Color.GREEN)
        d.setBackgroundListEvenColor(Color.CYAN)

        d.show(this.supportFragmentManager, "")

        d.setInfoMessageAll("cacacacacaca")
        d.setSearchHint("bbbbbb")
        d.setTextViewSizeAll(25f)

    }

}