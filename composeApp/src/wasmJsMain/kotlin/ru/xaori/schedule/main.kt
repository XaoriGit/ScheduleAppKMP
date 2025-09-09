package ru.xaori.schedule

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import kotlinx.browser.document
import kotlinx.browser.window
import org.w3c.dom.HTMLElement

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    val appRoot = document.getElementById("app") ?: error("No #app element in index.html")
    ComposeViewport(appRoot) {
        App()

        LaunchedEffect(Unit) {
            document.getElementById("splash")?.let { el ->
                (el as HTMLElement).classList.add("hidden")

                window.setTimeout({
                    el.remove()
                    null
                }, 350)
            }
        }
    }
}