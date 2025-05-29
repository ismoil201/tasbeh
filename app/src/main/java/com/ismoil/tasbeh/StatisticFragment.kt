package com.ismoil.tasbeh

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.animation.Easing
import com.ismoil.tasbeh.databinding.FragmentStatisticBinding
import com.ismoil.tasbeh.room.AppDataBase
import java.text.SimpleDateFormat
import java.util.*

class StatisticFragment : Fragment() {

    private var _binding: FragmentStatisticBinding? = null
    private val binding get() = _binding!!

    private lateinit var database: AppDataBase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStatisticBinding.inflate(inflater, container, false)
        database = AppDataBase.getInstance(requireContext())

        setupChart()
        setupStatistics()

        return binding.root
    }

    private fun setupChart() {
        val zikrData = database.zikrDao().getLast10ZikrCounts()

        val sdfInput = SimpleDateFormat("MM/dd HH:mm", Locale.getDefault())
        val sdfOutput = SimpleDateFormat("MM/dd", Locale.getDefault())

        // Sana bo‘yicha guruhlash
        val groupedMap = mutableMapOf<String, Int>()

        for (zikr in zikrData) {
            try {
                val parsedDate = sdfInput.parse(zikr.date)
                val onlyDate = sdfOutput.format(parsedDate!!)
                groupedMap[onlyDate] = groupedMap.getOrDefault(onlyDate, 0) + zikr.totalCount
            } catch (e: Exception) {
                // Noto‘g‘ri formatda bo‘lsa o‘tkazib yuboramiz
            }
        }

        val sortedDates = groupedMap.keys.sorted()
        val barEntries = ArrayList<BarEntry>()
        val labels = ArrayList<String>()

        for ((index, date) in sortedDates.withIndex()) {
            val totalCount = groupedMap[date] ?: 0
            barEntries.add(BarEntry(index.toFloat(), totalCount.toFloat()))
            labels.add(date)
        }

        val barDataSet = BarDataSet(barEntries, "Kunlik Zikrlar").apply {
            color = Color.parseColor("#E2BE7F")
            valueTextColor = Color.BLACK
            valueTextSize = 14f
        }

        val combinedData = CombinedData().apply {
            setData(BarData(barDataSet))
        }

        binding.chart.apply {
            data = combinedData
            description.isEnabled = false
            setDrawGridBackground(false)
            isHighlightPerTapEnabled = true

            xAxis.apply {
                position = XAxis.XAxisPosition.BOTTOM
                valueFormatter = IndexAxisValueFormatter(labels)
                granularity = 1f
                textColor = Color.DKGRAY
                textSize = 12f
                setDrawGridLines(false)
            }

            axisLeft.textColor = Color.DKGRAY
            axisRight.isEnabled = false
            legend.textColor = Color.BLACK
            animateY(1000, Easing.EaseInOutQuad)
            invalidate()
        }
    }

    private fun setupStatistics() {
        val allZikrs = database.zikrDao().getZikrs()

        val sdfInput = SimpleDateFormat("MM/dd HH:mm", Locale.getDefault())
        val sdfCompare = SimpleDateFormat("MM/dd", Locale.getDefault())

        val today = getCurrentDate("MM/dd")
        val weekAgo = getDateDaysAgo(7, "MM/dd")
        val monthAgo = getDateDaysAgo(30, "MM/dd")

        val processedZikrs = allZikrs.mapNotNull { zikr ->
            try {
                val parsedDate = sdfInput.parse(zikr.date)
                val dateOnly = sdfCompare.format(parsedDate!!)
                zikr.copy(date = dateOnly)
            } catch (e: Exception) {
                null
            }
        }

        val total = processedZikrs.sumOf { it.currentCount }
        val monthly = processedZikrs.filter { it.date >= monthAgo }.sumOf { it.currentCount }
        val weekly = processedZikrs.filter { it.date >= weekAgo }.sumOf { it.currentCount }
        val todayTotal = processedZikrs.filter { it.date == today }.sumOf { it.currentCount }

        binding.textTotalCount.text = "Umumiy: $total ta zikr"
        binding.textMonthCount.text = "Oylik: $monthly ta zikr"
        binding.textWeekCount.text = "Hafta: $weekly ta zikr"
        binding.textTodayCount.text = "Bugun: $todayTotal ta zikr"
    }

    private fun getCurrentDate(format: String): String {
        val sdf = SimpleDateFormat(format, Locale.getDefault())
        return sdf.format(Date())
    }

    private fun getDateDaysAgo(days: Int, format: String): String {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, -days)
        val sdf = SimpleDateFormat(format, Locale.getDefault())
        return sdf.format(calendar.time)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
