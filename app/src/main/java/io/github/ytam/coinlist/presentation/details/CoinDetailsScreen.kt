package io.github.ytam.coinlist.presentation.details

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionResult
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import io.github.ytam.coinlist.R
import io.github.ytam.coinlist.component.CoinDetailItem
import java.text.SimpleDateFormat

@Composable
fun CoinDetailsScreen(
    navController: NavController,
    viewModel: CoinDetailsViewModel = hiltViewModel()
) {
    val state = viewModel.state.value

    Column {
        TopAppBar(
            title =
            {
                state.data?.let {
                    Text(text = it.name)
                }
            },
            navigationIcon = {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(Icons.Filled.KeyboardArrowLeft, "")
                }
            },
            contentColor = Color.White,
            elevation = 12.dp
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {

            state.data?.let { coin ->
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(20.dp)
                ) {

                    item {

                        CoinDetailItem("Started at: ", convertDateString(coin.firstDataDate) )
                        Spacer(modifier = Modifier.height(15.dp))
                        CoinDetailItem("Last Date at: ", convertDateString(coin.lastDataDate) )

                        Spacer(modifier = Modifier.height(15.dp))
                        Text(
                            text = coin.description,
                            style = MaterialTheme.typography.body1
                        )
                        Spacer(modifier = Modifier.height(15.dp))
                    }
                }
            }
            if (state.errorMessage.isNotBlank()) {
                Text(
                    text = state.errorMessage,
                    color = MaterialTheme.colors.error,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .align(Alignment.Center)
                )
            }
            if (state.isLoading) {

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {

                    val lottieCompositionResult: LottieCompositionResult =
                        rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loading))

                    val progress by animateLottieCompositionAsState(
                        lottieCompositionResult.value,
                        isPlaying = true,
                        iterations = LottieConstants.IterateForever,
                        speed = 1.0f
                    )

                    LottieAnimation(
                        composition = lottieCompositionResult.value,
                        progress = progress,
                        modifier = Modifier.padding(all = 30.dp)
                    )
                }

            }
        }
    }
}

@SuppressLint("SimpleDateFormat")
fun convertDateString(dateString: String): String {
    val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    val formatter = SimpleDateFormat("dd.MM.yyyy")
    return formatter.format(parser.parse(dateString))
}
