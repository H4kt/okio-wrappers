package io.h4kt.okio

import okio.FileSystem
import okio.NodeJsFileSystem

actual val FileSystem.Companion.PLATFORM: FileSystem
    get() = NodeJsFileSystem
