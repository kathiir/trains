package app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RailDto {
    private Integer id;

    private RailDto next;

    private boolean isStation = false;

    private String name;

    public RailDto(String name) {
        this.name = name;
        isStation = true;
    }
}
