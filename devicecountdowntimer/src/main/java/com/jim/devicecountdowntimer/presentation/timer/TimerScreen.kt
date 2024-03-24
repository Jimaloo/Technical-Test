package com.jim.devicecountdowntimer.presentation.timer

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.ui.platform.testTag
import com.jim.devicecountdowntimer.presentation.composables.ErrorIndicator
import com.jim.devicecountdowntimer.presentation.composables.LoadingIndicator
import com.jim.devicecountdowntimer.ui.theme.cerapro

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimerScreen(state: TimerScreenState) {

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
                        text = state.timerStatus?.name ?: "",
                        fontFamily = cerapro,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                    )
                    Spacer(modifier = Modifier.height(30.dp))

                    Column(
                        modifier = Modifier
                            .background(
                                state.timerStatus?.timerBackgroundColor ?: Color.Green,
                                CircleShape
                            )
                            .border(
                                20.dp,
                                state.timerStatus?.timerBorderColor ?: Color.Green,
                                shape = RoundedCornerShape(500.dp)
                            )
                            .padding(16.dp)
                            .size(260.dp).testTag("timer_card"),
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
                            text = state.timeRemaining,
                            fontFamily = cerapro,
                            style = MaterialTheme.typography.displaySmall,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )

                    }

                }
            }
            state.timerStatus?.let {
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

                        Column {
                            Text(
                                text = it.statusMessage,
                                modifier = Modifier.padding(10.dp),
                                fontFamily = cerapro,
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = it.message,
                                modifier = Modifier.padding(start = 10.dp),
                                fontFamily = cerapro,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Normal
                            )
                        }

                    }

                }

            }

        }

        when (state.requestsStatus) {
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

