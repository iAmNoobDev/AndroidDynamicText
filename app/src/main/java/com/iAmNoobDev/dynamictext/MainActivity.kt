package com.iAmNoobDev.dynamictext

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.remember

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val sampleText = remember { getString(R.string.sample_text) }
            Text(
                text = DynamicText(
                    text = sampleText,
                    fieldStyles = listOf(
                        DynamicTextStyle(
                            color = 0xFFFF0000,
                            fontWeight = null,
                            fontStyle = null,
                            fontSize = null,
                            startIndex = 0,
                            endIndex = sampleText.length - 1,
                            dtTextDecorations = listOf(
                                DtTextDecoration.LINE_THROUGH,
                                DtTextDecoration.UNDER_LINE
                            )
                        )
                    )
                ).toDynamicText()
            )
        }
    }
}