package com.sharathkumar.ktor2

import android.app.DownloadManager.Query
import android.os.Bundle
import android.os.MessageQueue
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.sharathkumar.ktor2.ui.theme.Ktor2Theme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Ktor2Theme {
                ImagesScreen()

            }
        }
    }
}

class ImageViewModel : ViewModel(){
    val response: MyData = MyData()
    private val _images = MutableStateFlow<List<ImageResult>>(emptyList())
    val images: StateFlow<List<ImageResult>> = _images

    fun getImages(query: String){
        viewModelScope.launch {
            val result = response.fetchImages(query)
            if(result!=null){
                _images.value = result
            }
        }
    }
}

@Composable
fun ImagesScreen(){
    val viewModel: ImageViewModel = viewModel()
    val images by viewModel.images.collectAsState()
    var query by remember { mutableStateOf("") }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp), verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.CenterHorizontally) {
        TextField(value = query, onValueChange = { query =it}, modifier = Modifier.fillMaxWidth())
        Button(onClick = { viewModel.getImages(query) }) {
            Text("Search")
        }
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(images){ image ->
                Image(painter = rememberAsyncImagePainter(image.webformatURL),
                    contentDescription =image.tags ,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(8.dp),
                    contentScale = ContentScale.Crop)
            }
        }
    }
}
