package com.turingheights.core.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.turingheights.core.ui.theme.lemon

@Composable
fun Loader(
    modifier: Modifier = Modifier,
) {

    Box(
        modifier = modifier
    ) {
        CircularProgressIndicator(
            modifier = Modifier.align(Alignment.Center),
            color = lemon
        )
    }

}