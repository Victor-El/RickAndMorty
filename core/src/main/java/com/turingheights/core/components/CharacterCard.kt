package com.turingheights.core.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.turingheights.core.R
import com.turingheights.core.ui.theme.baseTextStyle
import com.turingheights.core.ui.theme.blueCardBackground
import com.turingheights.core.ui.theme.cosmicBlue
import com.turingheights.core.ui.theme.lemon
import com.turingheights.core.ui.theme.lemonGrey
import com.turingheights.core.ui.theme.white

@Composable
fun CharacterCard(
    modifier: Modifier = Modifier,
    id: Int,
    name: String,
    status: String,
    species: String,
    gender: String,
    origin: String,
    location: String,
    image: String,
    isFavourite: Boolean,
    selected: Boolean,
    onFavouriteClick: () -> Unit,
    onCardClick: () -> Unit = {},
    onLongClick: () -> Unit = {},
) {
    Box(
        modifier = Modifier.then(
            if (selected) Modifier.border(width = 1.dp, color = lemon, shape = RoundedCornerShape(4.dp)) else Modifier
        ),
    ) {
        Column(
            modifier = modifier
                .background(color = blueCardBackground, shape = RoundedCornerShape(4.dp))
                .combinedClickable(
                    onClick = onCardClick,
                    onLongClick = onLongClick,
                )
                .clip(RoundedCornerShape(4.dp)),
        ) {
            AsyncImage(
                modifier = Modifier
                    .height(250.dp)
                    .fillMaxWidth(),
                model = image,
                contentDescription = name,
                contentScale = ContentScale.Crop,
            )

            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
            ) {
                Text(
                    name,
                    style = baseTextStyle.copy(
                        fontFamily = FontFamily.Default,
                        color = white,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                    ),
                )
                CustomSpacer(8.dp)
                CharItemRow(
                    modifier = Modifier.fillMaxWidth(),
                    title = stringResource(R.string.status).uppercase(),
                    value = status,
                )
                CharItemRow(
                    modifier = Modifier.fillMaxWidth(),
                    title = stringResource(R.string.species).uppercase(),
                    value = "$species / $gender",
                    valueTextStyle = baseTextStyle.copy(
                        fontFamily = FontFamily.Default,
                        color = white,
                        fontSize = 14.sp,
                    )
                )
                CharItemRowVert(
                    title = stringResource(R.string.last_known_origin).uppercase(),
                    value = origin,
                    valueTextStyle = baseTextStyle.copy(
                        fontFamily = FontFamily.Default,
                        color = white,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                )
                CharItemRowVert(
                    title = stringResource(R.string.current_location).uppercase(),
                    value = location,
                )
            }
        }
        Selected(
            modifier = Modifier.align(Alignment.TopStart).padding(top = 12.dp, start = 12.dp),
            selected
        )
        Favourite(
            modifier = Modifier.align(Alignment.TopEnd).padding(top = 24.dp, end = 24.dp),
            isFavourite,
            onFavouriteClick
        )
    }
}

@Composable
private fun Favourite(
    modifier: Modifier = Modifier,
    isFavourite: Boolean,
    onFavouriteClick: () -> Unit,
) {
    Image(
        modifier = modifier.clickable {
            onFavouriteClick()
        },
        painter = painterResource(if (isFavourite) R.drawable.ic_unfavourite else R.drawable.ic_favourite),
        contentDescription = stringResource(R.string.favourite),
    )
}

@Composable
private fun Selected(
    modifier: Modifier = Modifier,
    selected: Boolean,
) {
    if (selected) {
        Text(
            stringResource(R.string.selected),
            modifier = modifier.background(
                color = lemon,
                shape = RoundedCornerShape(4.dp)
            ).padding(vertical = 4.dp, horizontal = 8.dp),
            style = baseTextStyle.copy(
                color = blueCardBackground,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold
            )
        )
    }
}

@Composable
private fun CharItemRow(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
    valueTextStyle: TextStyle = baseTextStyle.copy(
        fontFamily = FontFamily.Default,
        color = lemon,
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
    )
) {
    Row(
        modifier = modifier.padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            title,
            style = baseTextStyle.copy(
                color = lemonGrey,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
            )
        )

        Text(
            value,
            style = valueTextStyle
        )
    }
}

@Composable
private fun CharItemRowVert(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
    valueTextStyle: TextStyle = baseTextStyle.copy(
        fontFamily = FontFamily.Default,
        color = cosmicBlue,
    fontSize = 14.sp,
    fontWeight = FontWeight.Medium,
    )
) {
    Column(
        modifier = modifier.padding(vertical = 8.dp),
    ) {
        Text(
            title,
            style = baseTextStyle.copy(
                color = lemonGrey,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
            )
        )

        CustomSpacer(4.dp)

        Text(
            value,
            style = valueTextStyle
        )
    }
}