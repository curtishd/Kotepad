package me.cdh

import java.awt.Font
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import kotlin.time.Duration.Companion.hours

val textFont = Font("Cascadia Mono", Font.PLAIN, 16)

val labelFont = Font("Cascadia Mono", Font.BOLD, 22)

fun isDayTime(): Boolean =
    LocalTime.now().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)).substring(0, 2)
            .toInt().hours in 7.hours..18.hours