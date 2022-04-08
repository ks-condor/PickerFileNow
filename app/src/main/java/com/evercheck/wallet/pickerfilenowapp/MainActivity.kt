package com.evercheck.wallet.pickerfilenowapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.evercheck.wallet.pickerfilenow.PickerNowView
import com.evercheck.wallet.pickerfilenow.models.ComponentButton
import com.google.android.material.composethemeadapter.MdcTheme
import com.evercheck.wallet.pickerfilenow.models.PickerOption
import com.evercheck.wallet.pickerfilenow.models.PickerFileConfig
import com.evercheck.wallet.pickerfilenow.models.TypeOption
import com.evercheck.wallet.pickerfilenowapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initPickerSelectorView()
    }

    private fun initPickerSelectorView() {
        val pickerFileConfig = PickerFileConfig(
            pickerOptions = listOf(
                PickerOption(
                    name = "Photo library",
                    icon = R.drawable.ic_baseline_add_photo_alternate_24,
                    typeOption = TypeOption.Gallery
                ),
                PickerOption(name = "Choose photo", icon = R.drawable.ic_baseline_folder_24,
                    typeOption = TypeOption.Photo),
                PickerOption(name = "Take photo", icon = R.drawable.ic_baseline_camera_alt_24,
                    typeOption = TypeOption.PDF)
            )
        )
        val pickerSelectorFront = PickerNowView(pickerFileConfig)
        val pickerSelectorBack = PickerNowView(
            pickerFileConfig.copy(
                componentIcon = R.drawable.ic_baseline_library_add_24,
                componentButton = ComponentButton(componentButtonColor = 0xFF444444), pickerOptions = listOf(
                    PickerOption(
                        name = "Take photo", icon = R.drawable.ic_baseline_camera_alt_24,
                        colorIcon = 0xFFFF00FF,
                        typeOption = TypeOption.Gallery
                    )
                )
            )
        )
        binding.pickerSelectorFront.setContent {
            MdcTheme {
                pickerSelectorFront.ShowContentView()
            }
        }

        binding.pickerSelectorBack.setContent {
            MdcTheme {
                pickerSelectorBack.ShowContentView()
            }
        }
    }

}