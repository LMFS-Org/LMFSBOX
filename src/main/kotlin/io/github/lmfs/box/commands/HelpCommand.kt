package io.github.lmfs.box.commands

import com.github.ajalt.clikt.core.CliktCommand

object HelpCommand : CliktCommand(name = "help") {

    override fun run() {
        Commands.echoFormattedHelp()
    }

}