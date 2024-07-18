package no.breaks.mastermind

import org.springframework.stereotype.Service
import java.util.*

@Service
class GameService {

    fun generateSecretCode(): List<Color> {
        val colors = Color.values().toList()
        return List(4) {colors.random() }
    }

    fun generateGameId(): String {
        return UUID.randomUUID().toString()
    }

    fun evaluateGuess(secretCode: List<Color>, guess: List<Color>): GuessResponse {
        var correctPosition = 0
        var correctColor = 0
        val secretCodeCopy = secretCode.toMutableList()
        val guessCopy = guess.toMutableList()

        // First pass: Check for correct colors in correct positions
        for (i in secretCode.indices) {
            if (guess[i] == secretCode[i]) {
                correctPosition++
                secretCodeCopy[i] = Color.RED // Use any dummy value that won't match real colors
                guessCopy[i] = Color.RED
            }
        }

        // Second pass: Check for correct colors in wrong positions
        for (i in guessCopy.indices) {
            if (guessCopy[i] != Color.RED && guessCopy[i] in secretCodeCopy) {
                correctColor++
                secretCodeCopy[secretCodeCopy.indexOf(guessCopy[i])] = Color.RED
            }
        }

        return GuessResponse(correctPosition, correctColor)
    }
}