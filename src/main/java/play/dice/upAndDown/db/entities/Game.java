package play.dice.upAndDown.db.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import play.dice.upAndDown.db.GameStatusEnum;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
@Data
@Entity
@Table(name = "game")
public class Game extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "gameId", nullable = false)
    private Long gameId;
    @OneToMany(targetEntity = Player.class, mappedBy = "playerId", fetch = FetchType.EAGER)
    private List<Player> players;
    private Long firstPlayerId;
    private GameStatusEnum gameStatusEnum;
}