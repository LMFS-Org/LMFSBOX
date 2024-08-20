package io.github.lmfs.box.commands

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.long
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

object TimeCommand: CliktCommand(name = "time") {

    val format by option("--format", "-F", help = "Set the date format for output")
    val time by option("--time", "-T", help = "Set the time stamp for output").long()

    override fun run() {
        val dateFormat = format?.let { DateTimeFormatter.ofPattern(it) }
        val date = if (time == null) LocalDateTime.now() else LocalDateTime.ofInstant(Instant.ofEpochMilli(time!!), ZoneId.systemDefault())
        println(if (format == null) date else dateFormat!!.format(date))
    }

}