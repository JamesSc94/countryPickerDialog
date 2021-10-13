package com.jamessc.countrypicker.adapter

import android.graphics.Color
import android.graphics.Typeface
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.PopupWindow
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jamessc.countrypicker.*
import com.jamessc.countrypicker.databinding.CellDialogFilteringBinding
import com.jamessc.countrypicker.util.getImage
import com.jamessc.countrypicker.util.normal
import com.jamessc.countrypicker.util.realSeq
import com.jamessc.countrypicker.util.redrawCountrySequence

class adapterCountryRecyclerview constructor(val cseq : MutableList<CountrySequence>,
                                             val crv : CountryRecyclerview,
                                             var listenerSingle : OnSelectionListener
) : ListAdapter<Country, adapterCountryRecyclerview.ItemViewHolder>(adapter_CountryRecycler_DiffCallback()),
    Filterable {

    var filterType = "name"
    var oddColor = Color.TRANSPARENT
    var evenColor = Color.TRANSPARENT
    private val overrideSeq = listOf<Int>(3, 4, 6, 7, 8 ,9)
    var SortedPrioritize = arrayListOf<String>()
    var SortedType = 4
    var BgColor = Color.RED
    var cateSeperator = false
    var cateColor = Color.BLACK
    var cateBGColor = Color.DKGRAY

    lateinit var modelsFull: MutableList<Country>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = CellDialogFilteringBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        binding.root.redrawCountrySequence(binding.cellDialogFilterLl, cseq)

        return ItemViewHolder(
            binding.root, binding,
            cseq, if (cseq.size > 0) cseq.filter { it.sequence == 1 }[0].visible else false, crv,
            overrideSeq, listenerSingle,  cateColor, cateBGColor)

    }

    override fun onBindViewHolder(holder: ItemViewHolder, pos: Int) {
        holder.itemView.setBackgroundColor(if(pos%2 == 0) oddColor else evenColor)
        holder.bind(getItem(pos), if(cateSeperator) pos > 0 && getItem(pos - 1).name.substring(0, 1).equals(getItem(pos).name.substring(0, 1), true) else true)

    }

    class ItemViewHolder(itemView: View, private val binding : CellDialogFilteringBinding,
                         private val cseq: MutableList<CountrySequence>,
                         private val multiSelection : Boolean, private val crv : CountryRecyclerview,
                         private  val overrideSeq : List<Int>,
                         private val listener : OnSelectionListener,
                         private val cateColor: Int, private val cateBGColor: Int) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: Country, secHeader: Boolean) = with(itemView) {
            binding.model = item
            binding.sectiontext = item.name.first().toString()
            binding.sectiontextcolor = cateColor
            binding.sectiontextcolorbg = cateBGColor
            binding.sectiontextvisibility = if (secHeader) View.GONE else View.VISIBLE
            binding.executePendingBindings()

            Glide.with(itemView.context).load(item.flag.getImage(itemView.context)).centerCrop().into(binding.cellDialogFilterFlag)

            overrideSeq.onEach {
                when(it){
                    3 -> {
                        overrideTV(cseq.realSeq(it), binding.cellDialogFilteringPrefix,
                            item.specTVP.size, item.specTVP.color, item.specTVP.bold)

                    }

                    4 -> {
                        overrideTV(cseq.realSeq(it), binding.cellDialogFilteringName,
                            item.specTVN.size, item.specTVN.color, item.specTVN.bold)

                    }

                    6 -> {
                        overrideTV(cseq.realSeq(it), binding.cellDialogFilteringSname,
                            item.specTVSN.size, item.specTVSN.color, item.specTVSN.bold)

                    }

                    7 -> {
                        overrideTV(cseq.realSeq(it), binding.cellDialogFilteringCurrency,
                            item.specTVCur.size, item.specTVCur.color, item.specTVCur.bold)

                    }

                    8-> {
                        overrideTV(cseq.realSeq(it), binding.cellDialogFilteringScurrency,
                            item.specTVSCur.size, item.specTVSCur.color, item.specTVSCur.bold)

                    }

                    9 -> {
                        overrideTV(cseq.realSeq(it), binding.cellDialogFilteringCapital,
                            item.specTVCap.size, item.specTVCap.color, item.specTVCap.bold)

                    }

                }

            }

            binding.cellDialogFilterInfo.setOnClickListener {
                PopupWindow().normal(it, item.info)

            }

            binding.cellDialogFilterCb.setOnClickListener{
                item.isSelected = !item.isSelected

            }

            setOnClickListener {
                if(!multiSelection){
                    listener.onResult(item)

                }

            }

        }

        private fun overrideTV(seq : CountrySequence, tv : TextView, size : Float,
                               color : Int, bold : Boolean){
            if(tv.isVisible){
                if(size > -1){
                    tv.textSize = size

                }else{
                    tv.textSize = seq.tSize

                }

                if(color != -1){
                    tv.setTextColor(color)

                }else{
                    tv.setTextColor(seq.tColor)

                }

                if(bold){
                    tv.typeface = Typeface.DEFAULT_BOLD

                }else{
                    tv.typeface = if(seq.tBold) Typeface.DEFAULT_BOLD else Typeface.DEFAULT

                }

            }

        }

    }

    override fun getFilter(): Filter {
        return customFilter

    }

    private val customFilter = object : Filter() {

        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filteredList = mutableListOf<Country>()

            if (constraint == null || constraint.isEmpty()) {
                filteredList.addAll(modelsFull)
            } else {
                var temp = ""
                for (item in modelsFull) {
                    when(filterType){
                        "name" -> temp = item.name
                        "sname" -> temp = item.sname
                        "prefix" -> temp = item.prefix
                        "currency" -> temp = item.currency
                        "scurreny" -> temp = item.scurrency
                        "capital" -> temp = item.capital
                        else -> {
                            if (item.name.contains(constraint.toString(), true) ||
                                item.sname.contains(constraint.toString(), true) ||
                                item.prefix.contains(constraint.toString(), true) ||
                                item.currency.contains(constraint.toString(), true) ||
                                item.scurrency.contains(constraint.toString(), true) ||
                                item.capital.contains(constraint.toString(), true)) {

                                filteredList.add(item)

                            }

                        }

                    }

                    if (temp.contains(constraint.toString(), true)) {
                        filteredList.add(item)

                    }

                }

            }
            val results = FilterResults()
            results.values = filteredList
            return results

        }

        override fun publishResults(constraint: CharSequence?, filterResults: FilterResults?) {
            submitList(filterResults?.values as MutableList<Country>)

        }

    }

}

class adapter_CountryRecycler_DiffCallback : DiffUtil.ItemCallback<Country>() {

    override fun areItemsTheSame(oldItem: Country, newItem: Country): Boolean {
        return oldItem?.id == newItem?.id
    }

    override fun areContentsTheSame(oldItem: Country, newItem: Country): Boolean {
        return oldItem == newItem
    }

}