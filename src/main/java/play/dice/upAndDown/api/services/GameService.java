package play.dice.upAndDown.api.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import play.dice.upAndDown.BoardStrategy;
import play.dice.upAndDown.api.manager.BoardManager;
import play.dice.upAndDown.api.manager.GameManager;
import play.dice.upAndDown.api.manager.PlayerManager;
import play.dice.upAndDown.api.manager.RoundManager;
import play.dice.upAndDown.api.model.BoardVo;
import play.dice.upAndDown.api.model.GameVo;
import play.dice.upAndDown.api.model.PlayerVo;
import play.dice.upAndDown.api.model.RoundVo;
import play.dice.upAndDown.db.GameStatusEnum;
import play.dice.upAndDown.db.entities.Board;
import play.dice.upAndDown.db.entities.Game;
import play.dice.upAndDown.db.entities.Player;
import play.dice.upAndDown.db.entities.Round;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Slf4j
@Service
public class GameService {

    @Autowired
    private PlayerManager playerManager;

    @Autowired
    private GameManager gameManager;

    @Autowired
    private RoundManager roundManager;

    @Autowired
    private BoardManager boardManager;

    public GameVo createGame(Long playerId) {
        Game game = null;
        if (playerManager.isPlayerExists(playerId)) {
            final Player player = playerManager.getPlayer(playerId);
            log.info("Player details are below by given playerId: {}, player details: {}", playerId, player);
            game = gameManager.createGame(player);
            log.info("Game has been created successfully with given playerId: {} and game: {}", playerId, game);
        } else {
            log.error("player doesn't exists with given playerId: {}", playerId);
        }
        List<PlayerVo> playerVos = getPlayerVos(game);
        return GameVo.builder()
                .gameId(game.getGameId())
                .playerVos(playerVos)
                .gameStatusEnum(game.getGameStatusEnum().name())
                .build();
    }

    private List<PlayerVo> getPlayerVos(Game game) {
        List<PlayerVo> playerVos = new ArrayList<>();
        assert game != null;
        game.getPlayers().forEach(
                player -> {
                    PlayerVo playerVo = PlayerVo.builder().build();
                    BeanUtils.copyProperties(player,playerVo);
                    playerVos.add(playerVo);
                }

        );
        return playerVos;
    }

    public GameVo joinGame(Long gameId, Long playerId) {
        GameVo gameVo = GameVo.builder().build();
        Game game = Game.builder().build();
        Player player = Player.builder().build();
        if (gameManager.isGameExists(gameId)) {
            game = gameManager.getGame(gameId);
        } else {
            log.error("game doesn't exists with given gameId: {}", gameId);
        }
        if (playerManager.isPlayerExists(playerId)) {
            player = playerManager.getPlayer(playerId);
        } else {
            log.error("player doesn't exists with given playerId: {}", playerId);
        }
        if (!ObjectUtils.isEmpty(game) && !ObjectUtils.isEmpty(player)) {
            if (!GameStatusEnum.NOT_STARTED.equals(game.getGameStatusEnum())) {
                log.error("PLayer: {} can't join the game because game status: {}", player, game.getGameStatusEnum());
                return gameVo;
            }
            if (playerId.equals(game.getFirstPlayerId())) {
                log.error("Player: {} already created and joined the game, can't join again to game: {}", player, game);
                return null;
            }
            game = gameManager.joinGame(game, player);
        }
        List<PlayerVo> playerVos = getPlayerVos(game);
        return GameVo.builder()
                .gameId(game.getGameId())
                .playerVos(playerVos)
                .gameStatusEnum(game.getGameStatusEnum().name())
                .build();
    }


    public BoardVo play(Long gameId, Long playerId) {
        Game game = Game.builder().build();
        BoardVo emptyBoardVo = BoardVo.builder().build();
        if (gameManager.isGameExists(gameId) && playerManager.isPlayerExists(playerId)) {
            game = gameManager.getGame(gameId);
            if (game.getFirstPlayerId().equals(playerId) && GameStatusEnum.NOT_STARTED.equals(game.getGameStatusEnum())) {
                game.setGameStatusEnum(GameStatusEnum.IN_PROGRESS);
                game = gameManager.save(game);
            }
            if (!game.getFirstPlayerId().equals(playerId) && GameStatusEnum.NOT_STARTED.equals(game.getGameStatusEnum())) {
                log.error("the playerId: {} can't start the game: {} but only firstPlayerId: {}", playerId, game, game.getFirstPlayerId());
                return emptyBoardVo;
            }
            if (GameStatusEnum.IN_PROGRESS.equals(game.getGameStatusEnum())) {
                final Round round = createRound(gameId, playerId);
                final Integer currentPoints = round.getCurrentPoints();
                BoardVo boardVo = createBoard(gameId, playerId, currentPoints);
                final Long boardGameId = boardVo.getGameId();
                final List<PlayerVo> players = gameManager.getPlayers(boardGameId);
                final Map<PlayerVo, Integer> playerVoPrevPositionMap = boardManager.getPlayersPrevPositionMap(boardGameId, players);
                final Map<PlayerVo, Integer> playerVoPositionMap = boardManager.getPlayersPositionMap(boardGameId, players);
                final List<Round> rounds = roundManager.getRounds(boardGameId);
                boardVo.setGameId(boardGameId);
                boardVo.setPlayerVoPrevPositionMap(playerVoPrevPositionMap);
                boardVo.setPlayerVoPositionMap(playerVoPositionMap);
                List<RoundVo> roundVos = List.of(RoundVo.builder().build());
                BeanUtils.copyProperties(rounds,roundVos);
                boardVo.setRoundVoList(roundVos);
                return boardVo;
            }
            return emptyBoardVo;
        }
        return emptyBoardVo;
    }

    private BoardVo createBoard(Long gameId, Long playerId, Integer currentPoints) {
        Board board = boardManager.getBoard(gameId, playerId);
        if (!ObjectUtils.isEmpty(board)) {
            Integer prevPosition = board.getCurrentPosition();
            final Integer currentPosition = BoardStrategy.getActualPosition(prevPosition, currentPoints);
            board.setCurrentPosition(currentPosition);
            board.setPrevPosition(prevPosition);
        } else {
            board = Board.builder()
                    .gameId(gameId)
                    .playerId(playerId)
                    .prevPosition(0)
                    .currentPosition(currentPoints)
                    .build();
        }
        BoardVo boardVo = BoardVo.builder().build();
        board = boardManager.createBoard(board);
        BeanUtils.copyProperties(board, boardVo);
        return boardVo;
    }

    private Round createRound(Long gameId, Long playerId) {
        Random random = new Random();
        Integer currentPoints = random.nextInt(6);
        Round round = roundManager.getRound(gameId, playerId);
        if (!ObjectUtils.isEmpty(round)) {
            Integer prevPoints = round.getCurrentPoints();
            round.setPrevPoints(prevPoints);
            round.setCurrentPoints(currentPoints);
        } else {
            round = Round.builder()
                    .gameId(gameId)
                    .playerId(playerId)
                    .prevPoints(0)
                    .currentPoints(currentPoints)
                    .build();
        }
        return roundManager.createRound(round);
    }
}
