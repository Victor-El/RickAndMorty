package com.turingheights.core.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

@Composable
fun CustomSpacer(
    space: Dp,
    vertical: Boolean = true,
) {
    Spacer(
        modifier = Modifier.then(
            if (vertical) Modifier.height(space) else Modifier.width(space)
        )
    )
}