package io.github.lmfs.box.command

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.Context
import com.github.ajalt.clikt.core.subcommands
import com.github.ajalt.clikt.output.HelpFormatter

object Commands : CliktCommand("LMFSBox", name = ">>>", hidden = true, printHelpOnEmptyArgs = false) {

    init {
        Commands.subcommands(
            HelpCommand, CopyrightCommand, LicenseCommand, LsCommand, ExitCommand,
            TimeCommand, WhoAmICommand, PwdCommand, CdCommand, EchoCommand
        )
    }

    @JvmStatic
    val COMMAND_ALIASES = mutableMapOf<String, List<String>>()

    @JvmStatic
    fun createRootContext(parent: Context? = null): Context =
        CliktCommand::class.java.getDeclaredMethod(
            "createContext",
            List::class.java,
            Context::class.java,
            List::class.java
        ).apply {
            isAccessible = true
        }.invoke(this, emptyList<Any?>(), parent, emptyList<Any?>()) as Context


    override fun allHelpParams(): List<HelpFormatter.ParameterHelp> {
        return super.allHelpParams().filterNot { it is HelpFormatter.ParameterHelp.Option && "--help" in it.names }
    }

    override fun aliases() = COMMAND_ALIASES

    override fun run() = Unit

}