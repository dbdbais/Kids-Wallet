package com.ssafy.kidswallet.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BlueButton(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
    height: Int = 50,
    elevation: Int = 8,
    textStyle: TextStyle = FontSizes.h16,
    fontWeight: FontWeight = FontWeight.Bold
) {
    Box(
        modifier = modifier
            .shadow(
                elevation = elevation.dp,
                shape = RoundedCornerShape(15.dp)
            )
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(Color(0xFF99DDF8), Color(0xFF6DCEF5))
                ),
                shape = RoundedCornerShape(15.dp)
            )
            .clickable(onClick = onClick)
            .height(height.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = Color.White,
            style = textStyle.copy(fontWeight = fontWeight),
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}

@Composable
fun LightBlueButton(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
    height: Int = 50,
    elevation: Int = 8,
    textStyle: TextStyle = FontSizes.h16,
    fontWeight: FontWeight = FontWeight.Bold
) {
    Box(
        modifier = modifier
            .shadow(
                elevation = elevation.dp,
                shape = RoundedCornerShape(15.dp)
            )
            .background(
                Color(0xFFE9F8FE),
                shape = RoundedCornerShape(15.dp)
            )
            .clickable(onClick = onClick)
            .height(height.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = Color(0xFF6DCEF5),
            style = textStyle.copy(fontWeight = fontWeight),
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}

@Composable
fun GrayButton(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
    height: Int = 50,
    elevation: Int = 8,
    textStyle: TextStyle = FontSizes.h16,
    fontWeight: FontWeight = FontWeight.Bold
) {
    Box(
        modifier = modifier
            .shadow(
                elevation = elevation.dp,
                shape = RoundedCornerShape(15.dp)
            )
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(Color(0xFFA0A0A0), Color(0xFF8C8595))
                ),
                shape = RoundedCornerShape(15.dp)
            )
            .clickable(onClick = onClick)
            .height(height.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = Color.White,
            style = textStyle.copy(fontWeight = fontWeight),
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}

@Composable
fun LightGrayButton(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
    height: Int = 50,
    elevation: Int = 8,
    textStyle: TextStyle = FontSizes.h16,
    fontWeight: FontWeight = FontWeight.Bold
) {
    Box(
        modifier = modifier
            .shadow(
                elevation = elevation.dp,
                shape = RoundedCornerShape(15.dp)
            )
            .background(
                Color(0xFFF7F7F7),
                shape = RoundedCornerShape(15.dp)
            )
            .clickable(onClick = onClick)
            .height(height.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = Color(0xFF8C8595),
            style = textStyle.copy(fontWeight = fontWeight),
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}

@Composable
fun YellowButton(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
    height: Int = 50,
    elevation: Int = 8,
    textStyle: TextStyle = FontSizes.h16,
    fontWeight: FontWeight = FontWeight.Bold
) {
    Box(
        modifier = modifier
            .shadow(
                elevation = elevation.dp,
                shape = RoundedCornerShape(15.dp)
            )
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(Color(0xFFFFD262), Color(0xFFFBC02D))
                ),
                shape = RoundedCornerShape(15.dp)
            )
            .clickable(onClick = onClick)
            .height(height.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = Color.White,
            style = textStyle.copy(fontWeight = fontWeight),
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}

@Composable
fun GreenButton(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
    height: Int = 50,
    elevation: Int = 8,
    textStyle: TextStyle = FontSizes.h16,
    fontWeight: FontWeight = FontWeight.Bold
) {
    Box(
        modifier = modifier
            .shadow(
                elevation = elevation.dp,
                shape = RoundedCornerShape(15.dp)
            )
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(Color(0xFFA1E9A1), Color(0xFF77DD77))
                ),
                shape = RoundedCornerShape(15.dp)
            )
            .clickable(onClick = onClick)
            .height(height.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = Color.White,
            style = textStyle.copy(fontWeight = fontWeight),
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}

@Composable
fun PinkButton(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
    height: Int = 50,
    elevation: Int = 8,
    textStyle: TextStyle = FontSizes.h16,
    fontWeight: FontWeight = FontWeight.Bold
) {
    Box(
        modifier = modifier
            .shadow(
                elevation = elevation.dp,
                shape = RoundedCornerShape(15.dp)
            )
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(Color(0xFFFFA1B0), Color(0xFFFF8396))
                ),
                shape = RoundedCornerShape(15.dp)
            )
            .clickable(onClick = onClick)
            .height(height.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = Color.White,
            style = textStyle.copy(fontWeight = fontWeight),
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}


