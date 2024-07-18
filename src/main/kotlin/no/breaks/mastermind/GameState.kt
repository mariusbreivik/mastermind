package no.breaks.mastermind

data class GameState(
    val secretCode: List<Color>,
    val maxAttempts: Int = 10,
    var attempts: Int = 0,
    var isGameOver: Boolean = false,
    var isWon: Boolean = false
)
