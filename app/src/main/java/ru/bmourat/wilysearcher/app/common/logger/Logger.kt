package ru.bmourat.wilysearcher.app.common.logger

val Any.logTag: String
    get() {
        return this::class.java.simpleName
    }

interface Logger {

    enum class LogLevel{
        Verbose, Debug, Info, Warn, Error, NoLogs
    }

    fun verbose(tag: String, message: String)
    fun debug(tag: String, message: String)
    fun info(tag: String, message: String)
    fun warn(tag: String, message: String)
    fun error(tag: String, message: String)
    fun error(tag: String, message: String, throwable: Throwable?)
}
