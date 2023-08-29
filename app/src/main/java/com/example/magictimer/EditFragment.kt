package com.example.magictimer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.example.magictimer.databinding.FragmentEditBinding
import java.io.*

class EditFragment : Fragment() {
    private var _binding: FragmentEditBinding? = null
    private val binding get() = _binding!!

    private val contextOptions = mapOf(
        R.id.checkBoxCampusNetwork to 'A',
        R.id.checkBoxDisconnectable to 'B',
        R.id.checkBoxLoud to 'C',
        R.id.checkBoxCommute to 'D',
        R.id.checkBoxoutExercise to 'E',
        R.id.checkBoxinExercise to 'F',
        R.id.checkBoxSolitude to 'G',
        R.id.checkBoxEntertainment to 'H'
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 在这里调用 setupCheckboxListeners 函数
        setupCheckboxListeners(view)

        binding.btnAddReturn.setOnClickListener {
            NavHostFragment.findNavController(this)
                .navigate(R.id.action_EditFragment_to_FirstFragment)
        }

        val currentFile = File(requireContext().getExternalFilesDir(null), "current.txt")
        try {
            val formData = readFormData(currentFile)
            binding.editTextTaskName.setText(formData.taskName)
            binding.editTextTaskDetails.setText(formData.taskDetails)
            updateDurationSpinner(formData.selectedDuration)
            updateExecutionsSpinner(formData.selectedExecutions)
            updateCheckboxes(formData.selectedOptions)
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(requireContext(), "读取文件失败", Toast.LENGTH_SHORT).show()
        }

        val formDataFile = File(requireContext().getExternalFilesDir(null), "form_data.txt")
        val formRuleFile = File(requireContext().getExternalFilesDir(null), "form_rule.txt")

            binding.btnDelete.setOnClickListener {
                // 清空表单数据
                clearFormData()

            try {
                val currentText = currentFile.readText()
                val formDataText = formDataFile.readText()
                val formRuleText = formRuleFile.readText()
                val updatedFormDataText = formDataText.replace(currentText, "")
                formDataFile.writeText(updatedFormDataText)
                val updatedFormRuleText = formRuleText.replace(currentText, "")
                formRuleFile.writeText(updatedFormRuleText)
                Toast.makeText(requireContext(), "删除成功", Toast.LENGTH_SHORT).show()
            } catch (e: IOException) {
                e.printStackTrace()
                Toast.makeText(requireContext(), "删除失败", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnUpdate.setOnClickListener {
            val currentText = currentFile.readText()
            val formDataText = formDataFile.readText()
            val formRuleText = formRuleFile.readText()
            val updatedFormDataText = formDataText.replace(currentText, "")
            formDataFile.writeText(updatedFormDataText)
            val updatedFormRuleText = formRuleText.replace(currentText, "")
            formRuleFile.writeText(updatedFormRuleText)

            // 获取表单数据
            val taskName = binding.editTextTaskName.text.toString()
            val taskDetails = binding.editTextTaskDetails.text.toString()
            val spinnerDuration = binding.spinnerDuration
            val spinnerExecutions = binding.spinnerExecutions

            // 获取选中的任务时长和可执行次数
            val selectedDuration = spinnerDuration.selectedItem.toString()
            val selectedExecutions = spinnerExecutions.selectedItem.toString()

            // 获取选中与未选中的使用情境
            val selectedStatus = getSelectedContexts(view)

            // 写入文件
            try {
                // 更新current.txt文件
                val fileData = File(requireContext().getExternalFilesDir(null), "current.txt")
                val fosData = FileOutputStream(fileData)
                val writerData = BufferedWriter(OutputStreamWriter(fosData))

                writerData.write("%0 Task\n")
                writerData.write("%A $taskName\n")
                writerData.write("%F ${getCurrentDateTime()}\n") // 添加当前日期时间行
                writerData.write("%B $taskDetails\n")
                writerData.write("%C $selectedDuration\n")
                writerData.write("%D $selectedExecutions\n")
                writerData.write("%Z ${
                    selectedStatus.joinToString("") {
                        if (it.isSelected) it.letter.toString() else it.letter.lowercase().toString()
                    }
                }")
                writerData.write("%1\n")
                writerData.newLine()
                writerData.close()

                // 将current.txt的内容追加到form_data.txt
                val formDataText = fileData.readText()
                formDataFile.appendText(formDataText)

                // 将current.txt的内容追加到form_rule.txt
                formRuleFile.appendText(formDataText)

                Toast.makeText(requireContext(), "更新成功", Toast.LENGTH_SHORT).show()

                // 清空表单数据
                clearFormData()

            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun readFormData(file: File): FormData {
        val formData = FormData()
        BufferedReader(FileReader(file)).use { reader ->
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                when {
                    line!!.startsWith("%A") -> formData.taskName = line!!.substringAfter("%A ")
                    line!!.startsWith("%B") -> formData.taskDetails = line!!.substringAfter("%B ")
                    line!!.startsWith("%C") -> formData.selectedDuration = line!!.substringAfter("%C ")
                    line!!.startsWith("%D") -> formData.selectedExecutions = line!!.substringAfter("%D ")
                    line!!.startsWith("%Z") -> formData.selectedOptions = line!!.substringAfter("%Z ")
                }
            }
        }
        return formData
    }

    private fun updateDurationSpinner(selectedDuration: String) {
        val durationArray = resources.getStringArray(R.array.duration_array)
        val spinner: Spinner = binding.spinnerDuration
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, durationArray)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.setSelection(getDurationIndex(selectedDuration))
    }

    private fun getDurationIndex(duration: String): Int {
        val durationArray = resources.getStringArray(R.array.duration_array)
        return durationArray.indexOf(duration)
    }

    private fun updateExecutionsSpinner(selectedExecutions: String) {
        val executionsArray = resources.getStringArray(R.array.executions_array)
        val spinner: Spinner = binding.spinnerExecutions
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, executionsArray)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.setSelection(getExecutionsIndex(selectedExecutions))
    }

    private fun getExecutionsIndex(executions: String): Int {
        val executionsArray = resources.getStringArray(R.array.executions_array)
        return executionsArray.indexOf(executions)
    }

    private fun updateCheckboxes(selectedOptions: String) {
        for ((checkboxId, optionChar) in contextOptions) {
            val checkbox: CheckBox = view?.findViewById(checkboxId) ?: continue
            checkbox.isChecked = selectedOptions.contains(optionChar.toString())
        }
    }

    private fun getSelectedContexts(view: View): List<ContextOption> {
        val selectedContexts = mutableListOf<ContextOption>()
        for ((checkBoxId, optionChar) in contextOptions) {
            val checkBox = view.findViewById<CheckBox>(checkBoxId)
            if (checkBox.isChecked) {
                selectedContexts.add(ContextOption(optionChar, true))
            } else {
                selectedContexts.add(ContextOption(optionChar, false))
            }
        }
        return selectedContexts
    }

    data class FormData(
        var taskName: String = "",
        var taskDetails: String = "",
        var selectedDuration: String = "",
        var selectedExecutions: String = "",
        var selectedOptions: String = ""
    )
    private fun clearFormData() {
        binding.editTextTaskName.text.clear()
        binding.editTextTaskDetails.text.clear()
        binding.spinnerDuration.setSelection(0)
        binding.spinnerExecutions.setSelection(0)

        // 取消选择所有使用情境
        for ((checkBoxId, _) in contextOptions) {
            val checkBox = view?.findViewById<CheckBox>(checkBoxId)
            if (checkBox != null) {
                checkBox.isChecked = false
            }
        }
    }
    data class ContextOption(val letter: Char, val isSelected: Boolean)
}
