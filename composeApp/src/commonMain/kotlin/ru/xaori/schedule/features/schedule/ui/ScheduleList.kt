package ru.xaori.schedule.features.schedule.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.xaori.schedule.features.schedule.model.ScheduleDay

@Composable
fun ScheduleList(
    schedule: List<ScheduleDay>,
    pageState: PagerState,
) {
    HorizontalPager(
        state = pageState,
        pageSpacing = 16.dp,
    ) { page ->
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(bottom = 12.dp),
            modifier = Modifier.fillMaxHeight()
        ) {
            if (schedule[page].lessons.isNotEmpty()) {
                items(schedule[page].lessons) { lesson ->
                    ScheduleListItem(lesson)
                }
            } else {
               item {
                   ScheduleEmptyListItem()
               }
            }
        }
    }


}