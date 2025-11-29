package com.andreasmlbngaol.rabin.presentation.component

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.unit.dp
import com.andreasmlbngaol.rabin.presentation.utils.toClipEntry
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun CopyToClipboardButton(
    textToCopy: String,
) {
    val scope = rememberCoroutineScope()
    val clipboard = LocalClipboard.current

    FilledIconButton(
        shapes = IconButtonDefaults.shapes(),
        onClick = {
            scope.launch {
                clipboard.setClipEntry(textToCopy.toClipEntry())
            }
        },
        modifier = Modifier.size(32.dp)
    ) {
        Icon(
            imageVector = Icons.Filled.ContentCopy,
            contentDescription = "Copy ciphertext",
            modifier = Modifier.size(20.dp)
        )
    }

}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun CopyToClipboardButton(
    textToCopy: Long,
) {
    CopyToClipboardButton(textToCopy.toString())
}