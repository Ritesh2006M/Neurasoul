// /gallery/ui/common/chat/DataCard.kt

package com.google.ai.edge.gallery.ui.common.chat

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.ai.edge.gallery.ui.theme.GalleryTheme
import com.google.ai.edge.gallery.ui.theme.bodySmallMediumNarrow
import com.google.ai.edge.gallery.ui.theme.bodySmallMediumNarrowBold
import com.google.ai.edge.gallery.ui.theme.labelSmallNarrow
import com.google.ai.edge.gallery.ui.theme.labelSmallNarrowMedium

/**
 * Composable function to display a data card with a label and a numeric value.
 *
 * This function renders a column containing a label and a formatted numeric value. It provides
 * options for highlighting the value and displaying a placeholder when the value is not available.
 */
@Composable
fun DataCard(
  label: String,
  value: Float?,
  unit: String,
  highlight: Boolean = false,
  showPlaceholder: Boolean = false,
) {
  var strValue = "-"
  Column {
    Text(label, style = labelSmallNarrowMedium)
    if (showPlaceholder) {
      Text("-", style = bodySmallMediumNarrow)
    } else {
      strValue = if (value == null) "-" else "%.2f".format(value)
      if (highlight) {
        Text(strValue, style = bodySmallMediumNarrowBold, color = MaterialTheme.colorScheme.primary)
      } else {
        Text(strValue, style = bodySmallMediumNarrow)
      }
    }
    if (strValue != "-") {
      Text(unit, style = labelSmallNarrow, modifier = Modifier.alpha(0.5f).offset(y = (-1).dp))
    }
  }
}

@Preview(showBackground = true)
@Composable
fun DataCardPreview() {
  GalleryTheme {
    Row(modifier = Modifier.padding(16.dp), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
      DataCard(
        label = "sum",
        value = 123.45f,
        unit = "ms",
        highlight = true,
        showPlaceholder = false,
      )
      DataCard(
        label = "average",
        value = 12.3f,
        unit = "ms",
        highlight = false,
        showPlaceholder = false,
      )
      DataCard(
        label = "test",
        value = null,
        unit = "ms",
        highlight = false,
        showPlaceholder = false,
      )
    }
  }
}
