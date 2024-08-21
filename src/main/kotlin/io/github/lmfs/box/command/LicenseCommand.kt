package io.github.lmfs.box.command

import com.github.ajalt.clikt.core.CliktCommand
import io.github.lmfs.box.LMFSBox

object LicenseCommand : CliktCommand("Show license information", name = "license") {

    override fun run() {
        echo(LMFSBox.LICENSE)
    }

}