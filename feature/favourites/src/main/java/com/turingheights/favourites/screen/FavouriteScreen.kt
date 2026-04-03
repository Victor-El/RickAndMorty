package com.turingheights.favourites.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.turingheights.components.CharacterCard
import com.turingheights.components.CustomSpacer
import com.turingheights.components.EmptyState
import com.turingheights.components.TitleBar
import com.turingheights.core.ui.theme.cosmicBlue
import com.turingheights.core.ui.theme.lemon
import com.turingheights.core.ui.theme.lemonGrey
import com.turingheights.core.ui.theme.white
import com.turingheights.favourites.R
import com.turingheights.favourites.action.FavouriteScreenAction
import com.turingheights.favourites.viewmodels.FavouriteViewModel
import kotlinx.serialization.Serializable


@Serializable
data object FavouriteScreenRoute

@Composable
fun FavouriteScreen(
    viewModel: FavouriteViewModel = hiltViewModel(),
) {

    val savedVariants = buildAnnotatedString {
        val saved = stringResource(R.string.saved)
        val variants = stringResource(R.string.variants)
        this.withStyle(
            style = SpanStyle(
                fontFamily = FontFamily.Default,
                color = white,
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold,
            )
        ) {
            this.append(saved)
        }
        this.append(" ")
        this.withStyle(
            style = SpanStyle(
                fontFamily = FontFamily.Default,
                color = lemon,
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold,
            )
        ) {
            this.append(variants)
        }

    }

    val state = viewModel.state.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 12.dp, end = 12.dp),
    ) {
        TitleBar(
            modifier = Modifier
                .padding(bottom = 24.dp)
                .fillMaxWidth()
                .padding(horizontal = 0.dp, vertical = 24.dp),
            leading = {
                Icon(
                    modifier = Modifier.size(36.dp).clickable {
                        viewModel.onAction(FavouriteScreenAction.NavigateBack)
                    },
                    imageVector = Icons.Rounded.KeyboardArrowLeft,
                    tint = lemonGrey,
                    contentDescription = stringResource(R.string.back),
                )
            }
        )
        Text(
            stringResource(R.string.registry_archive).uppercase(),
            style = TextStyle(
                fontFamily = FontFamily.Monospace,
                color = cosmicBlue,
                fontSize = 12.sp,
            )
        )
        CustomSpacer(8.dp)
        Text(
            savedVariants,
        )

        CustomSpacer(12.dp)

        AnimatedVisibility(
            visible = state.value.favourites.isEmpty(),
        ) {
            EmptyState()
        }

        AnimatedVisibility(
            visible = state.value.favourites.isNotEmpty(),
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
            ) {
                items(state.value.favourites, key = { it.id }) { character ->
                    CharacterCard(
                        modifier = Modifier.animateItem(),
                        id = character.id,
                        name = character.name,
                        status = character.status,
                        species = character.specie,
                        gender = character.gender,
                        origin = character.origin,
                        location = character.location,
                        image = character.image,
                        isFavourite = character.favourite,
                        selected = false,
                        onFavouriteClick = {
                            viewModel.onAction(FavouriteScreenAction.UnFavourite(character.id))
                        },
                    )
                    CustomSpacer(16.dp)
                }
            }
        }

    }
}