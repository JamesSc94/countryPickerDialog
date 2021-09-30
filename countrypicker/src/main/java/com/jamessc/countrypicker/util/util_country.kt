package com.example.mylibrary.util

import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.LinearLayout
import com.example.mylibrary.Country
import com.example.mylibrary.CountrySequence
import com.example.mylibrary.CountryTVSpec
import com.example.mylibrary.R

class util_country(private val c : Context) {

    val list_country : List<Country>
        get() {
            var list : MutableList<Country> = arrayListOf()
            val arraySname = c.resources.getStringArray(R.array.country_sname)
            val arrayPrefix = c.resources.getStringArray(R.array.country_prefix)
            val arrayCurrency = c.resources.getStringArray(R.array.country_currency)
            val arraySCurrency = c.resources.getStringArray(R.array.country_scurrency)
            val arrayCapital = c.resources.getStringArray(R.array.country_capital)
            val arrayFlag = c.resources.getStringArray(R.array.country_flag)

            c.resources.getStringArray(R.array.country_name).forEachIndexed { i, s ->
                list.add(Country(i, s, arraySname[i], arrayPrefix[i], arrayCurrency[i],
                    arraySCurrency[i], arrayCapital[i], arrayFlag[i], "", CountryTVSpec(-1f, -1, false),
                    CountryTVSpec(-1f, -1, false), CountryTVSpec(-1f, -1, false),
                    CountryTVSpec(-1f, -1, false), CountryTVSpec(-1f, -1, false),
                    CountryTVSpec(-1f, -1, false), View.VISIBLE, false))

            }

            return list

        }

    val country_sequence : MutableList<CountrySequence>
    get() {
        var list : MutableList<CountrySequence> = arrayListOf()

        //Checkbox
        list.add(CountrySequence(1, 0.1f, 0f, false, 15f, Color.BLACK, false,
            LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT).apply { weight = 0.1f }))

        //Icon
        list.add(CountrySequence(2, 0.1f, 0f, false, 15f, Color.BLACK, false,
            LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT).apply { weight = 0.1f }))

        //Prefix
        list.add(CountrySequence(3, 0.2f, 0f, false, 15f, Color.BLACK, false,
            LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT).apply { weight = 0.2f }))

        //Name
        list.add(CountrySequence(4, 0.5f, 0f, false, 15f, Color.BLACK, false,
            LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT).apply { weight = 0.5f }))

        //Info
        list.add(CountrySequence(5, 0.075f, 0f, false, 15f, Color.BLACK, false,
            LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT).apply { weight = 0.075f }))

        //Short Name
        list.add(CountrySequence(6, 0.2f, 0f, false, 15f, Color.BLACK, false,
            LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT).apply { weight = 0.2f }))

        //Currency
        list.add(CountrySequence(7, 0.2f, 0f, false, 15f, Color.BLACK, false,
            LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT).apply { weight = 0.2f }))

        //Short Currency
        list.add(CountrySequence(8, 0.2f, 0f, false, 15f, Color.BLACK, false,
            LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT).apply { weight = 0.2f }))

        //Capital
        list.add(CountrySequence(9, 0.2f, 0f,false, 15f, Color.BLACK, false,
            LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT).apply { weight = 0.2f }))

        return list

    }

}