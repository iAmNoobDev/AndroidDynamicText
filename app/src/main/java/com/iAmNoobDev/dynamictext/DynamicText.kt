package com.iAmNoobDev.dynamictext

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

enum class DtFontWeight {
    SEMI_BOLD, BOLD, EXTRA_BOLD, MEDIUM, REGULAR
}

enum class DtTextDecoration {
    LINE_THROUGH, UNDER_LINE, NONE
}

enum class DtFontStyle {
    NORMAL, ITALICS
}

data class DynamicText(
    val text: String?,
    val fieldStyles: List<DynamicTextStyle>?,
)

data class DynamicTextStyle(
    val color: Long?,
    val fontWeight: DtFontWeight?,
    val fontStyle: DtFontStyle?,
    val fontSize: Int?,
    val startIndex: Int?,
    val endIndex: Int?,
    val dtTextDecorations: List<DtTextDecoration>?
)

fun DynamicText.toDynamicText(
    fontSize: TextUnit = 12.sp,
    color: Color = Color.Black,
    fontWeight: FontWeight = FontWeight.Normal,
    fontStyle: FontStyle = FontStyle.Normal,
    fontFamily: FontFamily? = null,
    textDecoration: TextDecoration = TextDecoration.None
) = buildAnnotatedString {
    text?.let {
        append(it)
        fieldStyles?.let { styles ->
            styles.forEach { style ->
                if (style.startIndex != null && style.startIndex >= 0 && style.endIndex != null && style.endIndex <= text.length) {
                    val dtTextDecoration: List<TextDecoration> = style.dtTextDecorations?.let { decorations ->
                        decorations.map { decoration ->
                            when (decoration) {
                                DtTextDecoration.LINE_THROUGH -> TextDecoration.LineThrough
                                DtTextDecoration.UNDER_LINE -> TextDecoration.Underline
                                DtTextDecoration.NONE -> TextDecoration.None
                            }
                        }
                    } ?: listOf(textDecoration)
                    addStyle(
                        style = SpanStyle(
                            fontFamily = fontFamily,
                            fontWeight = when (style.fontWeight) {
                                DtFontWeight.SEMI_BOLD -> FontWeight.SemiBold
                                DtFontWeight.BOLD -> FontWeight.Bold
                                DtFontWeight.EXTRA_BOLD -> FontWeight.ExtraBold
                                DtFontWeight.REGULAR -> FontWeight.Normal
                                DtFontWeight.MEDIUM -> FontWeight.Medium
                                else -> fontWeight
                            },
                            fontStyle = when (style.fontStyle) {
                                DtFontStyle.ITALICS -> FontStyle.Italic
                                DtFontStyle.NORMAL -> FontStyle.Normal
                                else -> fontStyle
                            },
                            fontSize = style.fontSize?.sp ?: fontSize,
                            color = style.color?.let { color -> Color(color) } ?: color,
                            textDecoration = TextDecoration.combine(dtTextDecoration)
                        ),
                        start = style.startIndex,
                        end = style.endIndex
                    )
                }
            }
        }
            ?: addStyle(
                style = SpanStyle(
                    fontFamily = fontFamily,
                    fontStyle = fontStyle,
                    fontWeight = fontWeight,
                    fontSize = fontSize,
                    color = color,
                    textDecoration = textDecoration
                ),
                start = 0,
                end = text.length
            )
    }
}