package play.dice.upAndDown.api.model;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Builder
@Data
public class PlayerVo implements Serializable {

    private Long playerId;
    private String name;
    private LocalDate dob;

}
