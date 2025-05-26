package com.ismoil.tasbeh

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.mikephil.charting.charts.CombinedChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.animation.Easing

class StatisticFragment : Fragment() {

    private lateinit var chart: CombinedChart

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_statistic, container, false)
        chart = view.findViewById(R.id.chart)
        setupChart()
        return view
    }

    private fun setupChart() {
        val barEntries = ArrayList<BarEntry>()
        val lineEntries = ArrayList<Entry>()
        val labels = ArrayList<String>()

        val sdf = java.text.SimpleDateFormat("MMM d", java.util.Locale.getDefault())
        val calendar = java.util.Calendar.getInstance()

        // Oxirgi 10 kunlik data (bugungi kundan orqaga qarab)
        calendar.add(java.util.Calendar.DAY_OF_YEAR, -9) // 10 kun oldinga borish

        for (i in 0 until 10) {
            val tasbehCount = (20..100).random()
            val dateLabel = sdf.format(calendar.time)

            barEntries.add(BarEntry(i.toFloat(), tasbehCount.toFloat()))
            lineEntries.add(Entry(i.toFloat(), tasbehCount.toFloat()))
            labels.add(dateLabel)

            calendar.add(java.util.Calendar.DAY_OF_YEAR, 1) // Keyingi kun
        }

        // Bar dataset
        val barDataSet = BarDataSet(barEntries, "Tasbeh Count").apply {
            color = Color.parseColor("#E2BE7F")
            valueTextColor = Color.BLACK
            valueTextSize = 14f
        }

        // Line dataset
//        val lineDataSet = LineDataSet(lineEntries, "Trend").apply {
//            color = Color.BLUE
//            setDrawCircles(true)
//            circleRadius = 4f
//            setCircleColor(Color.RED)
//            lineWidth = 2f
//            setDrawCircleHole(false)
//            valueTextSize = 10f
//            setDrawFilled(true)
//            fillAlpha = 100
//            fillColor = Color.BLUE
//            mode = LineDataSet.Mode.CUBIC_BEZIER
//        }

        // Combined data
        val combinedData = CombinedData().apply {
            setData(BarData(barDataSet))
//            setData(LineData(lineDataSet))
        }

        // Chart config
        chart.apply {
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
            animateY(1000, com.github.mikephil.charting.animation.Easing.EaseInOutQuad)
            invalidate()
        }
    }
}
