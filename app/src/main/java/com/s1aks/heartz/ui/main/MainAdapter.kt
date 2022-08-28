package com.s1aks.heartz.ui.main

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.s1aks.heartz.R
import com.s1aks.heartz.data.HeartData
import com.s1aks.heartz.databinding.FragmentMainDateItemBinding
import com.s1aks.heartz.databinding.FragmentMainItemBinding
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.concurrent.TimeUnit


class MainAdapter(
    private val resContext: Context,
    private val itemClickListener: OnItemClickListener
) : ListAdapter<HeartData, RecyclerView.ViewHolder>(ItemCallback) {

    private lateinit var dateBinding: FragmentMainDateItemBinding
    private lateinit var itemBinding: FragmentMainItemBinding

    @RequiresApi(Build.VERSION_CODES.O)
    fun setData(list: List<HeartData>) {
        val resultList = LinkedList<HeartData>()
        list.forEachIndexed { index: Int, heartData: HeartData ->
            if (index == 0 ||
                heartData.time?.let { TimeUnit.MILLISECONDS.toDays(it.toLong()) } !=
                list[index - 1].time?.let { TimeUnit.MILLISECONDS.toDays(it.toLong()) }
            ) {
                val header = heartData.copy()
                header.type = HeartData.Type.DATE
                resultList.add(header)
            }
            heartData.type = HeartData.Type.INFO
            resultList.add(heartData)
        }
        submitList(resultList)
    }

    override fun getItemViewType(position: Int): Int =
        when (currentList[position].type) {
            HeartData.Type.DATE -> 0
            else -> 1
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            0 -> {
                dateBinding = FragmentMainDateItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                DateViewHolder(dateBinding)
            }
            else -> {
                itemBinding = FragmentMainItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                ItemViewHolder(itemBinding)
            }
        }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (currentList[position].type) {
            HeartData.Type.DATE -> (holder as DateViewHolder).bind(position)
            else -> (holder as ItemViewHolder).bind(position)
        }
    }

    inner class DateViewHolder(private val binding: FragmentMainDateItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(position: Int) = with(binding) {
            currentList[position].let {
                date.text = it.time?.millisGetDate
            }
        }
    }

    inner class ItemViewHolder(private val binding: FragmentMainItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(position: Int) = with(binding) {
            currentList[position].let {
                time.text = it.time?.millisGetTime
                topPressure.text = it.topPressure.toString()
                lowPressure.text = it.lowPressure.toString()
                pulse.text = it.pulse.toString()
                binding.root.setBackgroundGradientFromHeartData(it)
                itemView.setOnClickListener { itemClickListener.onItemClick(position) }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setGradient(centerColor: Int): GradientDrawable =
        GradientDrawable(
            GradientDrawable.Orientation.LEFT_RIGHT,
            intArrayOf(
                resContext.getColor(R.color.white),
                resContext.getColor(centerColor),
                resContext.getColor(R.color.white)
            )
        )

    @RequiresApi(Build.VERSION_CODES.M)
    private fun View.setBackgroundGradientFromHeartData(data: HeartData) = with(data) {
        background = when {
            topPressure!! < 100 || topPressure > 144 || lowPressure!! < 60 || lowPressure > 99 ||
                    pulse!! < 45 || pulse > 99 -> setGradient(R.color.item_back_grad_5)
            topPressure in (100..104) || topPressure in (140..144) ||
                    lowPressure in (60..64) || lowPressure in (95..99) ||
                    pulse in (45..49) || pulse in (95..99) -> setGradient(R.color.item_back_grad_4)
            topPressure in (105..109) || topPressure in (135..139) ||
                    lowPressure in (65..69) || lowPressure in (90..94) ||
                    pulse in (50..54) || pulse in (90..94) -> setGradient(R.color.item_back_grad_3)
            topPressure in (110..114) || topPressure in (130..134) ||
                    lowPressure in (70..74) || lowPressure in (85..89) ||
                    pulse in (55..59) || pulse in (80..89) -> setGradient(R.color.item_back_grad_2)
            else -> setGradient(R.color.item_back_grad_1)
        }
    }

    companion object {
        const val DATE_DAY_FORMAT = "dd MMMM"
        const val DATE_TIME_FORMAT = "HH:mm"

        object ItemCallback : DiffUtil.ItemCallback<HeartData>() {
            override fun areItemsTheSame(oldItem: HeartData, newItem: HeartData): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: HeartData, newItem: HeartData): Boolean =
                oldItem == newItem
        }
    }
}

val String.millisGetDate: String
    @RequiresApi(Build.VERSION_CODES.O)
    get() = LocalDateTime.ofInstant(Instant.ofEpochMilli(this.toLong()), ZoneId.systemDefault())
        .format(DateTimeFormatter.ofPattern(MainAdapter.DATE_DAY_FORMAT))

val String.millisGetTime: String
    @RequiresApi(Build.VERSION_CODES.O)
    get() = LocalDateTime.ofInstant(Instant.ofEpochMilli(this.toLong()), ZoneId.systemDefault())
        .format(DateTimeFormatter.ofPattern(MainAdapter.DATE_TIME_FORMAT))


interface OnItemClickListener {
    fun onItemClick(position: Int)
}
