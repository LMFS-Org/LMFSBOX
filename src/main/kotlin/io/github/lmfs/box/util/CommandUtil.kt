package io.github.lmfs.box.util

import com.github.ajalt.clikt.completion.CompletionCandidates
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.Argument
import com.github.ajalt.clikt.parameters.options.Option
import io.github.lmfs.box.command.Commands
import org.jline.builtins.Completers
import org.jline.reader.Completer
import org.jline.reader.impl.completer.AggregateCompleter
import org.jline.reader.impl.completer.ArgumentCompleter
import org.jline.reader.impl.completer.NullCompleter
import org.jline.reader.impl.completer.StringsCompleter

fun CliktCommand.makeJLineTreeCompleter(): Completer =
    AggregateCompleter(registeredSubcommands().map { it.makeJLineTreeCompleter(emptyList()) })

fun CliktCommand.makeJLineTreeCompleter(parentCommandPath: List<CliktCommand>): Completer =
    registeredSubcommands().let { subcommands ->
        val commandPath = parentCommandPath + this
        if (subcommands.isEmpty()) ArgumentCompleter(
            commandPath.map { StringsCompleter(it.commandName) } +
                    Completers.OptionCompleter(
                        registeredArguments().map { it.makeJLineTreeCompleter() },
                        registeredOptions().flatMap { it.makeJLineDescription() },
                        commandPath.size
                    )
        )
        else AggregateCompleter(subcommands.map { it.makeJLineTreeCompleter(commandPath) })
    }

fun CompletionCandidates.makeJLineCompleter(): Completer = when (this) {
    is CompletionCandidates.Fixed -> StringsCompleter(candidates)
    is CompletionCandidates.Path -> Completers.FileNameCompleter()
    is CompletionCandidates.None -> NullCompleter.INSTANCE
    else -> NullCompleter.INSTANCE
}

fun Option.makeJLineDescription(): List<Completers.OptDesc> {
    val valueCompleter = completionCandidates.makeJLineCompleter()
    return names.map { Completers.OptDesc(it, it, this.optionHelp(Commands.createRootContext()), valueCompleter) } +
            secondaryNames.map { Completers.OptDesc(it, it) }
}

fun Argument.makeJLineTreeCompleter() = completionCandidates.makeJLineCompleter()
