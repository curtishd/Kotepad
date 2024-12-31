package me.cdh

import com.formdev.flatlaf.themes.FlatMacDarkLaf
import java.io.File
import javax.swing.SwingUtilities

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        FlatMacDarkLaf.setup()
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