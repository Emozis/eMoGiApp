package com.meta.emogi.domain.auth.entity


sealed interface StartDestination {
    data object Go2Main : StartDestination
    data object Go2Login : StartDestination
}