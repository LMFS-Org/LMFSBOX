package io.github.lmfs.box.command

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.defaultLazy
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.boolean
import com.github.ajalt.clikt.parameters.types.file
import org.fusesource.jansi.Ansi
import org.fusesource.jansi.Ansi.ansi
import java.io.File
import java.nio.file.Files
import java.text.SimpleDateFormat
import java.util.*

object LsCommand :
    CliktCommand("List information about the files (the current directory by default)", name = "ls") {

    @JvmStatic
    var DATE_FORMAT = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

    private val directory by argument("directory")
        .file(canBeFile = false, canBeDir = true, mustExist = true, mustBeReadable = true)
        .defaultLazy { File(System.getProperty("user.dir")) }

    private val showDate by option("--date", "-D", help = "Display the last modification date of the file")
        .boolean()
        .default(true)

    private val showSize by option("--size", "-S", help = "Display the size of the file")
        .boolean()
        .default(true)

    @JvmStatic
    fun processStyle(file: File, isSymbolicLink: Boolean, ansi: Ansi = ansi()) = ansi.also {
        if (file.isDirectory)
            it.fgBlue().bold()
        if (file.isHidden)
            it.a(Ansi.Attribute.ITALIC)
        if (isSymbolicLink)
            it.fgBrightCyan().boldOff()
    }

    @JvmStatic
    fun formatFileSize(bytes: Long): String {
        if (bytes < 1024) return "${bytes}B"
        val units = arrayOf("KiB", "MiB", "GiB", "TiB", "PiB", "EiB", "ZiB", "YiB")
        var value = bytes.toDouble()
        var i = -1
        while (value >= 1024 && i < units.size - 1) {
            value /= 1024
            i++
            if (i == units.size - 1) break
        }
        return String.format("%.2f%s", value, units[i])
    }

    override fun run() {
        val files = directory.listFiles()?.toList() ?: emptyList()

        val sizePlaceholder = if (showSize) files.maxOf { formatFileSize(it.length()).length } else 0
        val lastModifiedPlaceholder =
            if (showDate) files.maxOf { DATE_FORMAT.format(Date(it.lastModified())).length } else 0

        for (file in files) {
            val style = ansi()
            val prefix = buildString {
                if (showDate) append(
                    "${DATE_FORMAT.format(Date(file.lastModified()))}${
                        " ".repeat(
                            lastModifiedPlaceholder
                        )
                    }".subSequence(0..lastModifiedPlaceholder)
                )
                if (showSize) append("${formatFileSize(file.length())}${" ".repeat(sizePlaceholder)}".subSequence(0..sizePlaceholder))
            }
            style.a(prefix)
            val isSymbolicLink = Files.isSymbolicLink(file.toPath())
            processStyle(file, isSymbolicLink, style)
            if (isSymbolicLink) {
                val target = Files.readSymbolicLink(file.toPath())
                style.fgBrightBlue()
                    .a(file.name)
                    .reset()
                    .a(" -> ")
                    .reset()
                val relativize = runCatching { directory.toPath().relativize(target) }.getOrDefault(target)
                style.a(relativize)
            } else style.a(file.name)
            echo(style.reset())
        }
    }

}