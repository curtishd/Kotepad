package me.cdh

import com.formdev.flatlaf.themes.FlatMacDarkLaf
import com.formdev.flatlaf.themes.FlatMacLightLaf
import java.io.File
import javax.swing.SwingUtilities

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        File(userFavour).run {
            if (!exists() || length() == 0L) {
                createNewFile()
                FlatMacDarkLaf.setup()
                menu.icon = scaleIcon("menu_white.svg")
            } else bufferedReader().use {
                it.readLine().split("=")[1].run {
                    when (this) {
                        "FlatMacDarkLaf" -> {
                            FlatMacDarkLaf.setup()
                            menu.icon = scaleIcon("menu_white.svg")
                        }

                        else -> {
                            FlatMacLightLaf.setup()
                            menu.icon = scaleIcon("menu_black.svg")
                        }
                    }
                }
            }
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