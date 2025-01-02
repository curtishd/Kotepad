package me.cdh

import com.formdev.flatlaf.themes.FlatMacDarkLaf
import com.formdev.flatlaf.themes.FlatMacLightLaf
import java.io.File
import javax.swing.SwingUtilities

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        if (isDayTime()) {
            FlatMacLightLaf.setup()
            menu.icon = scaleIcon("menu_black.svg")
        } else {
            FlatMacDarkLaf.setup()
            menu.icon = scaleIcon("menu_white.svg")
        }
        SwingUtilities.invokeLater {
            window.isVisible = true
            if (args.isNotEmpty()) {
                with(File(args[0])) {
                    bufferedReader().use {
                        contentPane.text = it.readText()
                    }
                    userSelectedFile = this
                    window.title = name
                    contentPane.modified = false
                }
            }
        }
    }
}