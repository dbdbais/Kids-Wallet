package com.ssafy.kidswallet.ui.components

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.os.Build
import android.provider.MediaStore
import android.util.Base64
import android.net.Uri
import java.io.ByteArrayOutputStream

object ImageUtils {

    fun getBase64FromUri(context: Context, imageUri: Uri): String? {
        return try {
            // Uri를 Bitmap으로 변환
            val bitmap: Bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                val source = ImageDecoder.createSource(context.contentResolver, imageUri)
                ImageDecoder.decodeBitmap(source)
            } else {
                MediaStore.Images.Media.getBitmap(context.contentResolver, imageUri)
            }

            // Bitmap을 ByteArray로 변환
            val byteArrayOutputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
            val byteArray = byteArrayOutputStream.toByteArray()

            // ByteArray를 Base64 문자열로 변환
            Base64.encodeToString(byteArray, Base64.NO_WRAP)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun getBase64FromDrawable(context: Context, drawableResId: Int): String? {
        return try {
            val bitmap = BitmapFactory.decodeResource(context.resources, drawableResId)
            val byteArrayOutputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
            val byteArray = byteArrayOutputStream.toByteArray()
            Base64.encodeToString(byteArray, Base64.NO_WRAP)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}

// 사용 예시
// val base64String = ImageUtils.getBase64FromUri(context, imageUri)