package me.cdh

import com.formdev.flatlaf.extras.FlatSVGIcon
import java.awt.event.KeyEvent.*
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import java.awt.image.BufferedImage
import java.awt.image.BufferedImage.TYPE_INT_ARGB
import javax.swing.*


val open = JMenuItem("Open").apply {
    addActionListener(::openListener)
    accelerator = KeyStroke.getKeyStroke(VK_O, CTRL_DOWN_MASK)
}

val save = JMenuItem("Save").apply {
    addActionListener(::saveListener)
    accelerator = KeyStroke.getKeyStroke(VK_S, CTRL_DOWN_MASK)
}

val saveAs = JMenuItem("Save As").apply {
    addActionListener(::saveAsListener)
    accelerator = KeyStroke.getKeyStroke(VK_S, CTRL_DOWN_MASK or SHIFT_DOWN_MASK)
}

val darkTheme = JRadioButtonMenuItem("Dark").apply {
    addActionListener(::darkThemeListener)
}

val lightTheme = JRadioButtonMenuItem("Light").apply {
    addActionListener(::lightThemeListener)
}

@Suppress("unused")
private val btnGroup = ButtonGroup().apply {
    add(darkTheme)
    add(lightTheme)
}

val themes = JMenu("Theme").apply {
    add(darkTheme)
    add(lightTheme)
}

val settings = JMenu("Settings").apply {
    add(themes)
}

val exit = JMenuItem("Exit").apply {
    addActionListener(::exitListener)
}

val menu = JMenu().apply {
    icon = scaleIcon("menu_white.svg")
    add(open)
    addSeparator()
    add(save)
    add(saveAs)
    addSeparator()
    add(settings)
    add(exit)
}

private val mBar = JMenuBar().apply {
    add(menu)
}

val contentPane = EditPane()

private val scrollPane = JScrollPane().apply {
    setViewportView(contentPane)
}

val window = JFrame().apply {
    defaultCloseOperation = WindowConstants.DO_NOTHING_ON_CLOSE
    addWindowListener(object : WindowAdapter() {
        override fun windowClosing(e: WindowEvent?) {
            exitEvent()
        }
    })
    setSize(800, 600)
    setLocationRelativeTo(null)
    isVisible = true

    jMenuBar = mBar
    add(scrollPane)
}

fun scaleIcon(src: String) =
    BufferedImage(20, 20, TYPE_INT_ARGB).apply {
        createGraphics().run {
            drawImage(FlatSVGIcon(src).image, 0, 0, 20, 20, null)
            dispose()
        }
    }.let { ImageIcon(it) }
