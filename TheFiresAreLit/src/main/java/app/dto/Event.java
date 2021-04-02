package app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Event {
    private Train train;
    private Rail railSection;
    private Integer beginning;
    private Integer ending;

    public Event(Train train, Integer beginning, Integer ending){
        this.train = train;
    }

    public Event(Rail railSection, Integer beginning, Integer ending) {
        this.railSection = railSection;
        this.ending = ending;
    }
}
