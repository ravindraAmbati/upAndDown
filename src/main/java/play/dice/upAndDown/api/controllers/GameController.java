package play.dice.upAndDown.api.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import play.dice.upAndDown.api.model.BoardVo;
import play.dice.upAndDown.api.model.GameVo;
import play.dice.upAndDown.api.services.GameService;
import play.dice.upAndDown.db.entities.Board;
import play.dice.upAndDown.db.entities.Game;

@Slf4j
@RestController
@RequestMapping("/game")
public class GameController extends BaseController {

    @Autowired
    private GameService gameService;

    @GetMapping(value = "/create/{playerId}")
    public GameVo createNewGame(@PathVariable Long playerId) {
        return gameService.createGame(playerId);
    }

    @GetMapping(value = "/join/{gameId}/{playerId}")
    public GameVo joinGame(@PathVariable Long gameId, @PathVariable Long playerId) {
        return gameService.joinGame(gameId, playerId);
    }

    @GetMapping(value = "/play/{gameId}/{playerId}")
    public BoardVo play(@PathVariable Long gameId, @PathVariable Long playerId) {
        return gameService.play(gameId, playerId);
    }
}
