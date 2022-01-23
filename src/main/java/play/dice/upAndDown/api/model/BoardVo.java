package play.dice.upAndDown.api.model;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Builder
@Data
public class BoardVo implements Serializable {

    private Long gameId;
    private Map<PlayerVo, Integer> playerVoPrevPositionMap;
    private Map<PlayerVo, Integer> playerVoPositionMap;
    private List<RoundVo> roundVoList;
}
