package io.h4kt.okio

import okio.FileSystem

actual val FileSystem.Companion.PLATFORM: FileSystem
    get() = SYSTEM
