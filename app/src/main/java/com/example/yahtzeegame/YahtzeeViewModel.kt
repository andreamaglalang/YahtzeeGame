package com.example.yahtzeegame

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class YahtzeeViewModel : ViewModel() {

    var diceValues by mutableStateOf(
        listOf(1, 1, 1, 1, 1)
    )
        private set

    var heldDice by mutableStateOf(
        List(5) { false }
    )
        private set

    var categoryScores by mutableStateOf(
        emptyList<CategoryScore>()
    )
        private set

    var usedCategories by mutableStateOf(
        emptySet<YahtzeeCategory>()
    )
        private set

    var savedScores by mutableStateOf(
        emptyMap<YahtzeeCategory, Int>()
    )
        private set

    var rollCount by mutableStateOf(0)
        private set

    val maxRolls: Int = 3

    val totalScore: Int
        get() = savedScores.values.sum()

    val isGameOver: Boolean
        get() = usedCategories.size == YahtzeeCategory.entries.size

    private fun rollDice(): Int {

        return Random.nextInt(1, 7)
    }

    private fun rollRespectingHolds(): List<Int> {

        return List(5) { index ->

            if (heldDice[index]) {
                diceValues[index]
            } else {
                rollDice()
            }
        }
    }

    fun toggleHold(
        index: Int
    ) {

        heldDice = heldDice.toMutableList().also {
            it[index] = !it[index]
        }
    }

    fun rollWithCoroutine() {

        if (rollCount >= maxRolls) return

        rollCount++

        viewModelScope.launch {

            repeat(10) {

                diceValues = rollRespectingHolds()

                delay(100)
            }

            evaluateDice()
        }
    }

    fun chooseCategory(
        category: YahtzeeCategory
    ) {

        if (category in usedCategories) return

        val score = DiceRules.scoreFor(
            category = category,
            dice = diceValues
        )

        savedScores = savedScores + (category to score)

        usedCategories = usedCategories + category

        newTurn()
    }

    fun newTurn() {

        rollCount = 0

        heldDice = List(5) { false }

        diceValues = listOf(1, 1, 1, 1, 1)

        categoryScores = emptyList()
    }

    private fun evaluateDice() {

        categoryScores =
            DiceRules
                .getAvailableCategories(diceValues)
                .map { category ->

                    CategoryScore(
                        category = category,
                        score = DiceRules.scoreFor(
                            category = category,
                            dice = diceValues
                        )
                    )
                }
    }
}