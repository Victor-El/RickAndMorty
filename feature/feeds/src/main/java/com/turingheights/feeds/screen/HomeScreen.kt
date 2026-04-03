package com.turingheights.feeds.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.turingheights.components.CharacterCard
import com.turingheights.components.CustomSpacer
import com.turingheights.components.TitleBar
import com.turingheights.core.ui.theme.darkLemon
import com.turingheights.core.ui.theme.lemonGrey
import com.turingheights.core.ui.theme.white
import com.turingheights.feeds.R
import com.turingheights.feeds.action.HomeScreenAction
import com.turingheights.feeds.components.SelectionComponent
import com.turingheights.favourites.viewmodel.HomeViewModel
import kotlinx.serialization.Serializable

@Serializable
data object HomeScreenRoute

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
) {

    val state = viewModel.state.collectAsStateWithLifecycle()


    LaunchedEffect(Unit) {
        viewModel.onAction(HomeScreenAction.Load)
    }

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
            trailing = {
                Image(
                    modifier = Modifier.size(36.dp).clickable {
                        viewModel.onAction(HomeScreenAction.NavigateToFavourite)
                    },
                    painter = painterResource(com.turingheights.core.R.drawable.ic_unfavourite),
                    contentDescription = null,
                )
            }
        )
        Text(
            stringResource(R.string.experimental_data_log).uppercase(),
            style = TextStyle(
                fontFamily = FontFamily.Monospace,
                color = darkLemon,
                fontSize = 12.sp,
            )
        )
        CustomSpacer(8.dp)
        Text(
            stringResource(R.string.subjects).uppercase(),
            style = TextStyle(
                fontFamily = FontFamily.Monospace,
                color = white,
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold
            )
        )

        CustomSpacer(8.dp)
        Text(
            stringResource(R.string.archive_01).uppercase(),
            style = TextStyle(
                fontFamily = FontFamily.Monospace,
                color = lemonGrey,
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold
            )
        )

        CustomSpacer(12.dp)

        AnimatedVisibility(
            visible = state.value.selected.isNotEmpty(),
            enter = fadeIn(),
            exit = fadeOut(),
        ) {
            SelectionComponent(
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                selected = state.value.selected.size,
                total = state.value.characters.size
            )
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
        ) {
            items(state.value.characters, key = { it.id }) { character ->
                CharacterCard(
                    id = character.id,
                    name = character.name,
                    status = character.status,
                    species = character.specie,
                    gender = character.gender,
                    origin = character.origin,
                    location = character.location,
                    image = character.image,
                    isFavourite = state.value.favourites.contains(character.id),
                    selected = state.value.selected.contains(character.id),
                    onFavouriteClick = {
                        if (state.value.favourites.contains(character.id)) {
                            viewModel.onAction(HomeScreenAction.UnFavourite(character.id))
                        } else {
                            viewModel.onAction(HomeScreenAction.Favourite(listOf(character.id)))
                        }
                    },
                    onCardClick = {
                        if (state.value.selected.contains(character.id)) {
                            viewModel.onAction(HomeScreenAction.RemoveFromSelection(character.id))
                        } else {
                            viewModel.onAction(HomeScreenAction.AddToSelection(character.id))
                        }
                    },
                    onLongClick = {
                        viewModel.onAction(HomeScreenAction.StartSelection(character.id))
                    },
                )
                CustomSpacer(16.dp)
            }
        }

    }
}