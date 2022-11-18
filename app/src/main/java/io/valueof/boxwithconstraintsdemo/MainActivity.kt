package io.valueof.boxwithconstraintsdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.valueof.boxwithconstraintsdemo.ui.theme.BoxWithConstraintsDemoTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      BoxWithConstraintsDemoTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
          val cardData = remember { generateCards() }

          BoxWithConstraints {
            AdaptiveLayoutCardList(cardData)
          }
        }
      }
    }
  }
}

@Composable
fun BoxWithConstraintsScope.AdaptiveLayoutCardList(cardData: List<Pair<String, String>>) {
  LazyRow(
    horizontalArrangement = Arrangement.spacedBy(12.dp),
    verticalAlignment = Alignment.CenterVertically,
    contentPadding = PaddingValues(24.dp)
  ) {
    items(cardData) { card ->
      if (maxWidth > maxHeight) {
        // in landscape mode
        val cardWidth = maxWidth / 4
        MyCard(
          title = card.first,
          subtitle = card.second,
          height = maxHeight / 3,
          width = cardWidth - cardWidth * 0.15f
        )
      } else {
        // in portrait mode
        val cardWidth = maxWidth / 2
        MyCard(
          title = card.first,
          subtitle = card.second,
          height = maxHeight / 4,
          width = cardWidth - cardWidth * 0.2f
        )
      }
    }
  }
}

@Composable
fun MyCard(
  title: String,
  subtitle: String,
  height: Dp,
  width: Dp
) {
  Card(
    shape = RoundedCornerShape(12.dp),
    modifier = Modifier
      .height(height)
      .width(width)
  ) {
    Column(
      verticalArrangement = Arrangement.SpaceBetween,
      horizontalAlignment = Alignment.CenterHorizontally,
      modifier = Modifier
        .background(Color.DarkGray)
        .padding(24.dp)
    ) {
      Text(
        text = title,
        color = Color.White,
        style = MaterialTheme.typography.h6,
        textAlign = TextAlign.Center
      )
      Text(
        text = subtitle,
        color = Color.White,
        style = MaterialTheme.typography.subtitle1,
        textAlign = TextAlign.Center
      )
    }
  }
}

private fun generateCards(): List<Pair<String, String>> {
  return MutableList(20) { index ->
    val cardNumber = index + 1
    "Title $cardNumber" to "Subtitle $cardNumber"
  }
}

@Preview(showBackground = true)
@Composable
fun MyCardPreview(
  title: String = "Title 1",
  subtitle: String = "Subtitle 1",
  height: Dp = 80.dp,
  width: Dp = 60.dp
) {
  BoxWithConstraintsDemoTheme {
    MyCard(
      title = title,
      subtitle = subtitle,
      height = height,
      width = width
    )
  }
}
