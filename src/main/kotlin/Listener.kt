package me.cdh

import com.formdev.flatlaf.FlatLaf
import com.formdev.flatlaf.extras.FlatAnimatedLafChange
import com.formdev.flatlaf.extras.FlatSVGIcon
import com.formdev.flatlaf.extras.FlatSVGIcon.ColorFilter
import com.formdev.flatlaf.themes.FlatMacDarkLaf
import com.formdev.flatlaf.themes.FlatMacLightLaf
import java.awt.BorderLayout
import java.awt.Color
import java.awt.EventQueue
import java.awt.event.ActionEvent
import java.io.File
import javax.swing.*
import javax.swing.border.EtchedBorder
import kotlin.concurrent.timer
import kotlin.system.exitProcess

var userSelectedFile: File? = null

fun openListener(e: ActionEvent) {
    val chooser = JFileChooser()
    chooser.dialogTitle = "Select File To Open"
    chooser.fileSelectionMode = JFileChooser.FILES_ONLY
    if (chooser.showOpenDialog(window) == JFileChooser.APPROVE_OPTION) {
        if (contentPane.modified) {
            val dialog = JOptionPane.showConfirmDialog(
                window,
                "File is modified, Are you sure to exit without saving?",
                "Exit without saving",
                JOptionPane.YES_NO_OPTION
            )
            if (dialog == JOptionPane.YES_OPTION) {
                readFile(chooser)
            }
        } else {
            readFile(chooser)
        }
        contentPane.modified = false
    }
}

private fun readFile(chooser: JFileChooser) {
    userSelectedFile = chooser.selectedFile
    userSelectedFile?.bufferedReader().use {
        contentPane.text = it?.readText()
    }
    window.title = userSelectedFile?.name
}

fun exitListener(e: ActionEvent) {
    exitEvent()
}

internal fun exitEvent() {
    if (contentPane.modified) {
        val dialog = JOptionPane.showConfirmDialog(
            window,
            "File is modified, Are you sure to exit without saving?",
            "Exit without saving",
            JOptionPane.YES_NO_OPTION
        )
        if (dialog == JOptionPane.YES_OPTION) exitProcess(0)
    } else
        exitProcess(0)
}

fun saveListener(e: ActionEvent) {
    if (userSelectedFile == null) {
        saveAsListener(e)
    } else {
        userSelectedFile?.bufferedWriter().use { it?.write(contentPane.text) }
        labelPopup()
        window.title = userSelectedFile?.name
    }
    contentPane.modified = false
}

fun saveAsListener(e: ActionEvent) {
    val chooser = JFileChooser()
    chooser.dialogTitle = "Save File"
    val dialog = chooser.showSaveDialog(window)
    if (dialog == JFileChooser.APPROVE_OPTION) {
        chooser.selectedFile.bufferedWriter().use { it.write(contentPane.text) }
        window.title = userSelectedFile?.name
        labelPopup()
    }
    contentPane.modified = false
}

fun lightThemeListener(e: ActionEvent) {
    EventQueue.invokeLater {
        FlatAnimatedLafChange.showSnapshot()
        FlatMacLightLaf.setup()
        FlatLaf.updateUI()
        FlatAnimatedLafChange.hideSnapshotWithAnimation()
        menu.icon = scaleIcon("menu_black.svg")
    }
}

fun darkThemeListener(e: ActionEvent) {
    EventQueue.invokeLater {
        FlatAnimatedLafChange.showSnapshot()
        FlatMacDarkLaf.setup()
        FlatLaf.updateUI()
        FlatAnimatedLafChange.hideSnapshotWithAnimation()
        menu.icon = scaleIcon("menu_white.svg")
    }
}

fun labelPopup() {
    val label = JLabel("Save")
    label.font = labelFont
    val p = JPanel()
    p.border = EtchedBorder(EtchedBorder.RAISED)
    p.add(label, BorderLayout.CENTER)
    val frame = with(JFrame()) {
        layout = BorderLayout()
        add(p, BorderLayout.CENTER)
        setSize(200, 50)
        val point = window.locationOnScreen
        setLocation(point.x + window.width * 3 / 8, point.y + window.height * 4 / 5)
        isUndecorated = true
        isResizable = false
        defaultCloseOperation = WindowConstants.DISPOSE_ON_CLOSE
        isVisible = true
        isAlwaysOnTop = true
        this
    }
    timer(period = 1000L, action = { frame.dispose() }, initialDelay = 1000L)
}