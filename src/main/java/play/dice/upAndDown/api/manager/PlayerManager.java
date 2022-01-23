package play.dice.upAndDown.api.manager;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import play.dice.upAndDown.api.model.PlayerVo;
import play.dice.upAndDown.db.entities.Player;
import play.dice.upAndDown.db.repos.PlayerRepository;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
public class PlayerManager {

    @Autowired
    private PlayerRepository playerRepository;

    public boolean isPlayerExists(Long playerId) {
        return playerRepository.existsById(playerId);
    }

    public Player getPlayer(Long playerId) {
        return playerRepository.getById(playerId);
    }

    public Player createPlayer(PlayerVo playerVo) {
        Player player = Player.builder().build();
        BeanUtils.copyProperties(playerVo, player);
        String name = playerVo.getName();
        player.setCreatedBy(name);
        player.setCreatedDate(LocalDate.now());
        player.setUpdatedBy(name);
        player.setUpdatedDate(LocalDate.now());
        return playerRepository.save(player);
    }

    public List<Player> getPlayers(List<Long> playerIds) {
        return playerRepository.findAllById(playerIds);
    }
}
