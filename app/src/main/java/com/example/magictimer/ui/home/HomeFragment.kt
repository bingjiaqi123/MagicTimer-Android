package com.example.magictimer.ui.home

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.magictimer.databinding.FragmentHomeBinding
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        moveFilesFromAssets()

        binding.btnFTY.setOnClickListener {
            handleButtonAction("50分钟")
            loadCurrentTask()
        }
        binding.btnTTY.setOnClickListener {
            handleButtonAction("30分钟")
            loadCurrentTask()
        }
        binding.btnTY.setOnClickListener {
            handleButtonAction("20分钟")
            loadCurrentTask()
        }
        binding.btnTN.setOnClickListener {
            handleButtonAction("10分钟")
            loadCurrentTask()
        }
        binding.btnFV.setOnClickListener {
            handleButtonAction("5分钟")
            loadCurrentTask()
        }
        binding.btnO.setOnClickListener {
            handleButtonAction("1分钟")
            loadCurrentTask()
        }
        binding.btnRedo.setOnClickListener {
            handleButtonAction("")
            loadCurrentTask()
        }

        loadContextValue()
        loadCurrentTask()
    }

    private fun handleButtonAction(searchString: String) {
        val formRuleFile = File(requireContext().getExternalFilesDir(null), "form_rule.txt")
        val currentFile = File(requireContext().getExternalFilesDir(null), "current.txt")

        val pattern = Regex("%[01].*?\\n(?:%[^01].*?\\n)*")

        val formRuleContent = formRuleFile.readText()
        val tasks = pattern.findAll(formRuleContent)
            .map { it.value }
            .filter { it.contains(searchString) }
            .toList()

        if (tasks.isNotEmpty()) {
            val randomTask = tasks.random()

            currentFile.writeText(randomTask)

            val selectedTask = randomTask.substring(3).trim()

            val lines = selectedTask.lines()
            val aLine = lines.firstOrNull { it.startsWith("%A") }
            val processedTask = aLine?.substring(2)?.trim() ?: ""

            binding.editTextOutput.setText(processedTask)
        } else {
            currentFile.writeText("") // 清空current.txt文件
            binding.editTextOutput.setText("暂无适合的任务")
        }
    }


    private fun moveFilesFromAssets() {
        val fileDir = requireContext().getExternalFilesDir(null)
        val formRuleFile = File(fileDir, "form_rule.txt")
        val formDataFile = File(fileDir, "form_data.txt")
        val currentFile = File(fileDir, "current.txt")

        if (!formRuleFile.exists()) {
            copyFileFromAssets("form_rule.txt", formRuleFile)
        }

        if (!formDataFile.exists()) {
            copyFileFromAssets("form_data.txt", formDataFile)
        }

        if (!currentFile.exists()) {
            copyFileFromAssets("current.txt", currentFile)
        }
    }

    private fun copyFileFromAssets(assetFileName: String, destinationFile: File) {
        val assetManager = requireContext().assets
        try {
            val inputStream = assetManager.open(assetFileName)
            FileOutputStream(destinationFile).use { outputStream ->
                inputStream.copyTo(outputStream)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun loadContextValue() {
        try {
            val externalFilesDir = requireContext().getExternalFilesDir(null)
            val settingsFile = File(externalFilesDir, "magicsettings.txt")

            if (settingsFile.exists()) {
                val contextValue = settingsFile.readLines()
                    .firstOrNull { it.startsWith("%Z") }
                    ?.substring(2)
                    ?.trim()

                val contextText = when (contextValue) {
                    "1" -> "校内图书馆"
                    "2" -> "校内教室/会议室"
                    "3" -> "校内宿舍"
                    "4" -> "校外"
                    "5" -> "通勤"
                    "6" -> "家里"
                    "7" -> "上铺"
                    "8" -> "室内运动"
                    "9" -> "户外运动"
                    else -> contextValue
                }
                binding.ContextTextOutput.text = contextText?.let {
                    Editable.Factory.getInstance().newEditable(it)
                } ?: Editable.Factory.getInstance().newEditable("")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun loadCurrentTask() {
        val currentFile = File(requireContext().getExternalFilesDir(null), "current.txt")

        if (currentFile.exists()) {
            val selectedTask = currentFile.readText().trim()
            if (selectedTask.isNotEmpty()) {
                val lines = selectedTask.lines()
                val aLine = lines.firstOrNull { it.startsWith("%A") }
                val processedTask = aLine?.substring(2)?.trim() ?: ""
                binding.editTextOutput.setText(processedTask)
                return
            }
        }

        // 当前没有选定的任务，显示默认文本或提示信息
        binding.editTextOutput.setText("暂无选定任务")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
