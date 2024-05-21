package de.yanneckreiss.cameraxtutorial

import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import de.yanneckreiss.cameraxtutorial.ui.analysis.analysisPhotoScreen
import de.yanneckreiss.cameraxtutorial.ui.theme.JetpackComposeCameraXTutorialTheme

class MainActivity2 : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            JetpackComposeCameraXTutorialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    analysisPhotoScreen()
                }
            }
        }
    }
}
