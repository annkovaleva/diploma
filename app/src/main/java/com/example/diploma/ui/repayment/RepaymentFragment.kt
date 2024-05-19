package com.example.diploma.ui.repayment

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.diploma.databinding.FragmentRepaymentBinding
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter

class RepaymentFragment : Fragment() {

    private var _binding: FragmentRepaymentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val repaymentViewModel = ViewModelProvider(this)[RepaymentViewModel::class.java]

        _binding = FragmentRepaymentBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val spinner: Spinner = binding.Spinner
        val spinnerItems = arrayOf(
            "Все сотрудники",
            "Александра (top barber)",
            "Аят (top barber)",
            "Марина (pro barber)",
            "Муза (pro barber)",
            "Олег (pro barber)",
            "Шер (individual)"
        )
        val adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, spinnerItems)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        spinner.setSelection(0)

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                view?.let { updateCharts(position) }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        setupCharts()

        return root
    }

    private fun setupCharts() {
        updateCharts(0)
    }

    private fun updateCharts(position: Int) {

        // Создаем список данных для графика
        val data = when (position) {
            1 -> dataAlex
            2 -> dataAyat
            3 -> dataMarina
            4 -> dataMuza
            5 -> dataOleg
            6 -> dataSher
            else -> dataAllEmployees
        }

        val entries1_1 = data[0].mapIndexed { index, value -> BarEntry(index.toFloat(), value) }
        val entries1_2 = data[1].mapIndexed { index, value -> BarEntry(index.toFloat(), value) }
        val entries2 = data[2].mapIndexed { index, value -> BarEntry(index.toFloat(), value) }
        val entries3_1 = data[3].mapIndexed { index, value -> Entry(index.toFloat(), value) }
        val entries3_2 = data[4].mapIndexed { index, value -> Entry(index.toFloat(), value) }

        val dataSet1_1 = BarDataSet(entries1_1, "Возвратность")
        val dataSet1_2 = BarDataSet(entries1_2, "Уровень оттока")
        val dataSet2 = BarDataSet(entries2, "Записи")
        val dataSet3_1 = LineDataSet(entries3_1, "Постоянные")
        val dataSet3_2 = LineDataSet(entries3_2, "Новые")

        dataSet1_1.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                return value.toInt().toString()
            }
        }

        dataSet1_2.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                return value.toInt().toString()
            }
        }

        dataSet2.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                return String.format("%.1f", value)
            }
        }

        dataSet3_1.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                return value.toInt().toString()
            }
        }

        dataSet3_2.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                return value.toInt().toString()
            }
        }

        // Утсанавливаем цвета для графиков
        dataSet1_1.color = Color.parseColor("#C8FEDA")
        dataSet1_2.color = Color.parseColor("#57C920")
        dataSet1_1.setGradientColor(Color.parseColor("#C8FEDA"), Color.parseColor("#57C920"))
        dataSet1_2.setGradientColor(Color.parseColor("#57C920"), Color.parseColor("#57C920"))
        dataSet2.color = Color.parseColor("#946DDE")
        dataSet3_1.color = Color.parseColor("#FF7B12")
        dataSet3_2.color = Color.parseColor("#2D54F3")

        // Устанавливаем размер текста и ширину графиков
        dataSet1_1.valueTextSize = 12f
        dataSet1_2.valueTextSize = 12f
        dataSet2.valueTextSize = 12f
        dataSet3_1.valueTextSize = 12f
        dataSet3_2.valueTextSize = 12f

        dataSet3_1.lineWidth = 4f
        dataSet3_2.lineWidth = 4f

        // Создаем массивы данных для каждого графика
        val data1 = BarData(dataSet1_1, dataSet1_2)
        val data2 = BarData(dataSet2)
        val data3 = LineData(dataSet3_1, dataSet3_2)

        data1.barWidth = 0.8f
        data2.barWidth = 0.8f



        // ------ Настройка первого графика ------
        binding.bcRepayment.data = data1
        binding.bcRepayment.setVisibleXRange(0f, 6f)
        val xAxis1 = binding.bcRepayment.xAxis
        xAxis1.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                return if (value.toInt() >= 0 && value.toInt() < months.size) {
                    months[value.toInt()]
                } else {
                    ""
                }
            }
        }

        // Получаем объект легенды для графика
        val legend1 = binding.bcRepayment.legend

        // Устанавливаем размер текста легенды
        legend1.textSize = 14f

        // Устанавливаем описание для графика
        binding.bcRepayment.description.isEnabled = false

        binding.bcRepayment.invalidate()



        // ------ Настройка второго графика ------
        binding.bcOperationalRecords.data = data2
        binding.bcOperationalRecords.setVisibleXRange(0f, 6f)
        val xAxis2 = binding.bcOperationalRecords.xAxis
        xAxis2.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                return if (value.toInt() >= 0 && value.toInt() < months.size) {
                    months[value.toInt()]
                } else {
                    ""
                }
            }
        }

        // Получаем объект легенды для графика
        val legend2 = binding.bcOperationalRecords.legend

        // Устанавливаем размер текста легенды
        legend2.textSize = 14f

        // Устанавливаем описание для графика
        binding.bcOperationalRecords.description.isEnabled = false

        binding.bcOperationalRecords.invalidate()



        // ------ Настройка третьего графика ------
        binding.lcCustomerFlow.data = data3
        binding.lcCustomerFlow.setVisibleXRange(0f, 6f)
        val xAxis = binding.lcCustomerFlow.xAxis
        xAxis.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                return if (value.toInt() >= 0 && value.toInt() < months.size) {
                    months[value.toInt()]
                } else {
                    ""
                }
            }
        }

        // Устанавливаем размер текста для оси X
        xAxis.textSize = 12f

        xAxis.position = XAxis.XAxisPosition.BOTH_SIDED

        val yAxisLeft = binding.lcCustomerFlow.axisLeft
        val yAxisRight = binding.lcCustomerFlow.axisRight

        // Устанавливаем размер текста для осей Y
        yAxisLeft.textSize = 12f
        yAxisRight.textSize = 12f

        xAxis.axisMinimum = -0.5f
        xAxis.axisMaximum = 11.5f

        // Получаем объект легенды для LineChart
        val legend = binding.lcCustomerFlow.legend
        legend.textSize = 14f

        // Устанавливаем описание для LineChart
        binding.lcCustomerFlow.description.isEnabled = false

        binding.lcCustomerFlow.invalidate()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        val months = arrayOf(
            "Июн",
            "Июл",
            "Авг",
            "Сен",
            "Окт",
            "Ноя",
            "Дек",
            "Янв",
            "Фев",
            "Мар",
            "Апр",
            "Май"
        )

        val dataAllEmployees = listOf(
            listOf(44f, 46f, 45f, 41f, 46f, 43f, 50f, 37f, 42f, 49f, 45f, 30f),
            listOf(20f, 21f, 19f, 17f, 26f, 15f, 20f, 29f, 19f, 13f, 20f, 5f), // придумано
            listOf(
                0.2f,
                0.6f,
                0.3f,
                1.0f,
                0.9f,
                0.4f,
                0.4f,
                0.1f,
                0.5f,
                0.9f,
                0.3f,
                0.5f
            ), // придумано
            listOf(426f, 483f, 496f, 464f, 506f, 474f, 556f, 415f, 437f, 494f, 463f, 301f),
            listOf(76f, 128f, 132f, 118f, 116f, 92f, 128f, 70f, 85f, 100f, 66f, 49f)
        )

        val dataAlex = listOf(
            listOf(31f, 37f, 36f, 33f, 42f, 36f, 40f, 28f, 34f, 41f, 33f, 20f),
            listOf(15f, 18f, 17f, 16f, 25f, 14f, 15f, 24f, 16f, 12f, 17f, 4f), // придумано
            listOf(
                0.1f,
                0.4f,
                0.2f,
                0.8f,
                0.7f,
                0.3f,
                0.3f,
                0.05f,
                0.4f,
                0.8f,
                0.2f,
                0.4f
            ), // придумано
            listOf(44f, 56f, 55f, 50f, 56f, 57f, 59f, 40f, 45f, 60f, 52f, 31f),
            listOf(17f, 18f, 17f, 17f, 22f, 22f, 17f, 13f, 31f, 20f, 19f, 3f)
        )

        val dataAyat = listOf(
            listOf(37f, 45f, 44f, 37f, 41f, 43f, 44f, 40f, 42f, 46f, 39f, 33f),
            listOf(18f, 20f, 19f, 18f, 24f, 17f, 18f, 25f, 18f, 15f, 19f, 6f), // придумано
            listOf(
                0.15f,
                0.5f,
                0.25f,
                0.9f,
                0.8f,
                0.35f,
                0.35f,
                0.08f,
                0.45f,
                0.85f,
                0.25f,
                0.45f
            ), // придумано
            listOf(88f, 101f, 96f, 88f, 108f, 100f, 104f, 98f, 97f, 100f, 92f, 63f),
            listOf(15f, 22f, 33f, 27f, 29f, 21f, 29f, 21f, 21f, 24f, 16f, 9f)
        )

        val dataMarina = listOf(
            listOf(0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 19f, 18f, 17f),
            listOf(0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 14f, 18f, 5f), // придумано
            listOf(0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0.8f, 0.2f, 0.4f), // придумано
            listOf(0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 11f, 24f, 21f),
            listOf(0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 59f, 23f, 26f)
        )

        val dataMuza = listOf(
            listOf(0f, 0f, 0f, 0f, 0f, 0f, 0f, 12f, 19f, 20f, 20f, 12f),
            listOf(0f, 0f, 0f, 0f, 0f, 0f, 0f, 20f, 15f, 13f, 16f, 4f), // придумано
            listOf(0f, 0f, 0f, 0f, 0f, 0f, 0f, 0.06f, 0.4f, 0.75f, 0.18f, 0.38f), // придумано
            listOf(0f, 0f, 0f, 0f, 0f, 0f, 0f, 10f, 31f, 42f, 4f, 27f),
            listOf(0f, 0f, 0f, 0f, 0f, 0f, 82f, 77f, 65f, 81f, 48f, 24f)
        )

        val dataOleg = listOf(
            listOf(0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 9f),
            listOf(0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 2f), // придумано
            listOf(0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0.5f), // придумано
            listOf(0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 5f),
            listOf(0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 55f, 27f)
        )

        val dataSher = listOf(
            listOf(53f, 55f, 54f, 48f, 50f, 51f, 55f, 43f, 51f, 58f, 50f, 35f),
            listOf(9f, 12f, 11f, 16f, 12f, 15f, 16f, 21f, 6f, 13f, 7f, 5f), // придумано
            listOf(
                0.11f,
                0.38f,
                0.21f,
                0.8f,
                0.72f,
                0.31f,
                0.31f,
                0.065f,
                0.41f,
                0.78f,
                0.19f,
                0.39f
            ),  // придумано
            listOf(144f, 168f, 181f, 171f, 178f, 168f, 196f, 148f, 163f, 187f, 164f, 117f),
            listOf(29f, 24f, 41f, 30f, 29f, 26f, 24f, 16f, 14f, 21f, 10f, 8f)
        )
    }
}
