package play.dice.upAndDown.db.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import play.dice.upAndDown.api.model.RoundVo;
import play.dice.upAndDown.db.entities.Round;

import java.util.List;

public interface RoundRepository extends JpaRepository<Round, Long> {
    Round findByGameIdAndPlayerId(Long gameId, Long playerId);

    List<Round> findByGameId(Long gameId);
}