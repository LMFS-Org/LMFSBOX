package io.github.lmfs.box.commands

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.subcommands

object Commands : CliktCommand(name = ">>>") {

    val ALIASES = mutableMapOf<String, List<String>>()

    init {
        Commands.subcommands(
            HelpCommand, CopyrightCommand, LicenseCommand, ListCommand, ExitCommand,
            TimeCommand
        )
    }

    override fun aliases() = ALIASES

    override fun run() = Unit

}