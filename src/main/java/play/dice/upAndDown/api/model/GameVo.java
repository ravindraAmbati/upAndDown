package play.dice.upAndDown.api.model;

import lombok.Builder;
import lombok.Data;
import play.dice.upAndDown.db.GameStatusEnum;
import play.dice.upAndDown.db.entities.Player;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.List;

@Builder
@Data
public class GameVo implements Serializable {

    private Long gameId;
    private List<PlayerVo> playerVos;
    private String gameStatusEnum;
}
