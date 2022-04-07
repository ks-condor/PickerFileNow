package com.skysmobile.apps.pickerfileselectertest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.composethemeadapter.MdcTheme
import com.skysmobile.apps.pickerfileselectertest.databinding.ActivityMainBinding
import com.skysmobile.apps.pickerfileselector.PickerSelector
import com.skysmobile.apps.pickerfileselector.models.PickerOption
import com.skysmobile.apps.pickerfileselector.models.PickerSelectorConfig

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initPickerSelectorView()
    }

    private fun initPickerSelectorView() {
        val pickerSelectorConfig = PickerSelectorConfig(options = listOf(PickerOption(name = "Photo library", icon = R.drawable.ic_baseline_add_photo_alternate_24),
            PickerOption(name = "Choose photo", icon = R.drawable.ic_baseline_folder_24),
            PickerOption(name = "Take photo", icon = R.drawable.ic_baseline_camera_alt_24)))
        val pickerSelectorFront = PickerSelector(pickerSelectorConfig)
        val pickerSelectorBack = PickerSelector(pickerSelectorConfig.copy(iconSelector = R.drawable.ic_baseline_library_add_24,
            colorButtonSelector = 0xFF444444, options = listOf(
                PickerOption(name = "Take photo", icon = R.drawable.ic_baseline_camera_alt_24,
                    colorIcon = 0xFFFF00FF)
            )))
        binding.pickerSelectorFront.setContent {
            MdcTheme {
                pickerSelectorFront.PickerSelectorView()
            }
        }

        binding.pickerSelectorBack.setContent {
            MdcTheme {
                pickerSelectorBack.PickerSelectorView()
            }
        }
    }

}