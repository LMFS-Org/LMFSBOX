package io.github.lmfs.box.command

import com.github.ajalt.clikt.core.CliktCommand
import java.io.File

object PwdCommand : CliktCommand("Print the name of the current working directory", name = "pwd") {

    override fun run() {
        echo(File(System.getProperty("user.dir")).absoluteFile.toPath().normalize())
    }

}