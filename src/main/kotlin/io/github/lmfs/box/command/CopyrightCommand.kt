package io.github.lmfs.box.command

import com.github.ajalt.clikt.core.CliktCommand
import io.github.lmfs.box.LMFSBox

object CopyrightCommand : CliktCommand("Show copyright information", name = "copyright") {

    override fun run() {
        echo(LMFSBox.COPYRIGHT)
    }

}