package com.viveks.quotesapp.ui.theme.quotes

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.viveks.quotesapp.local.Quote
import org.koin.androidx.compose.getViewModel

@Composable
@Preview
fun QuoteScreen(viewModel: QuotesViewModel = getViewModel()) {
    val quotes = viewModel.quotes.collectAsState()
    val pagerState = rememberPagerState(pageCount = { quotes.value.size })
    var backgroundColor by remember { mutableStateOf(Color.Black) }

    if (quotes.value.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        if (pagerState.currentPage < quotes.value.size) {
            // Add a check to prevent IndexOutOfBounds
            val currentQuote = quotes.value[pagerState.currentPage]
            backgroundColor = getBackgroundColor(pagerState.currentPage, isSystemInDarkTheme())
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
                .padding(horizontal = 24.dp, vertical = 48.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {

                VerticalPager(
                    state = pagerState,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxSize(),
                ) { page ->
                    QuoteCard(quotes.value[page])
                }

                // Right side vertical indicator
                VerticalPageIndicator(
                    viewModel,
                    quotes.value[pagerState.currentPage],
                    totalPages = quotes.value.size,
                    currentPage = pagerState.currentPage,
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(8.dp)
                        .width(24.dp),
                )

            }
        }
    }
}

@Composable
fun QuoteCard(quote: Quote) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "“${quote.quote}”",
            style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.Bold,
                lineHeight = 40.sp
            ),
            color = Color.White,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "- ${quote.author}",
            style = MaterialTheme.typography.bodyLarge.copy(fontStyle = FontStyle.Italic),
            color = Color.White.copy(alpha = 0.9f),
            textAlign = TextAlign.Center
        )
    }
}


fun getBackgroundColor(id: Any, isDark: Boolean): Color {
    if (id is Int) {
        return when (id % 5) {
            0 -> if (isDark) Color(0xFF3E2723) else Color(0xFFFFE0B2)
            1 -> if (isDark) Color(0xFF004D40) else Color(0xFFB2DFDB)
            2 -> if (isDark) Color(0xFF1A237E) else Color(0xFFC5CAE9)
            3 -> if (isDark) Color(0xFF880E4F) else Color(0xFFF8BBD0)
            else -> if (isDark) Color(0xFF263238) else Color(0xFFCFD8DC)
        }
    } else {
        return if (isDark) Color(0xFF3E2723) else Color(0xFFFFE0B2)
    }

}