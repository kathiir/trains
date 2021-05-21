package app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventDto {
    private Integer id;

    private TrainDto train;
    private RailDto rail;
    private Integer beginning;
    private Integer ending;

    public EventDto(TrainDto train, Integer beginning, Integer ending){
        this.train = train;
    }

    public EventDto(RailDto railDtoSection, Integer beginning, Integer ending) {
        this.rail = railDtoSection;
        this.ending = ending;
    }
}
