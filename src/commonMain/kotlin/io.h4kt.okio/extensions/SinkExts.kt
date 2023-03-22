package io.h4kt.okio.extensions

import okio.Buffer
import okio.Sink

fun Sink.write(source: Buffer) {
    write(source, source.size)
}
