package app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainDto {
    private Integer id;

    private String name;
    private RouteDto route;
    private RailDto rail;
}
