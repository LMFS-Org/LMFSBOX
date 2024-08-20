package io.github.lmfs.box

import com.github.ajalt.clikt.testing.test
import io.github.lmfs.box.commands.Commands
import io.github.lmfs.box.util.space
import org.fusesource.jansi.Ansi
import org.fusesource.jansi.Ansi.ansi
import java.util.*

object LMFSBox {

    val license = this@LMFSBox::class.java.classLoader.getResourceAsStream("META-INF/lmfsbox.license.txt")?.readAllBytes()?.toString(Charsets.UTF_8) ?: ""
    val copyright = this@LMFSBox::class.java.classLoader.getResourceAsStream("META-INF/lmfsbox.copyright.txt")?.readAllBytes()?.toString(Charsets.UTF_8) ?: ""


    val properties = Properties().apply {
        load(this@LMFSBox::class.java.classLoader.getResourceAsStream("META-INF/lmfsbox.properties"))
    }

    val version = properties["lmfsbox.version"]
    val build = properties["lmfsbox.build"]
    val buildDate = properties["lmfsbox.build.date"]
    fun main(args: Array<String>) {
        if (args.isNotEmpty()) return runCommand(args.toList())
        println(
            ansi()
                .fgBrightBlue().bold().a("LMFSBox").reset().space(1)
                .fgBrightMagenta().a("$version").reset().space(1)
                .fgYellow().a("($buildDate)").reset().space(1)
                .fgBrightYellow().a("[$build]").reset()
        )
        println(ansi().a(Ansi.Attribute.ITALIC).a("Type \"help\", \"copyright\", \"credits\" or \"license\" for more information."))
        while (true) {
            print(ansi().fgGreen().a(">>>").space(1).reset())
            val input = readln()
            try {
                runCommand(input)
            } catch (throwable: Throwable) {
                throwable.printStackTrace(System.err)
            }
        }
    }

    fun runCommand(input: String) {
        val result = Commands.test(input, includeSystemEnvvars = true)
        print(result.output)
    }

    fun runCommand(input: List<String>) {
        val result = Commands.test(input, includeSystemEnvvars = true)
        print(result.output)
    }

}

fun main(args: Array<String>) = LMFSBox.main(args)