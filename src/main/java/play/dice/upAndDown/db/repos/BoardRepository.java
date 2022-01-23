package play.dice.upAndDown.db.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import play.dice.upAndDown.db.entities.Board;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    Board findByGameIdAndPlayerId(Long gameId, Long playerId);

    List<Board> findAllByGameId(Long gameId);
}