package com.nickcolmenero.mtg_helpertool.colorPicker

import android.graphics.Bitmap
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.palette.graphics.Palette



// Generate palette synchronously and return it.
fun createPaletteSync(bitmap: Bitmap): Palette = Palette.from(bitmap).generate()

// Generate palette asynchronously and use it on a different thread using onGenerated().
fun createPaletteAsync(bitmap: Bitmap) {
    Palette.from(bitmap).generate { palette ->
        // Use generated instance.
    }
}