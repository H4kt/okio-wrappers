package io.h4kt.okio

import io.h4kt.okio.extensions.toBuffer
import io.h4kt.okio.extensions.write
import okio.*
import okio.Path.Companion.toPath
import kotlin.jvm.JvmInline

private inline val fileSystem: FileSystem
    get() = FileSystem.PLATFORM

@JvmInline
value class File(
    val path: Path
) : IFile {

    override val metadata: FileMetadata
        get() = fileSystem.metadata(path)

    constructor(
        path: String,
        normalize: Boolean = false
    ) : this(path.toPath(normalize))

    override fun createDirectory() {
        fileSystem.createDirectory(path, mustCreate = false)
    }

    override fun createDirectories() {
        fileSystem.createDirectories(path, mustCreate = false)
    }

    override fun rename(target: Path) {

        if (fileSystem.exists(target)) {
            File(target).delete()
        }

        fileSystem.atomicMove(path, target)

    }

    override fun delete() {
        fileSystem.deleteRecursively(path, mustExist = false)
    }

    override fun exists(): Boolean {
        return fileSystem.exists(path)
    }

    override fun list(): List<Path> {
        return fileSystem.list(path)
    }

    override fun listOrNull(): List<Path>? {
        return fileSystem.listOrNull(path)
    }

    override fun listRecursively(
        followSymlinks: Boolean
    ): Sequence<Path> {
        return fileSystem.listRecursively(path, followSymlinks = followSymlinks)
    }

    override fun listFiles(): List<File> {
        return list().map(::File)
    }

    override fun listFilesOrNull(): List<File>? {
        return listOrNull()?.map(::File)
    }

    override fun listFilesRecursively(
        followSymlinks: Boolean
    ): Sequence<File> {
        return listRecursively(followSymlinks).map(::File)
    }

    override fun source(): Source {
        return fileSystem.source(path)
    }

    override fun sink(): Sink {
        return fileSystem.sink(path)
    }

    override fun appendingSink(): Sink {
        return fileSystem.appendingSink(path)
    }

    override fun write(bytes: ByteArray) {
        sink().use { it.write(bytes.toBuffer()) }
    }

    override fun append(bytes: ByteArray) {
        appendingSink().use { it.write(bytes.toBuffer()) }
    }

    override fun readAllBytes(): ByteArray {
        return source().use { it.buffer().readByteArray() }
    }

}
