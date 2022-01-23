package play.dice.upAndDown.db.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import play.dice.upAndDown.db.entities.Game;

public interface GameRepository extends JpaRepository<Game, Long> {
}