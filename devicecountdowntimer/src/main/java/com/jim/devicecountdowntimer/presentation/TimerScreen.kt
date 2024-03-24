package com.jim.devicecountdowntimer.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.activity.viewModels
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jim.devicecountdowntimer.ui.theme.cerapro

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimerScreen() {

    val viewModel: TimerViewModel = viewModel()

    LaunchedEffect(key1 = true, block = {
        viewModel.onEventChange(TimerScreenUiEvent.StartTimer)
    })
    val status = viewModel.state.value

    Scaffold {
        Column(
            modifier = Modifier.padding(10.dp)
        ) {

            Box(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Mkopa Test",
                    Modifier.padding(10.dp),
                    fontFamily = cerapro,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(30.dp))
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(10.dp),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(30.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = status.timerStatus?.name ?: "", fontFamily = cerapro,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(30.dp))

                    Column(
                        modifier = Modifier
                            .background(
                                status.timerStatus?.timerBackgroundColor ?: Color.Green,
                                CircleShape
                            )
                            .border(
                                20.dp,
                                status.timerStatus?.timerBorderColor ?: Color.Gray,
                                shape = RoundedCornerShape(500.dp)
                            )
                            .padding(16.dp)
                            .size(260.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Text(
                            text = "Time left:",
                            fontFamily = cerapro,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            modifier = Modifier.padding(bottom = 20.dp)
                        )

                        Text(
                            text = status.timeRemaining,
                            fontFamily = cerapro,
                            style = MaterialTheme.typography.displaySmall,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )

                    }

                }
            }
            status.timerStatus?.message?.let {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    shape = RoundedCornerShape(10.dp),
                    elevation = CardDefaults.cardElevation(4.dp),
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Icon(
                            Icons.Default.Info,
                            contentDescription = "",
                            modifier = Modifier.size(30.dp)
                        )

                        Text(
                            text = it,
                            modifier = Modifier.padding(10.dp),
                            fontFamily = cerapro,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Normal
                        )
                    }

                }

            }

        }

        when (status.requestsStatus) {
            is TimerRequestsStatus.Loading -> {
                LoadingIndicator()
            }

            is TimerRequestsStatus.Error -> {
                ErrorIndicator("An error occurred while fetching data. Please try again later")
            }

            TimerRequestsStatus.Success -> {}
            null -> {}
        }

    }
}

@Composable
@Preview
fun TimerScreenPreview() {
    TimerScreen()
}