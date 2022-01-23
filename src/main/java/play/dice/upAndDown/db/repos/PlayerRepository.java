package play.dice.upAndDown.db.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import play.dice.upAndDown.db.entities.Player;

public interface PlayerRepository extends JpaRepository<Player, Long> {
}