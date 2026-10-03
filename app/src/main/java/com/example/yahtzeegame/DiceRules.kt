package com.example.yahtzeegame

object DiceRules {

    fun countOccurrences(
        dice: List<Int>
    ): Map<Int, Int> {

        return dice
            .groupingBy { it }
            .eachCount()
    }

    fun isThreeOfAKind(
        dice: List<Int>
    ): Boolean {

        val counts = countOccurrences(dice)

        return counts.values.any { it >= 3 }
    }

    fun isFourOfAKind(
        dice: List<Int>
    ): Boolean {

        val counts = countOccurrences(dice)

        return counts.values.any { it >= 4 }
    }

    fun isYahtzee(
        dice: List<Int>
    ): Boolean {

        val counts = countOccurrences(dice)

        return counts.values.any { it == 5 }
    }

    fun isFullHouse(
        dice: List<Int>
    ): Boolean {

        val counts = countOccurrences(dice)

        return counts.values.sorted() == listOf(2, 3)
    }

    fun isLargeStraight(
        dice: List<Int>
    ): Boolean {

        val values = dice.toSet()

        return values == setOf(1, 2, 3, 4, 5) ||
                values == setOf(2, 3, 4, 5, 6)
    }

    fun isSmallStraight(
        dice: List<Int>
    ): Boolean {

        val values = dice.toSet()

        return values.containsAll(
            listOf(1, 2, 3, 4)
        ) ||
                values.containsAll(
                    listOf(2, 3, 4, 5)
                ) ||
                values.containsAll(
                    listOf(3, 4, 5, 6)
                )
    }

    fun scoreFor(
        category: YahtzeeCategory,
        dice: List<Int>
    ): Int {

        return when (category) {

            YahtzeeCategory.THREE_OF_A_KIND -> {

                if (isThreeOfAKind(dice)) {
                    dice.sum()
                } else {
                    0
                }
            }

            YahtzeeCategory.FOUR_OF_A_KIND -> {

                if (isFourOfAKind(dice)) {
                    dice.sum()
                } else {
                    0
                }
            }

            YahtzeeCategory.FULL_HOUSE -> {

                if (isFullHouse(dice)) {
                    25
                } else {
                    0
                }
            }

            YahtzeeCategory.SMALL_STRAIGHT -> {

                if (isSmallStraight(dice)) {
                    30
                } else {
                    0
                }
            }

            YahtzeeCategory.LARGE_STRAIGHT -> {

                if (isLargeStraight(dice)) {
                    40
                } else {
                    0
                }
            }

            YahtzeeCategory.YAHTZEE -> {

                if (isYahtzee(dice)) {
                    50
                } else {
                    0
                }
            }

            YahtzeeCategory.CHANCE -> {

                dice.sum()
            }
        }
    }

    fun getAvailableCategories(
        dice: List<Int>
    ): List<YahtzeeCategory> {

        return YahtzeeCategory.entries.filter { category ->

            scoreFor(
                category = category,
                dice = dice
            ) > 0
        }
    }
}