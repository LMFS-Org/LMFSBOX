package io.github.lmfs.box.commands

import com.github.ajalt.clikt.core.CliktCommand
import io.github.lmfs.box.LMFSBox

object CopyrightCommand : CliktCommand(name = "copyright") {

    override fun run() {
        println(LMFSBox.copyright)
    }

}