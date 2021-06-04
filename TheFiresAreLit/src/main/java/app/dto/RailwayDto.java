package app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RailwayDto {
    private Integer id;

    @Builder.Default
    private List<RailDto> railDtos = new LinkedList<>();
    @Builder.Default
    private List<RailDto> stationDtos = new LinkedList<>();
}
