package com.jamessc.countrypicker

import android.widget.LinearLayout

data class Country(val id : Int,
                   val name : String,
                   val sname : String,
                   val prefix : String,
                   val currency : String,
                   val scurrency : String,
                   val capital : String,
                   var flag : String,
                   var info : String,
                   var specTVN : CountryTVSpec,
                   var specTVSN : CountryTVSpec,
                   var specTVP : CountryTVSpec,
                   var specTVCur : CountryTVSpec,
                   var specTVSCur : CountryTVSpec,
                   var specTVCap : CountryTVSpec,
                   var isInfoHidden : Int,
                   var isSelected : Boolean) {

}

data class CountrySequence(
    var sequence : Int,
    var weight : Float,
    var spaceLeft : Float = 0f,
    var visible : Boolean,
    var tSize : Float,
    var tColor : Int,
    var tBold : Boolean,
    var lparams : LinearLayout.LayoutParams) {

}

data class CountryTVSpec(
    var size : Float,
    var color : Int,
    var bold : Boolean,

)