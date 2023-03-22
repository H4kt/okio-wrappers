package io.h4kt.okio.extensions

import okio.Buffer

fun ByteArray.toBuffer(): Buffer {
    return Buffer().write(this, offset = 0, byteCount = size)
}
