package com.gplay.core.util.datetime

import kotlinx.datetime.Instant

data class InstantWrapper(val isoString: String) {
    val value: Instant get() = Instant.parse(isoString)
}
