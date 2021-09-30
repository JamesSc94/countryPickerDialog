package com.example.mylibrary.util

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Size
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.content.res.ResourcesCompat
import androidx.core.util.Predicate
import androidx.core.view.children
import androidx.databinding.DataBindingUtil
import com.example.mylibrary.Country
import com.example.mylibrary.CountrySequence
import com.example.mylibrary.R
import com.example.mylibrary.databinding.DialogPopupDescriptionBinding
import java.util.*
import kotlin.collections.ArrayList

//View
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }
    })

}

fun View.redrawCountrySequence(ll : LinearLayout, cseq : MutableList<CountrySequence>){
    val childrens = arrayListOf<View>()
    val totalWeight = cseq.filter { it.visible }.map { r -> r.weight }.sum() + cseq.filter { it.visible }.map { r -> r.spaceLeft }.sum()

    ll.children.forEachIndexed { i, v ->
        if(v is TextView){
            v.setTextColor(cseq.realSeq(i + 1).tColor)
            v.textSize = cseq.realSeq(i + 1).tSize
            v.typeface = if(cseq.realSeq(i + 1).tBold) Typeface.DEFAULT_BOLD else Typeface.DEFAULT

        }

        v.CellInit(cseq.realSeq(i + 1).weight)

        childrens.add(v)

    }

    ll.removeAllViews()

    if(totalWeight > 1){
        var tempWeight = 0f
        var sumWeight = 0f

        var llNew = LinearLayout(ll.context).CellInit()

        ll.orientation = LinearLayout.VERTICAL

        cseq.forEachIndexed { _, s ->
            if(s.visible){
                if(tempWeight + s.weight + s.spaceLeft < 1){
                    if(s.spaceLeft > 0){
                        llNew.addView(Space(ll.context).CellInit(s.spaceLeft))

                    }

                    llNew.addView(childrens[s.sequence - 1])
                    tempWeight += s.weight + s.spaceLeft
                    sumWeight += s.weight + s.spaceLeft

                    if (sumWeight == totalWeight){
                        ll.addView(llNew)

                    }

                }else{
                    tempWeight = 0f

                    ll.addView(llNew)

                    llNew = LinearLayout(ll.context).CellInit()

                    if(s.spaceLeft > 0){
                        llNew.addView(Space(ll.context).CellInit(s.spaceLeft))

                    }

                    llNew.addView(childrens[s.sequence - 1])
                    tempWeight += s.weight + s.spaceLeft
                    sumWeight += s.weight + s.spaceLeft

                    if (sumWeight == totalWeight){
                        ll.addView(llNew)

                    }

                }

            }

        }

    }else{
        for (s in cseq){
            if(s.visible){
                if(s.spaceLeft > 0){
                    ll.addView(Space(ll.context).CellInit(s.spaceLeft))

                }

                ll.addView(childrens[s.sequence - 1])

            }

        }

    }

    ll.invalidate()

}

fun LinearLayout.CellInit() : LinearLayout{
    layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
    orientation = LinearLayout.HORIZONTAL
    weightSum = 1f
    gravity = Gravity.CENTER_VERTICAL

    return this

}

fun Space.CellInit(weight : Float) : Space {
    layoutParams = LinearLayout.LayoutParams(0, 0, weight)

    return this

}

fun View.CellInit(weight : Float) : View {
    layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, weight)

    return this

}

fun PopupWindow.normal(anchor : View, s : String){
    PopupWindow(anchor.context).apply {
        isOutsideTouchable = true
        val inflater = LayoutInflater.from(anchor.context)
        contentView = inflater.inflate(R.layout.dialog_popup_description, null).apply {
            measure(
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
            )

        }

    }.also { popupWindow ->
        val location = IntArray(2).apply {
            anchor.getLocationOnScreen(this)
        }
        val size = Size(
            popupWindow.contentView.measuredWidth,
            popupWindow.contentView.measuredHeight
        )
        popupWindow.showAtLocation(
            anchor,
            Gravity.TOP or Gravity.START,
            location[0] - (size.width - anchor.width) / 2,
            location[1] - size.height
        )

        popupWindow.contentView.findViewById<TextView>(R.id.dialog_popup_description_info).text = s

    }

}

//Function
fun MutableList<Country>.filterName(list: List<String>, show: Boolean) = filter {
    val temp : MutableList<Country> = mutableListOf<Country>()

    this.forEach {
        var matches = show

        list.forEach { s ->
            if(it.name.equals(s, true)){
                matches = !show

            }

        }

        if(!matches) temp.add(it)

    }

    return temp

}

fun List<Country>.filterCheckbox() = filter {
    it.isSelected

}

fun List<Country>.filterNameOnly(s : String) = filter {
    it.name.equals(s, true)

}

fun List<Country>.filterNameListOnlyRLI(list: List<String>) : List<Int> {
    val temp = arrayListOf<Int>()

    this.forEachIndexed { i, it ->
        list.forEach { s ->
            if(it.name.equals(s, true)){
                temp.add(i)

            }

        }

    }

    return temp

}

fun List<Country>.filterNameListNewList(list: List<String>) : ArrayList<Country> {
    val temp = arrayListOf<Country>()

    this.onEach { it ->
        list.forEach { s ->
            if(it.name.equals(s, true)){
                temp.add(it)

            }

        }

    }

    return temp

}

fun List<Country>.filterNameListExclude(list: List<String>) : List<Country> {
    val temp : MutableList<Country> = mutableListOf<Country>()

    this.onEach { it ->
        var exclu = true
        list.forEach { s ->
            if(it.name.equals(s, true)){
                exclu = false

            }

        }

        if(exclu){
            temp.add(it)
        }

    }

    return temp

}

fun List<Country>.filterSetInfoName(hm : HashMap<String, String>) {
    hm.onEach { entry ->
        this.filterNameOnly(entry.key).apply {
            if (isNotEmpty()){
                this[0].info = entry.value

            }

        }

    }

}

fun MutableList<Country>.sortedBy(list: List<String>, type: Int) : MutableList<Country> {
    val tempPrior = this.filterNameListNewList(list)
    val tempRemain = this.filterNameListExclude(list)

    tempPrior.addAll(tempRemain.sortedBy {
        when(type){
            4 -> it.name
            6 -> it.sname
            7 -> it.currency
            8 -> it.scurrency
            else ->  {
                it.capital
            }

        }

    })

    return tempPrior

}

fun MutableList<Country>.hiddenInfo(list: List<String>, show: Boolean) {
    this.onEach {
        list.forEach { s ->
            if(it.name.equals(s, true)){
                it.isInfoHidden = if(show) View.VISIBLE else View.INVISIBLE

            }else{
                if(show){
                    it.isInfoHidden = View.INVISIBLE

                }

            }

        }

    }

}

//fun <T> removeObject(list: MutableList<T>, predicate: Predicate<T>) {
//    val newList: MutableList<T> = ArrayList()
//    list.filter { predicate.test(it) }.forEach { newList.add(it) }
//    list.removeAll(newList)
//
//}

fun MutableList<CountrySequence>.swap(pos : Int, posSet : Int){
    val ori = get(pos - 1)
    val hold = filter { it.sequence == posSet }

    if (hold.isNotEmpty()){
        set(pos - 1, hold[0])
        set(indexOfFirst { it.sequence == hold[0].sequence }, ori)
//        get(pos - 1).sequence = hold[0].sequence
//        get(indexOfFirst { it.sequence == hold[0].sequence }).sequence = ori

    }

}

fun MutableList<CountrySequence>.realSeq(pos : Int) : CountrySequence {
    return filter { it.sequence == pos }[0]

}

fun MutableList<CountrySequence>.tvToColor(darkMode : Boolean){
    this.onEach {
        if(darkMode) it.tColor = Color.WHITE else Color.BLACK
    }

}

fun String.getImage(c : Context) : Drawable? {
    return ResourcesCompat.getDrawable(c.resources, c.resources.getIdentifier(this, "drawable", c.packageName), null)

}