package ru.bmourat.wilysearcher.app.common.logger

import android.util.Log

class AndroidLogger(private var logLevel: Logger.LogLevel = Logger.LogLevel.Verbose): Logger {

    companion object {
        const val appPrefix = "WilySearcher"
    }

    override fun verbose(tag: String, message: String) {
        if (logLevel <= Logger.LogLevel.Verbose) {
            Log.v("$appPrefix/$tag", message)
        }
    }

    override fun debug(tag: String, message: String) {
        if (logLevel <= Logger.LogLevel.Debug) {
            Log.d("$appPrefix/$tag", message)
        }
    }

    override fun info(tag: String, message: String) {
        if (logLevel <= Logger.LogLevel.Info) {
            Log.i("$appPrefix/$tag", message)
        }
    }

    override fun warn(tag: String, message: String) {
        if (logLevel <= Logger.LogLevel.Warn) {
            Log.w("$appPrefix/$tag", message)
        }
    }

    override fun error(tag: String, message: String) {
        error(tag, message, null)
    }

    override fun error(tag: String, message: String, throwable: Throwable?) {
        if (logLevel <= Logger.LogLevel.Error) {
            Log.e("$appPrefix/$tag", message, throwable)
        }
    }
}
