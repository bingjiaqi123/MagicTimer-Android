package com.example.magictimer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.example.magictimer.databinding.FragmentAddBinding
import com.example.magictimer.databinding.FragmentContextBinding
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class FragmentContext : Fragment(), AdapterView.OnItemSelectedListener {
    private lateinit var binding: FragmentContextBinding
    private lateinit var textViewContextValue: TextView
    private lateinit var spinnerStatus: Spinner
    private lateinit var spinnerLocation: Spinner
    private lateinit var spinnerMode: Spinner

    private val statusMap = mapOf(
        "校内" to 0,
        "校外" to 4,
        "通勤" to 5,
        "家里" to 6,
        "上铺" to 7,
        "运动" to 8
    )

    private val locationMap = mapOf(
        "图书馆" to 1,
        "教室/会议室" to 2,
        "宿舍" to 3
    )

    private val modeMap = mapOf(
        "室内" to 0,
        "户外" to 1
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContextBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnContextReturn.setOnClickListener {
            NavHostFragment.findNavController(this)
                .navigate(R.id.action_ContextFragment_to_SettingsFragment)
        }

        binding.btnContextHome.setOnClickListener {
            NavHostFragment.findNavController(this)
                .navigate(R.id.action_ContextFragment_to_FirstFragment)
        }

        textViewContextValue = binding.textViewContextValue

        spinnerStatus = binding.spinnerStatus
        spinnerLocation = binding.spinnerLocation
        spinnerMode = binding.spinnerMode

        spinnerStatus.onItemSelectedListener = this
        spinnerLocation.onItemSelectedListener = this
        spinnerMode.onItemSelectedListener = this

        val statusArray = resources.getStringArray(R.array.status_array)
        resources.getStringArray(R.array.location_array)
        val modeArray = resources.getStringArray(R.array.mode_array)

        ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            statusArray
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerStatus.adapter = adapter
        }

        ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            modeArray
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerMode.adapter = adapter
        }

        spinnerStatus.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedStatus = parent.getItemAtPosition(position).toString()
                updateLocationVisibility(selectedStatus)
                updateContextValue()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

// 在应用程序启动时复制默认的magicsettings.txt文件
        val fileDir = requireContext().getExternalFilesDir(null)
        val magicSettingsFile = File(fileDir, "magicsettings.txt")
        if (!magicSettingsFile.exists()) {
            try {
                val inputStream = requireContext().assets.open("magicsettings.txt")
                val outputStream = FileOutputStream(magicSettingsFile)
                inputStream.copyTo(outputStream)
                inputStream.close()
                outputStream.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
// 在应用程序启动时复制默认的form_rule.txt文件
        val formRuleFile = File(fileDir, "form_rule.txt")
        if (!formRuleFile.exists()) {
            try {
                val inputStream = requireContext().assets.open("form_rule.txt")
                val outputStream = FileOutputStream(formRuleFile)
                inputStream.copyTo(outputStream)
                inputStream.close()
                outputStream.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        binding.btnContextConfirm.setOnClickListener {
            readMagicSettingsAndFormData()
        }
    }

    private fun updateLocationVisibility(selectedStatus: String) {
        if (selectedStatus == "校内") {
            spinnerLocation.visibility = View.VISIBLE
        } else {
            spinnerLocation.visibility = View.GONE
        }

        if (selectedStatus == "运动") {
            spinnerMode.visibility = View.VISIBLE
        } else {
            spinnerMode.visibility = View.GONE
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        updateContextValue()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        // Do nothing
    }

    private fun updateContextValue() {
        val selectedStatus = spinnerStatus.selectedItem.toString()
        val selectedLocation = spinnerLocation.selectedItem.toString()
        val selectedMode = spinnerMode.selectedItem.toString()

        var contextValue = 0

        if (statusMap.containsKey(selectedStatus)) {
            contextValue += statusMap[selectedStatus] ?: 0

            // 只在状态为“校内”时考虑位置的值
            if (selectedStatus == "校内" && locationMap.containsKey(selectedLocation)) {
                contextValue += locationMap[selectedLocation] ?: 0
            }

            // 只在状态为“运动”时考虑模式的值
            if (selectedStatus == "运动" && modeMap.containsKey(selectedMode)) {
                contextValue += modeMap[selectedMode] ?: 0
            }
        }

        textViewContextValue.text = "情境值: $contextValue"

        // 将当前情境值写入文件
        try {
            val fileDir = requireContext().getExternalFilesDir(null)
            val file = File(fileDir, "magicsettings.txt")
            val contextLine = "%Z $contextValue"

            // 读取原始文件的内容
            val originalLines = file.readText().split("\n").toMutableList()

            // 找到以 %Z 开头的行的索引
            val index = originalLines.indexOfFirst { it.startsWith("%Z") }

            if (index != -1) {
                // 替换以 %Z 开头的行
                originalLines[index] = contextLine

                // 将更新后的内容写入文件
                file.writeText(originalLines.joinToString("\n"))

                Toast.makeText(requireContext(), "已更新", Toast.LENGTH_SHORT).show()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun readMagicSettingsAndFormData() {
        val magicSettingsFile = File(requireContext().getExternalFilesDir(null), "magicsettings.txt")
        val formDataFile = File(requireContext().getExternalFilesDir(null), "form_data.txt")
        val formRuleFile = File(requireContext().getExternalFilesDir(null), "form_rule.txt")

        if (magicSettingsFile.exists() && formDataFile.exists()) {
            val magicSettingsContent = magicSettingsFile.readText()
            val formDataContent = formDataFile.readText()

            val zLine = magicSettingsContent.lines().find { it.startsWith("%Z") }
            if (zLine != null && (zLine.contains("1") || zLine.contains("2") || zLine.contains("3") || zLine.contains("4") || zLine.contains("5") || zLine.contains("6") || zLine.contains("7") || zLine.contains("8") || zLine.contains("9"))) {
                val tasks = extractTasks(formDataContent)

                formRuleFile.writeText("") // Clear the existing content of form_rule.txt

                formRuleFile.bufferedWriter().use { writer ->
                    for (task in tasks) {
                        if (shouldSkipTask(task, zLine)) continue
                        writer.write(task)
                        writer.newLine()
                    }
                }
            }
        }
    }

    private fun shouldSkipTask(task: String, zLine: String): Boolean {
        val taskZLine = task.lines().find { it.startsWith("%Z") }
        return when {
            zLine.contains("1") && taskZLine != null && taskZLine.contains("cdefgh") -> true
            zLine.contains("2") && taskZLine != null && taskZLine.contains("cdefgh") -> true
            zLine.contains("3") && (taskZLine == null || !(taskZLine.contains("a") && taskZLine.contains("e"))) -> true
            zLine.contains("4") && (taskZLine == null || !(taskZLine.contains("a") && taskZLine.contains("efg"))) -> true
            zLine.contains("5") && taskZLine != null && (taskZLine.contains("a") || taskZLine.contains("d")) -> true
            zLine.contains("6") && (taskZLine == null || !(taskZLine.contains("a") && taskZLine.contains("e"))) -> true
            zLine.contains("7") && (taskZLine == null || !(taskZLine.contains("a") && taskZLine.contains("e"))) -> true
            zLine.contains("8") && taskZLine != null && taskZLine.contains("F") -> true
            zLine.contains("9") && taskZLine != null && taskZLine.contains("E") -> true
            else -> false
        }
    }

    private fun extractTasks(formDataContent: String): List<String> {
        val pattern = Regex("%[01].*?\\n(?:%[^01].*?\\n)*") // 匹配%0行和%1行开始的任务
        return pattern.findAll(formDataContent).map { it.value }.toList()
    }
}