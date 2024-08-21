package io.github.lmfs.box.command

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option

object EchoCommand : CliktCommand("Write content to the standard output.", name = "echo") {

    private val text by option("--text", "-T").default("")

    override fun run() {
        echo(text)
    }

}