package ru.sorokina.unsplash.modern.ui.common

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun LazyStaggeredGrid(
    columnCount: Int,
    content: @Composable LazyStaggeredGridScope.() -> Unit,
) {
    val gridScope = LazyStaggeredGridScope(columnCount)
    content(gridScope)

    LazyColumn {
        item {
            Row(
                modifier = Modifier
                    .padding(PaddingValues(16.dp))
                    .clip(
                        RoundedCornerShape(
                            topStart = 10.dp,
                            topEnd = 10.dp
                        )
                    ),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                for (index in 0 until columnCount) {
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        for (itemContent in gridScope.items[index]) {
                            itemContent()
                        }
                    }
                }
            }
        }
    }
}

class LazyStaggeredGridScope(
    private val columnCount: Int,
) {
    var currentIndex = 0
    val items: Array<MutableList<@Composable () -> Unit>> =
        Array(columnCount) { mutableListOf() }

    fun item(content: @Composable () -> Unit) {
        items[currentIndex % columnCount] += content
        currentIndex += 1
    }
}