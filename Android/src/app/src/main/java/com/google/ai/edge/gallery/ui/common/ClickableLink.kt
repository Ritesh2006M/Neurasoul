// /gallery/ui/common/ClickableLink.kt

package com.google.ai.edge.gallery.ui.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.google.ai.edge.gallery.ui.theme.customColors

@Composable
fun ClickableLink(url: String, linkText: String, icon: ImageVector) {
  val uriHandler = LocalUriHandler.current
  val annotatedText =
    AnnotatedString(
      text = linkText,
      spanStyles =
        listOf(
          AnnotatedString.Range(
            item =
              SpanStyle(
                color = MaterialTheme.customColors.linkColor,
                textDecoration = TextDecoration.Underline,
              ),
            start = 0,
            end = linkText.length,
          )
        ),
    )

  Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
    Icon(icon, contentDescription = "", modifier = Modifier.size(16.dp))
    Text(
      text = annotatedText,
      textAlign = TextAlign.Center,
      style = MaterialTheme.typography.bodyLarge,
      modifier = Modifier.padding(start = 6.dp).clickable { uriHandler.openUri(url) },
    )
  }
}
