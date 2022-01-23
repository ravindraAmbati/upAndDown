package play.dice.upAndDown.api.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import play.dice.upAndDown.api.manager.PlayerManager;
import play.dice.upAndDown.api.model.PlayerVo;
import play.dice.upAndDown.db.entities.Player;

@Slf4j
@Service
public class PlayerService {

    @Autowired
    private PlayerManager playerManager;

    public Player createPlayer(PlayerVo playerVo) {
        if(!ObjectUtils.isEmpty(playerVo) && !ObjectUtils.isEmpty(playerVo.getName())){
            return playerManager.createPlayer(playerVo);
        } else {
            log.error("The given player is invalid: {}",playerVo);
        }
        return null;
    }
}
