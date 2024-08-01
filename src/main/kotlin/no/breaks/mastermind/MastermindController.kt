package no.breaks.mastermind

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/mastermind")
class MastermindController(val gameService: GameService) {

    val logger: Logger = LoggerFactory.getLogger(MastermindController::class.java)

    val games = mutableMapOf<String, GameState>()

    @PostMapping("/start")
    fun startGame(): ResponseEntity<Map<String, Any>> {
        val secretCode = gameService.generateSecretCode()
        val gameId = gameService.generateGameId()
        games[gameId] = GameState(secretCode)
        logger.info("a new game created with id: $gameId and secret code: $secretCode")

        return ResponseEntity(mapOf("gameId" to gameId), HttpStatus.CREATED)
    }

    @PostMapping("/{gameId}/guess")
    fun makeGuess(@PathVariable gameId: String, @RequestBody guessRequest: GuessRequest): ResponseEntity<Any> {
        val game = games[gameId] ?: return ResponseEntity("Game not found", HttpStatus.NOT_FOUND)

        if (game.isGameOver) {
            return ResponseEntity("Game is already over", HttpStatus.BAD_REQUEST)
        }

        val feedback = gameService.evaluateGuess(game.secretCode, guessRequest.guess)

        game.attempts++
        if (feedback.correctPosition == 4) {
            game.isWon = true
            game.isGameOver = true
        } else if (game.attempts >= game.maxAttempts) {
            game.isGameOver = true
        }

        return ResponseEntity(feedback, HttpStatus.OK)
    }

    @GetMapping("/{gameId}/status")
    fun getGameStatus(@PathVariable gameId: String): ResponseEntity<Any> {
        val game = games[gameId] ?: return ResponseEntity("Game not found", HttpStatus.NOT_FOUND)
        return ResponseEntity(game, HttpStatus.OK)
    }
}