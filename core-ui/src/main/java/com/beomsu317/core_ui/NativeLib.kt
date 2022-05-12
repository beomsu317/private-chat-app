package com.beomsu317.core_ui

class NativeLib {

    /**
     * A native method that is implemented by the 'core_ui' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(): String

    companion object {
        // Used to load the 'core_ui' library on application startup.
        init {
            System.loadLibrary("core_ui")
        }
    }
}