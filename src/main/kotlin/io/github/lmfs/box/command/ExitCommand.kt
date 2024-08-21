package io.github.lmfs.box.command

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.default
import com.github.ajalt.clikt.parameters.types.int
import kotlin.system.exitProcess

object ExitCommand : CliktCommand("Exit the program", name = "exit") {

    private val code by argument("code", help = "Status code").int().default(0)

    override fun run() {
        exitProcess(code)
    }

}