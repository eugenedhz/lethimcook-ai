package de.yanneckreiss.cameraxtutorial.ui.analysis

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.platform.LocalContext
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import java.io.File
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

@SuppressLint("RememberReturnType")
@Composable
fun analysisPhotoScreen() {
    val folderName = "LetHimCook-App"
    val context = LocalContext.current
    val lastImageUri = getLastImageUri(context, folderName)
    val bitmap = lastImageUri.let { uri ->
        context.contentResolver.openInputStream(uri)?.use {
            BitmapFactory.decodeStream(it).asImageBitmap()
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        if (bitmap != null) {
            Image(
                painter = BitmapPainter(bitmap),
                contentDescription = "Last Image from Gallery",
                modifier = Modifier.scale(0.9F, 0.7F)
            )
        } else {
            Text(text = "Failed to load last image")
        }
    }
}


fun getLastImageUri(context: Context, folderName: String): Uri {
    Log.d(TAG, "---------------------------- FINDING IMAGES ------------------------------------")
    val projection = arrayOf(
        MediaStore.Images.Media._ID,
        MediaStore.Images.Media.DATE_TAKEN
    )

    val imageNames = getAllImageNames(context, folderName)
    for (imageName in imageNames) {
        Log.d(TAG, "Image Name: $imageName")
    }

    val picturesDirectory =
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
    val file = File(picturesDirectory, "$folderName/${imageNames.last()}")

    return Uri.fromFile(file)
}

fun getAllImageNames(context: Context, folderName: String): List<String> {
    val imageNames = mutableListOf<String>()

    val projection = arrayOf(
        MediaStore.Images.Media.DISPLAY_NAME
    )

    val selection = "${MediaStore.Images.Media.BUCKET_DISPLAY_NAME} = ?"
    val selectionArgs = arrayOf(folderName)
    val sortOrder = "${MediaStore.Images.Media.DATE_TAKEN} DESC"

    context.contentResolver.query(
        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
        projection,
        selection,
        selectionArgs,
        sortOrder
    )?.use { cursor ->
        while (cursor.moveToNext()) {
            val nameColumnIndex = cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME)
            val name = cursor.getString(nameColumnIndex)
            imageNames.add(name)
        }
    }

    return imageNames
}