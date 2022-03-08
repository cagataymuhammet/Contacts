package com.cagataymuhammet.contactlist.util.extentions

import java.util.*

fun String.toTrLowerCase(): String {
    return this.toLowerCase(Locale("tr", "TR"))
}

fun String.isContaintsTR(other: String): Boolean {
    return this.toTrLowerCase().contains(other.toTrLowerCase())
}

