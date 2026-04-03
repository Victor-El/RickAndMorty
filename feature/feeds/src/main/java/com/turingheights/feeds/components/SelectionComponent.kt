package com.turingheights.feeds.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.turingheights.components.CustomSpacer
import com.turingheights.core.ui.theme.baseTextStyle
import com.turingheights.core.ui.theme.blueCardBackground
import com.turingheights.core.ui.theme.lemon
import com.turingheights.core.ui.theme.lemonGrey
import com.turingheights.feeds.R

@Composable
fun SelectionComponent(
    modifier: Modifier = Modifier,
    selected: Int,
    total: Int,
) {
    Row(
        modifier = modifier.background(color = blueCardBackground),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .height(60.dp)
                .width(2.dp)
                .background(color = lemon)
        ) {}
        CustomSpacer(12.dp, false)
        Column {
            Text(
                stringResource(R.string.selected_entities).uppercase(),
                style = baseTextStyle.copy(
                    fontSize = 10.sp,
                    color = lemonGrey,
                )
            )
            CustomSpacer(4.dp)
            Text(
                "$selected / $total",
                style = baseTextStyle.copy(
                    fontSize = 20.sp,
                    color = lemonGrey,
                    fontWeight = FontWeight.Bold,
                )
            )
        }
    }
}

@Composable
private fun AddToFavourites(
    modifier: Modifier = Modifier,
    onFavouriteClick: () -> Unit,
) {
    Button(
        modifier = modifier,
        onClick = onFavouriteClick
    ) {
        Text(
            "Add to favourites"
        )
    }
}