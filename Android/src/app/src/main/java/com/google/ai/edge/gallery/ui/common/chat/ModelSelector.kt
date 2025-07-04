// /gallery/ui/common/chat/ModelSelector.kt

package com.google.ai.edge.gallery.ui.common.chat

// import androidx.compose.ui.tooling.preview.Preview
// import com.google.ai.edge.gallery.ui.preview.PreviewModelManagerViewModel
// import com.google.ai.edge.gallery.ui.preview.TASK_TEST1
// import com.google.ai.edge.gallery.ui.preview.TASK_TEST2
// import com.google.ai.edge.gallery.ui.theme.GalleryTheme

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.ai.edge.gallery.data.Model
import com.google.ai.edge.gallery.data.Task
import com.google.ai.edge.gallery.data.convertValueToTargetType
import com.google.ai.edge.gallery.ui.common.ConfigDialog
import com.google.ai.edge.gallery.ui.common.modelitem.ModelItem
import com.google.ai.edge.gallery.ui.modelmanager.ModelManagerViewModel

/**
 * Composable function to display a selectable model item with an option to configure its settings.
 */
@Composable
fun ModelSelector(
  model: Model,
  task: Task,
  modelManagerViewModel: ModelManagerViewModel,
  modifier: Modifier = Modifier,
  contentAlpha: Float = 1f,
  onConfigChanged: (oldConfigValues: Map<String, Any>, newConfigValues: Map<String, Any>) -> Unit =
    { _, _ ->
    },
) {
  var showConfigDialog by remember { mutableStateOf(false) }
  val context = LocalContext.current

  Column(modifier = modifier) {
    Box(
      modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
      contentAlignment = Alignment.Center,
    ) {
      // Model row.
      Row(
        modifier = Modifier.fillMaxWidth().graphicsLayer { alpha = contentAlpha },
        verticalAlignment = Alignment.CenterVertically,
      ) {
        ModelItem(
          model = model,
          task = task,
          modelManagerViewModel = modelManagerViewModel,
          onModelClicked = {},
          onConfigClicked = { showConfigDialog = true },
          verticalSpacing = 10.dp,
          modifier = Modifier.weight(1f).padding(horizontal = 16.dp),
          showDeleteButton = false,
          showConfigButtonIfExisted = true,
          canExpand = false,
        )
      }
    }
  }

  // Config dialog.
  if (showConfigDialog) {
    ConfigDialog(
      title = "Model configs",
      configs = model.configs,
      initialValues = model.configValues,
      onDismissed = { showConfigDialog = false },
      onOk = { curConfigValues ->
        // Hide config dialog.
        showConfigDialog = false

        // Check if the configs are changed or not. Also check if the model needs to be
        // re-initialized.
        var same = true
        var needReinitialization = false
        for (config in model.configs) {
          val key = config.key.label
          val oldValue =
            convertValueToTargetType(
              value = model.configValues.getValue(key),
              valueType = config.valueType,
            )
          val newValue =
            convertValueToTargetType(
              value = curConfigValues.getValue(key),
              valueType = config.valueType,
            )
          if (oldValue != newValue) {
            same = false
            if (config.needReinitialization) {
              needReinitialization = true
            }
            break
          }
        }
        if (same) {
          return@ConfigDialog
        }

        // Save the config values to Model.
        val oldConfigValues = model.configValues
        model.configValues = curConfigValues

        // Force to re-initialize the model with the new configs.
        if (needReinitialization) {
          modelManagerViewModel.initializeModel(
            context = context,
            task = task,
            model = model,
            force = true,
          )
        }

        // Notify.
        onConfigChanged(oldConfigValues, model.configValues)
      },
    )
  }
}

// @Preview(showBackground = true)
// @Composable
// fun ModelSelectorPreview() {
//   GalleryTheme {
//     Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
//       ModelSelector(
//         model = TASK_TEST1.models[0],
//         task = TASK_TEST1,
//         modelManagerViewModel = PreviewModelManagerViewModel(context = LocalContext.current),
//       )
//       ModelSelector(
//         model = TASK_TEST1.models[1],
//         task = TASK_TEST1,
//         modelManagerViewModel = PreviewModelManagerViewModel(context = LocalContext.current),
//       )
//       ModelSelector(
//         model = TASK_TEST2.models[1],
//         task = TASK_TEST2,
//         modelManagerViewModel = PreviewModelManagerViewModel(context = LocalContext.current),
//       )
//     }
//   }
// }
