package com.nickcolmenero.mtg_helpertool.colorPicker

import android.graphics.Bitmap
import androidx.palette.graphics.Palette
fun createPaletteSync(bitmap: Bitmap): Palette = Palette.from(bitmap).generate()
fun createPaletteAsync(bitmap: Bitmap) {
    Palette.from(bitmap).generate { palette ->
        // Use generated instance.
    }
}