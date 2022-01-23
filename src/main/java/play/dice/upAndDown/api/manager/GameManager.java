package play.dice.upAndDown.api.manager;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import play.dice.upAndDown.api.model.PlayerVo;
import play.dice.upAndDown.db.GameStatusEnum;
import play.dice.upAndDown.db.entities.Game;
import play.dice.upAndDown.db.entities.Player;
import play.dice.upAndDown.db.repos.GameRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class GameManager {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private PlayerManager playerManager;

    public Game createGame(Player player) {
        if (!ObjectUtils.isEmpty(player)) {
            Game game = Game.builder()
                    .players(List.of(player))
                    .firstPlayerId(player.getPlayerId())
                    .gameStatusEnum(GameStatusEnum.NOT_STARTED)
                    .build();
            String name = player.getName();
            game.setCreatedBy(name);
            game.setCreatedDate(LocalDate.now());
            game.setUpdatedBy(name);
            game.setUpdatedDate(LocalDate.now());
            return gameRepository.save(game);
        } else {
            log.error("player is missing/empty : {}", player);
        }
        return null;
    }

    public boolean isGameExists(Long gameId) {
        return gameRepository.existsById(gameId);
    }

    public Game getGame(Long gameId) {
        return gameRepository.getById(gameId);
    }

    public Game joinGame(Game game, Player player) {
        List<Player> players = game.getPlayers();
        log.info("The existing players: {} of the game: {}", players, game);
        players.add(player);
        game.setPlayers(players);
        return gameRepository.save(game);
    }

    public List<PlayerVo> getPlayers(Long gameId) {
        List<PlayerVo> players = new ArrayList<>();
        Game game = gameRepository.getById(gameId);
        game.getPlayers().forEach(
                player -> {
                    PlayerVo playerVo = PlayerVo.builder().build();
                    BeanUtils.copyProperties(player,playerVo);
                    players.add(playerVo);
                }
        );
        return players;
    }

    public Game save(Game game) {
        return gameRepository.save(game);
    }
}
