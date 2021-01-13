package com.marwaeltayeb.weatherforecast.theme

import android.app.Dialog
import android.content.Context
import android.view.Window
import android.widget.TextView
import com.marwaeltayeb.weatherforecast.R

class DialogManager {

    companion object {
        fun showCustomAlertDialog(context: Context?, callback: ColorDialogCallback) {
            val dialog = Dialog(context!!)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.color_pallete)

            val blueColor = dialog.findViewById<TextView>(R.id.greyColor)
            blueColor.setOnClickListener {
                callback.onChosen(blueColor.text.toString())
                dialog.cancel()
            }

            val blackColor = dialog.findViewById<TextView>(R.id.blackColor)
            blackColor.setOnClickListener {
                callback.onChosen(blackColor.text.toString())
                dialog.cancel()
            }

            val redColor = dialog.findViewById<TextView>(R.id.redColor)
            redColor.setOnClickListener {
                callback.onChosen(redColor.text.toString())
                dialog.cancel()
            }

            val purpleColor = dialog.findViewById<TextView>(R.id.purpleColor)
            purpleColor.setOnClickListener {
                callback.onChosen(purpleColor.text.toString())
                dialog.cancel()
            }

            val greenColor = dialog.findViewById<TextView>(R.id.greenColor)
            greenColor.setOnClickListener {
                callback.onChosen(greenColor.text.toString())
                dialog.cancel()
            }

            val greyColor = dialog.findViewById<TextView>(R.id.blueColor)
            greyColor.setOnClickListener {
                callback.onChosen(greyColor.text.toString())
                dialog.cancel()
            }

            val orangeColor = dialog.findViewById<TextView>(R.id.orangeColor)
            orangeColor.setOnClickListener {
                callback.onChosen(orangeColor.text.toString())
                dialog.cancel()
            }

            val pinkColor = dialog.findViewById<TextView>(R.id.pinkColor)
            pinkColor.setOnClickListener {
                callback.onChosen(pinkColor.text.toString())
                dialog.cancel()
            }

            dialog.show()
        }
    }
}