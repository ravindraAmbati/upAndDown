package play.dice.upAndDown.db.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
@Data
@Entity
@Table(name = "round")
public class Round extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "roundId", nullable = false)
    private Long roundId;
    private Long gameId;
    private Long playerId;
    private Integer prevPoints;
    private Integer currentPoints;
}