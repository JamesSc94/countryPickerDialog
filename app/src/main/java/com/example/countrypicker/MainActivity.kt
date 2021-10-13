package com.example.countrypicker

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.jamessc.countrypicker.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rvt = findViewById<CountryRecyclerview>(R.id.activity_main_rv)
        val btn = findViewById<Button>(R.id.activity_main_button)

        //Info
        val hidden = arrayListOf<String>("Thailand", "")
        val show = arrayListOf<String>("Singapore", "Malaysia")
        val showTemp = arrayListOf<String>("Afghanistan")
        val showSize = arrayListOf<String>("Malaysia")

        val hm = HashMap<String, String>()
        hm["Malaysia"] = "malaysia individual"
        hm["Indonesia"] = "indonesia"

        val hmmanual = HashMap<String, String>()
        hmmanual["Malaysia"] = "alaysia"
        hmmanual["Indonesia"] = "ndonesia"

        val rv = findViewById<CountryRecyclerview>(R.id.activity_main_rv)
        rv.setVisibleCheckbox(true)
        rv.setVisiblePrefix(true)
        rv.setVisibleIcon(true)
        rv.setVisibleName(true)
        rv.setVisibleNameShort(true)
        rv.setVisibleCurrency(true)
        rv.setVisibleCurrencyShort(true)
        rv.setVisibleCapital(true)
        rv.setVisibleInfo(true)

        //Position
        rv.setPositionCheckbox(1)
        rv.setPositionPrefix(1)
        rv.setPositionIcon(1)
        rv.setPositionName(1)
        rv.setPositionNameShort(1)
        rv.setPositionCurrency(1)
        rv.setPositionCurrencyShort(1)
        rv.setPositionCapital(1)

        //Weight
        rv.setWeightCheckbox(1f)
        rv.setWeightPrefix(1f)
        rv.setWeightIcon(1f)
        rv.setWeightName(1f)
        rv.setWeightNameShort(1f)
        rv.setWeightCurrency(1f)
        rv.setWeightCurrencyShort(1f)
        rv.setWeightCapital(1f)
        rv.setWeightInfo(1f)

        //Spacing
        rv.setSpacingCheckbox(0.2f)
        rv.setSpacingPrefix(0.2f)
        rv.setSpacingIcon(0.1f)
        rv.setSpacingName(0.3f)
        rv.setSpacingNameShort(0.2f)
        rv.setSpacingCurrency(0.3f)
        rv.setSpacingCurrencyShort(0.1f)
        rv.setSpacingCapital(0.5f)
        rv.setSpacingInfo(0.2f)

        //Info
        rv.setInfoMessageAll("All msg")
        rv.setInfoMessageCustom(hm)
        rv.setInfoHiddenOnly(hidden)
        rv.setInfoShowOnly(showTemp)

        //Hidden
        rv.setHiddenOnlyWithName(hidden)
        rv.setShowOnlyWithName(show)

        //TextView
        rv.setTextViewSizeName(20f)
        rv.setTextViewSizeNameShort(12f)
        rv.setTextViewSizePrefix(25f)
        rv.setTextViewSizeCurrency(18f)
        rv.setTextViewSizeCurrencyShort(30f)
        rv.setTextViewSizeCapital(10f)
        rv.setTextViewSizeAll(10f)

        rv.setTextViewBoldName(true)
        rv.setTextViewBoldNameShort(false)
        rv.setTextViewBoldPrefix(true)
        rv.setTextViewBoldCurrency(false)
        rv.setTextViewBoldCurrencyShort(false)
        rv.setTextViewBoldCapital(false)
        rv.setTextViewBoldAll(true)

        rv.setTextViewColorName(Color.BLUE)
        rv.setTextViewColorNameShort(Color.BLUE)
        rv.setTextViewColorPrefix(Color.BLUE)
        rv.setTextViewColorCurrency(Color.BLUE)
        rv.setTextViewColorCurrencyShort(Color.BLUE)
        rv.setTextViewColorCapital(Color.BLUE)
        rv.setTextViewColorAll(Color.BLUE)

        //TextView Custom
        rv.setTextViewPrefixSizeWithName(showSize, 15f)
        rv.setTextViewNameSizeWithName(showSize, 30f)
        rv.setTextViewNameShortSizeWithName(showSize, 25f)
        rv.setTextViewCurrencySizeWithName(showSize, 10f)
        rv.setTextViewCurrencyShortSizeWithName(showSize, 5f)
        rv.setTextViewCapitalSizeWithName(showSize, 20f)

        rv.setTextViewPrefixBoldWithName(show, true)
        rv.setTextViewNameBoldWithName(show, true)
        rv.setTextViewNameShortBoldWithName(show, true)
        rv.setTextViewCurrencyBoldWithName(show, true)
        rv.setTextViewCurrencyShortBoldWithName(show, true)
        rv.setTextViewCapitalBoldWithName(show, true)

        rv.setTextViewPrefixColorWithName(show, Color.RED)
        rv.setTextViewNameColorWithName(show, Color.RED)
        rv.setTextViewNameShortColorWithName(show, Color.RED)
        rv.setTextViewCurrencyColorWithName(show, Color.RED)
        rv.setTextViewCurrencyShortColorWithName(show, Color.RED)
        rv.setTextViewCapitalColorWithName(show, Color.RED)

        //Sort
        rv.setSortByName()
        rv.setSortByNameShort()
        rv.setSortByCurrency()
        rv.setSortByCurrencyShort()
        rv.setSortByCapital()
        rv.setSortPrioritizeByName(show)

        //BG
        rv.setBackgroundColor(Color.GRAY)
        rv.setBackgroundListOddColor(Color.GREEN)
        rv.setBackgroundListEvenColor(Color.CYAN)

        rv.setCategoryVisible(true)
        rv.setCategoryColor(Color.BLUE)
        rv.setCategoryBGColor(Color.GREEN)
        rv.setDivider(true)

        rv.setManualPrefixChange(hmmanual)
        rv.setManualNameShortChange(hmmanual)
        rv.setManualCurrencyChange(hmmanual)
        rv.setManualCurrencyShortChange(hmmanual)
        rv.setManualCapitalChange(hmmanual)
        rv.setManualNameChange(hmmanual)

        rv.setOnSelectionListener(object : OnSelectionListener {
            override fun onResult(c : Country) {

            }

        })

        rv.getMultiSelected()

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
        d.setPositionCheckbox(1)
        d.setPositionPrefix(1)
        d.setPositionIcon(1)
        d.setPositionName(1)
        d.setPositionNameShort(1)
        d.setPositionCurrency(1)
        d.setPositionCurrencyShort(1)
        d.setPositionCapital(1)

        //Weight
        d.setWeightCheckbox(1f)
        d.setWeightPrefix(1f)
        d.setWeightIcon(1f)
        d.setWeightName(1f)
        d.setWeightNameShort(1f)
        d.setWeightCurrency(1f)
        d.setWeightCurrencyShort(1f)
        d.setWeightCapital(1f)
        d.setWeightInfo(1f)

        //Spacing
        d.setSpacingCheckbox(0.2f)
        d.setSpacingPrefix(0.2f)
        d.setSpacingIcon(0.1f)
        d.setSpacingName(0.3f)
        d.setSpacingNameShort(0.2f)
        d.setSpacingCurrency(0.3f)
        d.setSpacingCurrencyShort(0.1f)
        d.setSpacingCapital(0.5f)
        d.setSpacingInfo(0.2f)

        //Multi
        d.setCheckboxSelectionMax(3)
        d.setCheckboxSelectionMin(6)
        d.setCheckboxSelectionMsg("aaa")

        d.setInfoMessageAll("All msg")
        d.setInfoMessageCustom(hm)
        d.setInfoHiddenOnly(hidden)
        d.setInfoShowOnly(showTemp)

        //Hidden
        d.setHiddenOnlyWithName(hidden)
        d.setShowOnlyWithName(show)

        //Filter
        d.setFilterByName()
        d.setFilterByNameShort()
        d.setFilterByPrefix()
        d.setFilterByCurreny()
        d.setFilterByCurrencyShort()
        d.setFilterByCapital()
        d.setFilterByAll()
        d.setFilterScrollByName()
        d.setFilterScrollByNameShort()
        d.setFilterScrollByPrefix()
        d.setFilterScrollByCurreny()
        d.setFilterScrollByCurrencyShort()
        d.setFilterScrollByCapital()

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

        d.setCategoryVisible(true)
        d.setCategoryColor(Color.BLUE)
        d.setCategoryBGColor(Color.GREEN)
        d.setDivider(true)

        d.setManualPrefixChange(hmmanual)
        d.setManualNameShortChange(hmmanual)
        d.setManualCurrencyChange(hmmanual)
        d.setManualCurrencyShortChange(hmmanual)
        d.setManualCapitalChange(hmmanual)
        d.setManualNameChange(hmmanual)


//        d.show(this.supportFragmentManager, "")

        d.setOnSelectionListener(object : OnSelectionListener {
            override fun onResult(c : Country) {

            }

        })

        d.setOnMultiSelectionListener(object : OnMultiSelectionListener {
            override fun onResult(c : List<Country>) {

            }

        })

    }

}