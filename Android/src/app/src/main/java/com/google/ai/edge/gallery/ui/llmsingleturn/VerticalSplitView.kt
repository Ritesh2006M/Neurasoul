// /gallery/ui/llmsingleturn/VerticalSplitView.kt

package com.google.ai.edge.gallery.ui.llmsingleturn

// import androidx.compose.ui.tooling.preview.Preview
// import com.google.ai.edge.gallery.ui.theme.GalleryTheme
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.google.ai.edge.gallery.ui.theme.customColors

@Composable
fun VerticalSplitView(
  topView: @Composable () -> Unit,
  bottomView: @Composable () -> Unit,
  modifier: Modifier = Modifier,
  initialRatio: Float = 0.5f,
  minTopHeight: Dp = 250.dp,
  minBottomHeight: Dp = 200.dp,
  handleThickness: Dp = 20.dp,
) {
  var splitRatio by remember { mutableFloatStateOf(initialRatio) }
  var columnHeightPx by remember { mutableFloatStateOf(0f) }
  var columnHeightDp by remember { mutableStateOf(0.dp) }
  val localDensity = LocalDensity.current

  Column(
    modifier =
      modifier.fillMaxSize().onGloballyPositioned { coordinates ->
        // Set column height using the LayoutCoordinates
        columnHeightPx = coordinates.size.height.toFloat()
        columnHeightDp = with(localDensity) { coordinates.size.height.toDp() }
      }
  ) {
    Box(modifier = Modifier.fillMaxWidth().weight(splitRatio)) { topView() }

    Box(
      modifier =
        Modifier.fillMaxWidth()
          .height(handleThickness)
          .background(MaterialTheme.customColors.agentBubbleBgColor)
          .pointerInput(Unit) {
            detectDragGestures { change, dragAmount ->
              val newTopHeightPx = columnHeightPx * splitRatio + dragAmount.y
              var newTopHeightDp = with(localDensity) { newTopHeightPx.toDp() }
              if (newTopHeightDp < minTopHeight) {
                newTopHeightDp = minTopHeight
              }
              if (columnHeightDp - newTopHeightDp < minBottomHeight) {
                newTopHeightDp = columnHeightDp - minBottomHeight
              }
              splitRatio = newTopHeightDp / columnHeightDp
              change.consume()
            }
          },
      contentAlignment = Alignment.Center,
    ) {
      Box(
        modifier =
          Modifier.width(32.dp)
            .height(4.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f))
      )
    }

    Box(modifier = Modifier.fillMaxWidth().weight(1f - splitRatio)) { bottomView() }
  }
}

// @Preview(showBackground = true)
// @Composable
// fun VerticalSplitViewPreview() {
//   GalleryTheme { VerticalSplitView(topView = { Text("top") }, bottomView = { Text("bottom") }) }
// }
