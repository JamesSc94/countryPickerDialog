package com.jamessc.countrypicker.util

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
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.content.res.ResourcesCompat
import androidx.core.util.Predicate
import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.jamessc.countrypicker.Country
import com.jamessc.countrypicker.CountrySequence
import com.jamessc.countrypicker.R
import com.jamessc.countrypicker.vm.Vm_dialog_filter
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

        if(v is ImageView && v.id == R.id.cell_dialog_filter_flag && cseq.realSeq(2).visible){
            v.CellInitImageView(cseq.realSeq(i + 1).weight)

        }else{
            v.CellInit(cseq.realSeq(i + 1).weight)

        }

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
    layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, weight).apply {
        setMargins(15,0,0,0)

    }

    return this

}

fun View.CellInitImageView(weight : Float) : View {
    layoutParams = LinearLayout.LayoutParams(0, (500f * weight).toInt(), weight).apply {
        setMargins(15,0,0,0)

    }

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

fun List<Country>.filterSetName(hm : HashMap<String, String>) {
    hm.onEach { entry ->
        this.filterNameOnly(entry.key).apply {
            if (isNotEmpty()){
                this[0].name = entry.value

            }

        }

    }

}

fun List<Country>.filterSetSName(hm : HashMap<String, String>) {
    hm.onEach { entry ->
        this.filterNameOnly(entry.key).apply {
            if (isNotEmpty()){
                this[0].sname = entry.value

            }

        }

    }

}

fun List<Country>.filterSetPrefix(hm : HashMap<String, String>) {
    hm.onEach { entry ->
        this.filterNameOnly(entry.key).apply {
            if (isNotEmpty()){
                this[0].prefix = entry.value

            }

        }

    }

}

fun List<Country>.filterSetCurrency(hm : HashMap<String, String>) {
    hm.onEach { entry ->
        this.filterNameOnly(entry.key).apply {
            if (isNotEmpty()){
                this[0].currency = entry.value

            }

        }

    }

}

fun List<Country>.filterSetSCurrency(hm : HashMap<String, String>) {
    hm.onEach { entry ->
        this.filterNameOnly(entry.key).apply {
            if (isNotEmpty()){
                this[0].scurrency = entry.value

            }

        }

    }

}

fun List<Country>.filterSetCapital(hm : HashMap<String, String>) {
    hm.onEach { entry ->
        this.filterNameOnly(entry.key).apply {
            if (isNotEmpty()){
                this[0].capital = entry.value

            }

        }

    }

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
            4 -> it.name.lowercase()
            6 -> it.sname.lowercase()
            7 -> it.currency.lowercase()
            8 -> it.scurrency.lowercase()
            else ->  {
                it.capital.lowercase()
            }

        }

    })

    return tempPrior

}

fun MutableList<Country>.hiddenInfo(list: List<String>, show: Boolean) {
    this.onEach {
        for(l in list){
            if(it.name.equals(l, true)){
                it.isInfoHidden = if(show) View.VISIBLE else View.INVISIBLE
                break

            }else{
                it.isInfoHidden = if(show) View.INVISIBLE else View.VISIBLE

            }

        }

    }

}

fun MutableList<CountrySequence>.swap(pos : Int, posSet : Int){
    val ori = this.realSeq(pos)
    val hold = get(posSet - 1)

    set(indexOfFirst { it.sequence == ori.sequence }, hold)
    set(posSet - 1, ori)

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