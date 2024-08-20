package io.github.lmfs.box.util

import org.fusesource.jansi.Ansi

fun Ansi.space(count: Int = 1): Ansi = a(" ".repeat(count))
