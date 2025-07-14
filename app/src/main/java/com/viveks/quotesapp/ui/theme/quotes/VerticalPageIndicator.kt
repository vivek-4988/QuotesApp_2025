package com.viveks.quotesapp.ui.theme.quotes

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import com.viveks.quotesapp.MainApplication
import com.viveks.quotesapp.local.Quote

@Composable
fun  VerticalPageIndicator(
    viewModel: QuotesViewModel,
    quote : Quote,
    totalPages: Int,
    currentPage: Int,
    visibleIndicators: Int = 3,
    modifier: Modifier = Modifier,
){
    val startIndex = maxOf(0, currentPage - visibleIndicators / 2)
    val endIndex = minOf(totalPages, startIndex + visibleIndicators)

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {

        Spacer(modifier = Modifier.weight(1f))


        for (i in startIndex until endIndex){
            val isSelected = i == currentPage
            val size = if (isSelected) 12.dp else 8.dp
            val color = if (isSelected) Color.White else Color.White.copy(alpha = 0.5f)

            Box(
                modifier = Modifier
                    .padding(vertical = 4.dp)
                    .size(size)
                    .clip(CircleShape)
                    .background(color)
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        // ✅ Like/Share/Bookmark Icons at Bottom
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.padding(bottom = 24.dp)
        ) {
            val context = LocalContext.current

            IconButton(onClick = {
                viewModel.toggleFavorite(quote.id, quote.isFavorite)
                viewModel.toggleFavoriteWithAi(quote)
            }) {
                Icon(
                    imageVector = if (quote.isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = null,
                    tint = if (quote.isFavorite) Color.Red else Color.White
                )
            }
            IconButton(onClick = {
                val shareText = quote.quote
                val sendIntent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, shareText)
                    type = "text/plain"
                }
                context.startActivity(Intent.createChooser(sendIntent,"Today's Quote"))
            }) {
                Icon(
                    imageVector = Icons.Outlined.Share,
                    contentDescription = "Share",
                    modifier = Modifier.size(24.dp),
                    tint = Color.White
                )
            }
//            Icon(
//                imageVector = Icons.Outlined.Info,
//                contentDescription = "Bookmark",
//                modifier = Modifier.size(24.dp),
//                tint = Color.White
//            )
        }


    }
}