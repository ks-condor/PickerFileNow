package com.evercheck.wallet.pickerfilenowapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.evercheck.wallet.custompickerfile.PickerComponent
import com.evercheck.wallet.custompickerfile.models.ComponentButton
import com.google.android.material.composethemeadapter.MdcTheme
import com.evercheck.wallet.custompickerfile.models.PickerOption
import com.evercheck.wallet.custompickerfile.models.PickerFileConfig
import com.evercheck.wallet.custompickerfile.models.TypeOption
import com.evercheck.wallet.custompickerfile.ui.PickerFileListener
import com.evercheck.wallet.pickerfilenowapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), PickerFileListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initPickerSelectorView()
    }

    private fun initPickerSelectorView() {
        val pickerFileConfig = PickerFileConfig(
            componentId = binding.pickerFileFront.id,
            selectedFileIcon = com.evercheck.wallet.custompickerfile.R.drawable.ic_file_pdf,
            pickerOptions = listOf(
                PickerOption(
                    name = "Photo library",
                    icon = R.drawable.ic_baseline_add_photo_alternate_24,
                    typeOption = TypeOption.Photo
                ),
                PickerOption(name = "Choose photo", icon = R.drawable.ic_baseline_folder_24,
                    typeOption = TypeOption.Photo),
                PickerOption(name = "Choose PDF", icon = com.evercheck.wallet.custompickerfile.R.drawable.ic_file_pdf,
                    typeOption = TypeOption.PDF)
            )
        )
        binding.pickerFileFront.setContent {
                PickerComponent(pickerFileConfig = pickerFileConfig,
                    pickerFileListener = this).ContentScreen()
        }

        binding.pickerFileBack.setContent {
                PickerComponent(
                    pickerFileConfig.copy(
                        componentId = binding.pickerFileBack.id,
                        componentIcon = R.drawable.ic_baseline_library_add_24,
                        componentButton = ComponentButton(componentButtonColor = 0xFF444444), pickerOptions = listOf(
                            PickerOption(
                                name = "Take photo", icon = R.drawable.ic_baseline_camera_alt_24,
                                colorIcon = 0xFFFF00FF,
                                typeOption = TypeOption.Photo
                            )
                        )
                    ),
                    pickerFileListener = this
                ).ContentScreen()
        }
    }

    /**
     *  @param componentId identificador unico del componente
     *  @param requestCode codigo de solicitud para saber que llamo se hizo y pertenece a que solicitud
     *  @param filePathString la ruta del archivo en el dispositivo
    * */
    override fun onResultSelectedFile(componentId: Int, requestCode: Int, filePathString: String) {
        Toast.makeText(applicationContext, "onResultSelectedFile--> componentId:$componentId filePathString:$filePathString", Toast.LENGTH_SHORT).show()
    }

    /**
     *  @param componentId identificador unico del componente
     *  @param requestCode codigo de solicitud que usa para saber que imagen fue la seleccionada y que se ha borrado actualmente
     * */
    override fun onRemovedFile(componentId: Int, requestCode: Int) {
        Toast.makeText(applicationContext, "onRemovedFile--> componentId:$componentId", Toast.LENGTH_SHORT).show()
    }

}