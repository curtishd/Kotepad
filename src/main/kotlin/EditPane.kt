package me.cdh

import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.JMenuItem
import javax.swing.JPopupMenu
import javax.swing.JTextArea
import javax.swing.event.DocumentEvent
import javax.swing.event.DocumentListener

class EditPane : JTextArea() {
    private val copy = JMenuItem("Copy").apply {
        addActionListener { copy() }
    }
    private val cut = JMenuItem("Cut").apply {
        addActionListener { cut() }
    }
    private val paste = JMenuItem("Paste").apply {
        addActionListener { paste() }
    }
    var modified = false

    private val popupMenu = JPopupMenu().apply {
        add(copy)
        add(cut)
        add(paste)
    }

    init {
        font = textFont
        wrapStyleWord = true
        lineWrap = true
        addMouseListener(object : MouseAdapter() {
            override fun mousePressed(e: MouseEvent?) {
                showPopup(e)
            }

            override fun mouseReleased(e: MouseEvent?) {
                showPopup(e)
            }

            fun showPopup(e: MouseEvent?) {
                if (e?.isPopupTrigger == true) popupMenu.show(e.component, e.x, e.y)
            }
        })
        document.addDocumentListener(object : DocumentListener {
            override fun insertUpdate(e: DocumentEvent?) {
                changedUpdate(e)
            }

            override fun removeUpdate(e: DocumentEvent?) {
                changedUpdate(e)
            }

            override fun changedUpdate(e: DocumentEvent?) {
                modified = true
            }
        })
    }

    private fun readResolve(): Any = EditPane()
}