package play.dice.upAndDown.api.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import play.dice.upAndDown.api.model.RoundVo;
import play.dice.upAndDown.db.entities.Round;
import play.dice.upAndDown.db.repos.RoundRepository;

import java.util.List;

@Service
public class RoundManager {

    @Autowired
    private RoundRepository roundRepository;

    public Round getRound(Long gameId, Long playerId) {
        return roundRepository.findByGameIdAndPlayerId(gameId, playerId);
    }

    public Round createRound(Round round) {
        return roundRepository.save(round);
    }

    public List<Round> getRounds(Long gameId) {
        return roundRepository.findByGameId(gameId);
    }
}
