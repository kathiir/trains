package app.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Railway {
    @Builder.Default
    private List<Rail> rails = new LinkedList<>();
    @Builder.Default
    private List<Station> stations = new LinkedList<>();
}
