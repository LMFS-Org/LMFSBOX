package io.github.lmfs.box.commands

import com.github.ajalt.clikt.core.CliktCommand
import io.github.lmfs.box.LMFSBox

object LicenseCommand : CliktCommand(name = "license") {

    override fun run() {
        println(LMFSBox.license)
    }

}