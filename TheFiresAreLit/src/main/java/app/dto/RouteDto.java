package app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Builder

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RouteDto {
    private Integer schedule_id;
    private Integer log_id;

    @Builder.Default
    private List<RoutePartDto> schedule = new ArrayList<>();
    @Builder.Default
    private List<RoutePartDto> log = new ArrayList<>();
}
