# A kotlin implementation of the board game Mastermind
```
This implementation uses Spring Boot to create a REST API for the Mastermind board game, 
providing endpoints to start a game, make guesses, and check the status of the game.
```

## Start Game server
Run `MastermindApplication`

## Start a new game
```shell
$ curl -X POST http://localhost:8080/mastermind/start
```

## Make a guess
```shell
curl -X POST http://localhost:8080/mastermind/{gameId}/guess -H "Content-Type: application/json" -d '{"guess": ["RED", "GREEN", "BLUE", "YELLOW"]}'
```

## Check Game Status:
```shell
curl http://localhost:8080/mastermind/{gameId}/status
```
