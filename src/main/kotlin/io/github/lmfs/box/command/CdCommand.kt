package io.github.lmfs.box.command

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.ProgramResult
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.types.path
import kotlin.io.path.Path


object CdCommand : CliktCommand("Change the working directory", name = "cd") {

    private val to by argument("to").path(mustExist = false, canBeSymlink = true, canBeDir = true, canBeFile = false)

    override fun run() {
        val current = Path(System.getProperty("user.dir"))
        val changed = current.resolve(to).toFile()
        if (!changed.isDirectory) throw ProgramResult(1)
        System.setProperty("user.dir", changed.toString())
        throw ProgramResult(0)
    }

}