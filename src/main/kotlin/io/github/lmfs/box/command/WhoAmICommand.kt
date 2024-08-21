package io.github.lmfs.box.command

import com.github.ajalt.clikt.core.CliktCommand

object WhoAmICommand : CliktCommand("Print the user name", name = "whoami") {

    override fun run() {
        echo(System.getProperty("user.name"))
    }

}