package app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
//@AllArgsConstructor
@NoArgsConstructor
public class Rail {
    UUID uuid = UUID.randomUUID();
}
