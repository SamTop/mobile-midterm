package com.example.midterm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Button
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.midterm.ui.theme.Grey
import com.example.midterm.ui.theme.MidtermTheme
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MidtermTheme {
                Main()
            }
        }
    }

    @Composable
    private fun Main() {
        MidtermTheme {
            Surface(
                modifier = Modifier.fillMaxSize(),
            ) {
                Column (verticalArrangement = Arrangement.Center) {
                    Title()
                    Game()
                }
            }
        }
    }

    @Composable
    private fun Title() {
        Column (modifier = Modifier.height(20.dp), verticalArrangement = Arrangement.Center) {
            Text(
                text = getString(R.string.app_name),
            )
        }
    }

    @Composable
    private fun Game() {
        val target = remember{ mutableStateOf(getRandomNum()) }
        val sliderValue = remember{ mutableStateOf(0f) }
        val totalScore = remember { mutableStateOf(0) }
        val score = remember { mutableStateOf(0) }

        val label = remember { mutableStateOf("") }

        Column (modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = getString(R.string.objective) + target.value.toString(),
                modifier = Modifier.padding(0.dp, 50.dp)
            )
            Slider(
                value = sliderValue.value,
                valueRange = 0f..100f,
                modifier = Modifier.padding(10.dp, 70.dp),
                onValueChange = {
                    sliderValue.value = it
                })
            Button(
                onClick = {
                    setScore(score, sliderValue, target)
                    setLabel(label, score)
                    target.value = getRandomNum()
                    sliderValue.value = 50f
                    totalScore.value += score.value
                },
                modifier = Modifier.padding(0.dp, 50.dp)
            ) {
                Text(text = getString(R.string.button_name))
            }
            Text(
                text = getString(R.string.score) + totalScore.value,
                modifier = Modifier.padding(0.dp, 40.dp)
            )
            Column (modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = (label.value),
                    modifier = Modifier.padding(0.dp, 30.dp),
                    color = Grey
                )
            }
        }
    }

    private fun setLabel(label: MutableState<String>, score: MutableState<Int>) {
        if (score.value == 5)
            label.value = getString(R.string.perfect)
        else
            label.value = getString(R.string.try_again)
    }

    private fun setScore(score: MutableState<Int>, current: MutableState<Float>, target: MutableState<Int>) {
        if (-3 <= target.value - current.value && target.value - current.value <= 3) {
            score.value = 5
        } else if (-8 <= target.value - current.value && target.value - current.value <= 8) {
            score.value = 1
        } else {
            score.value = 0
        }
        println(target.value - current.value)
    }

    private fun getRandomNum(): Int {
        return (0..100).shuffled().last()
    }

}
