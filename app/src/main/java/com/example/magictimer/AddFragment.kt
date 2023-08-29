package com.example.magictimer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.example.magictimer.databinding.FragmentAddBinding
import java.io.*
import java.text.SimpleDateFormat
import java.util.*

class AddFragment : Fragment() {

    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!

    // 定义选中状态与字母的映射关系
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
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnAddReturn.setOnClickListener {
            NavHostFragment.findNavController(this).navigate(R.id.action_AddFragment_to_FirstFragment)
        }

        val checkBoxCampusNetwork = view.findViewById<CheckBox>(R.id.checkBoxCampusNetwork)
        val checkBoxDisconnectable = view.findViewById<CheckBox>(R.id.checkBoxDisconnectable)
        val checkBoxCommute = view.findViewById<CheckBox>(R.id.checkBoxCommute)

        // 设置勾选状态变化的监听器
        checkBoxCampusNetwork.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                checkBoxDisconnectable.isChecked = false
                checkBoxCommute.isChecked = false
            }
        }

        checkBoxDisconnectable.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                checkBoxCampusNetwork.isChecked = false
            }
        }

        checkBoxCommute.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                checkBoxCampusNetwork.isChecked = false
            }
        }

        binding.btnAddTask.setOnClickListener {
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
                val fileData = getFile("form_data.txt") // 获取对应的文件
                val fosData = FileOutputStream(fileData, true)
                val writerData = BufferedWriter(OutputStreamWriter(fosData))

                // 写入formdata.txt
                writerData.write("%0 Task\n")
                writerData.write("%A $taskName\n")
                writerData.write("%F ${getCurrentDateTime()}\n") // 添加当前日期时间行
                writerData.write("%B $taskDetails\n")
                writerData.write("%C $selectedDuration\n")
                writerData.write("%D $selectedExecutions\n")
                writerData.write("%Z ${
                    selectedStatus.joinToString("") {
                        if (it.isSelected) it.letter else it.letter.lowercase()
                    }
                }")
                writerData.write("%1\n")
                writerData.newLine()

                writerData.close()
                Toast.makeText(requireContext(), "表单数据已保存", Toast.LENGTH_SHORT).show()

                // 更新formrule.txt
                val fileRule = getFile("form_rule.txt")
                val fosRule = FileOutputStream(fileRule, false)
                val writerRule = BufferedWriter(OutputStreamWriter(fosRule))

                // 读取formdata.txt的内容
                val fisData = FileInputStream(fileData)
                val readerData = BufferedReader(InputStreamReader(fisData))

                while (true) {
                    val line = readerData.readLine() ?: break
                    writerRule.write(line)
                    writerRule.newLine()
                }

                readerData.close()
                writerRule.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getFile(fileName: String): File? {
        val externalFilesDir = requireContext().getExternalFilesDir(null)
        return if (externalFilesDir != null) {
            File(externalFilesDir, fileName)
        } else {
            null
        }
    }

    // 获取选中与未选中的使用情境
    private fun getSelectedContexts(view: View): List<ContextOption> {
        val selectedContexts = mutableListOf<ContextOption>()
        for ((checkBoxId, letter) in contextOptions) {
            val checkBox = view.findViewById<CheckBox>(checkBoxId)
            if (checkBox.isChecked) {
                selectedContexts.add(ContextOption(letter.toString(), true))
            } else {
                selectedContexts.add(ContextOption(letter.toString(), false))
            }
        }
        return selectedContexts
    }

    // 获取当前日期时间字符串
    private fun getCurrentDateTime(): String {
        val dateFormat = SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault())
        val currentDate = Date()
        return dateFormat.format(currentDate)
    }
}

data class ContextOption(val letter: String, val isSelected: Boolean)
