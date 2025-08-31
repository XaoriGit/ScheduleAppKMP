package ru.xaori.schedule.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import ru.xaori.schedule.features.clientChoice.ClientChoiceViewModel
import ru.xaori.schedule.features.clientChoice.model.ClientChoiceDataState
import ru.xaori.schedule.features.clientChoice.model.ClientTypeDestination
import ru.xaori.schedule.features.clientChoice.ui.ButtonClientChoice
import ru.xaori.schedule.features.myAppBar.ui.MyAppBar
import schedule.composeapp.generated.resources.Res
import schedule.composeapp.generated.resources.ic_cancel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClientChoiceScreen(
    showCancelButton: Boolean,
    goToBack: () -> Unit,
    goToMain: () -> Unit,
    viewModel: ClientChoiceViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.padding(16.dp, 8.dp)
    ) {
        MyAppBar("Расписание", "Выбор расписания") {
            if (showCancelButton) {
                IconButton(
                    onClick = goToBack,
                    modifier = Modifier.size(28.dp),
                ) {
                    Icon(
                        painterResource(Res.drawable.ic_cancel),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
        SecondaryTabRow(
            selectedTabIndex = uiState.selectedTabIndex,
            divider = {},
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            modifier = Modifier.clip(RoundedCornerShape(12.dp))
        ) {
            ClientTypeDestination.entries.forEachIndexed { index, type ->
                Tab(
                    selected = uiState.selectedTabIndex == index,
                    onClick = {
                        viewModel.onTabSelected(ClientTypeDestination.entries[index].ordinal)
                    },
                    text = {
                        Text(
                            type.title,
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                )
            }
        }
        TextField(
            value = uiState.searchQuery,
            onValueChange = { value -> viewModel.onSearchQueryChange(value) },
            label = {
                Text(
                    "Введите группу или фамилию",
                )
            },
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                focusedLabelColor = MaterialTheme.colorScheme.primary
            )
        )
        when (val state = uiState.dataState) {
            is ClientChoiceDataState.Loading -> {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.width(36.dp),
                        color = MaterialTheme.colorScheme.primary,
                        trackColor = MaterialTheme.colorScheme.surface,
                    )
                }
            }

            is ClientChoiceDataState.Success -> {
                LazyColumn {
                    if (uiState.selectedTabIndex == ClientTypeDestination.Group.ordinal) {
                        itemsIndexed(state.clientChoice.groups.filter {
                            it.contains(uiState.searchQuery, ignoreCase = true)
                        }) { index, group ->

                            ButtonClientChoice(group) {
                                viewModel.onClickClientChoice(
                                    it,
                                    goToMain
                                )
                            }
                        }
                    } else {
                        itemsIndexed(state.clientChoice.teachers.filter {
                            it.contains(uiState.searchQuery, ignoreCase = true)
                        }) { index, teacher ->
                            ButtonClientChoice(teacher) {
                                viewModel.onClickClientChoice(
                                    it,
                                    goToMain
                                )
                            }
                        }
                    }
                }
            }

            is ClientChoiceDataState.Error -> {
                Text(state.detail)
            }
        }

    }
}