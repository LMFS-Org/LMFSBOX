package io.github.lmfs.box

import com.github.ajalt.clikt.testing.test
import com.github.ajalt.mordant.rendering.AnsiLevel
import io.github.lmfs.box.command.Commands
import io.github.lmfs.box.util.makeJLineTreeCompleter
import io.github.lmfs.box.util.space
import org.fusesource.jansi.Ansi
import org.fusesource.jansi.Ansi.ansi
import org.jline.reader.EndOfFileException
import org.jline.reader.LineReaderBuilder
import org.jline.reader.UserInterruptException
import org.jline.terminal.TerminalBuilder
import java.util.*


object LMFSBox {

    @JvmStatic
    val LICENSE = this.javaClass.classLoader
        .getResourceAsStream("META-INF/lmfsbox.license.txt")
        ?.readAllBytes()
        ?.toString(Charsets.UTF_8)
        ?: ""

    @JvmStatic
    val COPYRIGHT = this.javaClass.classLoader
        .getResourceAsStream("META-INF/lmfsbox.copyright.txt")
        ?.readAllBytes()
        ?.toString(Charsets.UTF_8)
        ?: ""

    @JvmStatic
    val PROPERTIES = Properties().also {
        it.load(
            this.javaClass.classLoader.getResourceAsStream("META-INF/lmfsbox.properties")
        )
    }

    @JvmStatic
    val VERSION = PROPERTIES["lmfsbox.version"]

    @JvmStatic
    val BUILD = PROPERTIES["lmfsbox.build"]

    @JvmStatic
    val BUILD_DATE = PROPERTIES["lmfsbox.build.date"]

    @JvmStatic
    val GIT_HASH = PROPERTIES["lmfsbox.git.hash"]

    @JvmStatic
    val GIT_BRANCH = PROPERTIES["lmfsbox.git.branch"]

    fun main(args: Array<String>) {
        if (args.isNotEmpty())
            return runCommand(args.toList())

        println(
            ansi()
                .fgBrightBlue().bold().a("LMFSBox").reset().space(1)
                .fgBrightMagenta().a("$VERSION").reset().space(1)
                .fgYellow().a("(commits/$GIT_BRANCH:$GIT_HASH, $BUILD_DATE)").reset().space(1)
                .fgBrightYellow().a("[$BUILD]").reset()
        )

        println(
            ansi().a(Ansi.Attribute.ITALIC)
                .a("Type \"help\", \"copyright\" or \"license\" for more information.")
        )
        val builder = LineReaderBuilder.builder()
            .terminal(
                TerminalBuilder.builder()
                    .system(true)
                    .jansi(true)
                    .dumb(true)
                    .build()
            )
            .completer(Commands.makeJLineTreeCompleter())
            .build()
        while (true) {
            try {
                val input = builder.readLine(ansi().fgGreen().a(">>>").space(1).reset().toString())
                runCommand(input)
            } catch (exception: EndOfFileException) {
                break
            } catch (exception: UserInterruptException) {
                break
            } catch (throwable: Throwable) {
                throwable.printStackTrace(System.err)
            }
        }
    }

    fun runCommand(input: String) {
        val result = Commands.test(
            input,
            includeSystemEnvvars = true,
            ansiLevel = AnsiLevel.TRUECOLOR,
            height = Int.MAX_VALUE,
            width = Int.MAX_VALUE
        )
        print(result.output)
    }

    fun runCommand(input: List<String>) {
        val result = Commands.test(
            input,
            includeSystemEnvvars = true,
            ansiLevel = AnsiLevel.TRUECOLOR,
            height = Int.MAX_VALUE,
            width = Int.MAX_VALUE
        )
        print(result.output)
    }

}

fun main(args: Array<String>) = LMFSBox.main(args)
