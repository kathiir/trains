package app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoutePartDto {
    private Integer id;

    private RailDto station;
    private Integer arrival;
    private Integer department;

    public RoutePartDto(RailDto station, Integer arrival, Integer department) {
        this.station = station;
        this.arrival = arrival;
        this.department = department;
    }
}
