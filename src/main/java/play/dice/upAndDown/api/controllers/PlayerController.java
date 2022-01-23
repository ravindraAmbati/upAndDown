package play.dice.upAndDown.api.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import play.dice.upAndDown.api.model.PlayerVo;
import play.dice.upAndDown.api.services.PlayerService;
import play.dice.upAndDown.db.entities.Player;

@Slf4j
@RestController
@RequestMapping("/player")
public class PlayerController extends BaseController {

    @Autowired
    private PlayerService playerService;

    @PutMapping(value = "/create",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public Player createPlayer(@RequestBody PlayerVo playerVo){
        return playerService.createPlayer(playerVo);
    }
}
