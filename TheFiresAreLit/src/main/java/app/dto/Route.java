package app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Triple;

import java.util.ArrayList;
import java.util.List;

@Builder

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Route {
//    private Train train;
    @Builder.Default
    private List<Triple<Station, Integer, Integer>> schedule = new ArrayList<>();
    @Builder.Default
    private List<Triple<Station, Integer, Integer>> log = new ArrayList<>();
}
