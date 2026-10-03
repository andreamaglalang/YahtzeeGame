package com.example.yahtzeegame

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun YahtzeeScreen(
    viewModel: YahtzeeViewModel
) {

    Column(
        modifier = Modifier.padding(16.dp)
    ) {

        Text(
            text = "Yahtzee"
        )

        Row {

            viewModel.diceValues.forEachIndexed { index, value ->

                Die(
                    value = value,
                    isHeld = viewModel.heldDice[index],
                    onClick = {

                        viewModel.toggleHold(index)
                    }
                )
            }
        }

        Text(
            text = "Roll ${viewModel.rollCount} of ${viewModel.maxRolls}"
        )

        Button(
            onClick = {

                viewModel.rollWithCoroutine()
            },
            enabled = viewModel.rollCount < viewModel.maxRolls
        ) {

            Text("Roll")
        }

        Button(
            onClick = {

                viewModel.newTurn()
            }
        ) {

            Text("New Turn")
        }

        YahtzeeCategory.entries.forEach { category ->

            val isUsed = category in viewModel.usedCategories

            val availableScore = viewModel.categoryScores
                .firstOrNull { it.category == category }
                ?.score

            val savedScore = viewModel.savedScores[category]

            Button(
                onClick = {

                    viewModel.chooseCategory(category)
                },
                enabled = !isUsed && viewModel.rollCount > 0
            ) {

                Text(
                    text = when {
                        isUsed -> "$category: $savedScore"
                        availableScore != null -> "$category: $availableScore"
                        else -> "$category: -"
                    }
                )
            }
        }

        Text(
            text = "Total: ${viewModel.totalScore}"
        )

        if (viewModel.isGameOver) {

            Text(
                text = "Game Over!"
            )
        }
    }
}

@Composable
fun Die(
    value: Int,
    isHeld: Boolean,
    onClick: () -> Unit
) {

    val diceImage = when (value) {

        1 -> R.drawable.dice_1
        2 -> R.drawable.dice_2
        3 -> R.drawable.dice_3
        4 -> R.drawable.dice_4
        5 -> R.drawable.dice_5
        6 -> R.drawable.dice_6

        else -> R.drawable.dice_1
    }

    Box(
        modifier = Modifier
            .padding(2.dp)
            .border(
                width = if (isHeld) 3.dp else 0.dp,
                color = if (isHeld) Color.Red else Color.Transparent
            )
            .clickable {

                onClick()
            }
    ) {

        Image(
            painter = painterResource(
                id = diceImage
            ),
            contentDescription = "Dice showing $value",
            modifier = Modifier.size(64.dp)
        )
    }
}