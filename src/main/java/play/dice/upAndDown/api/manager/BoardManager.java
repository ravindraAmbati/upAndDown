package play.dice.upAndDown.api.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import play.dice.upAndDown.api.model.PlayerVo;
import play.dice.upAndDown.db.entities.Board;
import play.dice.upAndDown.db.repos.BoardRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
public class BoardManager {

    @Autowired
    private BoardRepository boardRepository;

    public Board getBoard(Long gameId, Long playerId) {
        return boardRepository.findByGameIdAndPlayerId(gameId, playerId);
    }

    public Board createBoard(Board board) {
        return boardRepository.save(board);
    }

    public Map<PlayerVo, Integer> getPlayersPrevPositionMap(Long gameId, List<PlayerVo> players) {
        Map<PlayerVo, Integer> playersPositionMap = new HashMap<>();
        List<Board> boards = boardRepository.findAllByGameId(gameId);
        players.forEach(
                playerVo -> {
                    Optional<Board> boardByPlayer = boards.stream().filter(board -> board.getPlayerId().equals(playerVo.getPlayerId())).findFirst();
                    boardByPlayer.ifPresent(board -> {
                        playersPositionMap.put(playerVo, board.getPrevPosition());
                    });
                }
        );
        return playersPositionMap;
    }

    public Map<PlayerVo, Integer> getPlayersPositionMap(Long gameId, List<PlayerVo> players) {
        Map<PlayerVo, Integer> playersPositionMap = new HashMap<>();
        List<Board> boards = boardRepository.findAllByGameId(gameId);
        players.forEach(
                playerVo -> {
                    Optional<Board> boardByPlayer = boards.stream().filter(board -> board.getPlayerId().equals(playerVo.getPlayerId())).findFirst();
                    boardByPlayer.ifPresent(board -> {
                        playersPositionMap.put(playerVo, board.getCurrentPosition());
                    });
                }
        );
        return playersPositionMap;
    }
}
