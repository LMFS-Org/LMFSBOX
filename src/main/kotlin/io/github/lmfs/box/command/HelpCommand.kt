package io.github.lmfs.box.command

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.optional

object HelpCommand : CliktCommand("Show this help message", name = "help") {

    private val argument by argument("command").optional()

    override fun run() {
        if (argument != null) {
            val subCommand = Commands.registeredSubcommands()
                .firstOrNull { it.commandName == argument }
                ?: HelpCommand
            subCommand.echoFormattedHelp()
        } else {
            Commands.echoFormattedHelp()
        }
    }

}