package com.google.ai.edge.gallery.data

/** Possible action for app bar. */
enum class AppBarActionType {
  NO_ACTION,
  APP_SETTING,
  DOWNLOAD_MANAGER,
  MODEL_SELECTOR,
  NAVIGATE_UP,
  REFRESH_MODELS,
  REFRESHING_MODELS,
}

class AppBarAction(val actionType: AppBarActionType, val actionFn: () -> Unit)
