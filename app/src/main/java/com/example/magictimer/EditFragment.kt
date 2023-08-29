package com.example.magictimer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.magictimer.databinding.FragmentEditBinding
import java.io.File
import java.io.IOException

class EditFragment : Fragment() {

    private var _binding: FragmentEditBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 隐藏顶部状态栏
        requireActivity().window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        binding.btnEditReturn.setOnClickListener {
            findNavController().navigate(R.id.action_EditFragment_to_FirstFragment)
        }

        // 读取current.txt文件并显示内容
        val mainFile = File(requireContext().getExternalFilesDir(null), "current.txt")
        try {
            val text = mainFile.readText()
            binding.etFormData.setText(text) // 设置EditText的文本
        } catch (e: IOException) {
            e.printStackTrace()
            // 处理文件读取失败的逻辑
            Toast.makeText(requireContext(), "读取文件失败", Toast.LENGTH_SHORT).show()
        }

        // 删除按钮点击事件
        binding.btnDelete.setOnClickListener {
            val mainFile = File(requireContext().getExternalFilesDir(null), "current.txt")
            val formDataFile = File(requireContext().getExternalFilesDir(null), "form_data.txt")
            val formRuleFile = File(requireContext().getExternalFilesDir(null), "form_rule.txt")

            try {
                val textToDelete = binding.etFormData.text.toString()

                // 从current.txt文件中查找并提取任务
                val currentText = mainFile.readText()
                val pattern = Regex("%[01].*?\\n(?:%[^01].*?\\n)*")
                val updatedCurrentText = pattern.replace(currentText) { matchResult ->
                    val matchedTask = matchResult.value
                    if (matchedTask.contains(textToDelete)) {
                        "" // 删除匹配的任务
                    } else {
                        matchedTask // 保留不需要删除的任务
                    }
                }
                mainFile.writeText(updatedCurrentText)

                // 从form_data.txt文件中删除匹配的任务
                val formDataText = formDataFile.readText()
                val updatedFormDataText = pattern.replace(formDataText) { matchResult ->
                    val matchedTask = matchResult.value
                    if (matchedTask.contains(textToDelete)) {
                        "" // 删除匹配的任务
                    } else {
                        matchedTask // 保留不需要删除的任务
                    }
                }
                formDataFile.writeText(updatedFormDataText)

                // 从form_rule.txt文件中删除匹配的任务
                val formRuleText = formRuleFile.readText()
                val updatedFormRuleText = pattern.replace(formRuleText) { matchResult ->
                    val matchedTask = matchResult.value
                    if (matchedTask.contains(textToDelete)) {
                        "" // 删除匹配的任务
                    } else {
                        matchedTask // 保留不需要删除的任务
                    }
                }
                formRuleFile.writeText(updatedFormRuleText)

                // 清空current.txt文件内容
                mainFile.writeText("")

                Toast.makeText(requireContext(), "删除成功", Toast.LENGTH_SHORT).show()
            } catch (e: IOException) {
                e.printStackTrace()
                Toast.makeText(requireContext(), "删除失败", Toast.LENGTH_SHORT).show()
            }
        }

        // 更新按钮点击事件
        binding.btnUpdate.setOnClickListener {
            val mainFile = File(requireContext().getExternalFilesDir(null), "current.txt")
            val formDataFile = File(requireContext().getExternalFilesDir(null), "form_data.txt")
            val formRuleFile = File(requireContext().getExternalFilesDir(null), "form_rule.txt")

            try {
                val textToDelete = binding.etFormData.text.toString()

                // 从current.txt文件中查找并提取任务
                val currentText = mainFile.readText()
                val pattern = Regex("%[01].*?\\n(?:%[^01].*?\\n)*")
                val updatedCurrentText = pattern.replace(currentText) { matchResult ->
                    val matchedTask = matchResult.value
                    if (matchedTask.contains(textToDelete)) {
                        "" // 删除匹配的任务
                    } else {
                        matchedTask // 保留不需要删除的任务
                    }
                }
                mainFile.writeText(updatedCurrentText)

                // 从form_data.txt文件中删除匹配的任务
                val formDataText = formDataFile.readText()
                val updatedFormDataText = pattern.replace(formDataText) { matchResult ->
                    val matchedTask = matchResult.value
                    if (matchedTask.contains(textToDelete)) {
                        "" // 删除匹配的任务
                    } else {
                        matchedTask // 保留不需要删除的任务
                    }
                }
                formDataFile.writeText(updatedFormDataText)

                // 从form_rule.txt文件中删除匹配的任务
                val formRuleText = formRuleFile.readText()
                val updatedFormRuleText = pattern.replace(formRuleText) { matchResult ->
                    val matchedTask = matchResult.value
                    if (matchedTask.contains(textToDelete)) {
                        "" // 删除匹配的任务
                    } else {
                        matchedTask // 保留不需要删除的任务
                    }
                }
                formRuleFile.writeText(updatedFormRuleText)

                // 将etFormData中的内容追加到current.txt、form_data.txt和form_rule.txt中
                val formDataToAppend = binding.etFormData.text.toString() + "\n"
                mainFile.appendText(formDataToAppend)
                formDataFile.appendText(formDataToAppend)
                formRuleFile.appendText(formDataToAppend)

                Toast.makeText(requireContext(), "更新成功", Toast.LENGTH_SHORT).show()
            } catch (e: IOException) {
                e.printStackTrace()
                Toast.makeText(requireContext(), "更新失败", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
