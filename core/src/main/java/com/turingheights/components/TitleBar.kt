package com.turingheights.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.turingheights.core.R
import com.turingheights.core.ui.theme.lemon

@Composable
fun TitleBar(
    modifier: Modifier = Modifier,
    leading: (@Composable () -> Unit)? = null,
    trailing: (@Composable () -> Unit)? = null,
) {

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = if (leading == null && trailing == null)
            Arrangement.Center else if (trailing != null)
                Arrangement.SpaceBetween else Arrangement.Start,
    ) {
        leading?.invoke()
        CustomSpacer(4.dp)
        Text(
            stringResource(R.string.rick_and_morty).uppercase(),
            style = TextStyle(
                fontFamily = FontFamily.Monospace,
                color = lemon,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
            ),
            textAlign = TextAlign.Center
        )
        trailing?.invoke()
    }

}