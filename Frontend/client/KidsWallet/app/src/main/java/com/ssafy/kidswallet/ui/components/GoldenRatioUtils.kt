package com.ssafy.kidswallet.ui.components

object GoldenRatioUtils {
    private const val GOLDEN_RATIO = 1.618f

    // 황금비 높이 구하는 함수
    fun goldenHeight(width: Float): Float {
        return width * GOLDEN_RATIO
    }

    // 황금비 너비 구하는 함수
    fun goldenWidth(height: Float): Float {
        return height * GOLDEN_RATIO
    }
}
