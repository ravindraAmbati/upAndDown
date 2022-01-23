package play.dice.upAndDown.api.model;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Builder
@Data
public class RoundVo implements Serializable {

    private Long roundId;
    private String playerName;
    private Integer prevPoints;
    private Integer currentPoints;

}
