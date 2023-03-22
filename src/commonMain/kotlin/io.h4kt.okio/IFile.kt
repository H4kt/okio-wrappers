package io.h4kt.okio

import okio.FileMetadata
import okio.Path
import okio.Path.Companion.toPath
import okio.Sink
import okio.Source

internal interface IFile {

    val metadata: FileMetadata

    fun createDirectory()
    fun createDirectories()

    fun rename(target: Path)

    fun rename(
        target: String,
        normalize: Boolean = false
    ): Unit = rename(target.toPath(normalize))

    fun delete()

    fun exists(): Boolean

    fun list(): List<Path>
    fun listOrNull(): List<Path>?
    fun listRecursively(followSymlinks: Boolean = false): Sequence<Path>

    fun listFiles(): List<File>
    fun listFilesOrNull(): List<File>?
    fun listFilesRecursively(followSymlinks: Boolean = false): Sequence<File>

    fun source(): Source

    fun sink(): Sink
    fun appendingSink(): Sink

    fun write(bytes: ByteArray)
    fun append(bytes: ByteArray)

    fun readAllBytes(): ByteArray

}