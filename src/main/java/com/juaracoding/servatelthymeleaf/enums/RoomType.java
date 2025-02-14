package com.juaracoding.servatelthymeleaf.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@AllArgsConstructor
public enum RoomType {
    STANDARD_ROOM(2),
    DELUX_DOUBLE(2),
    DELUX_TWIN(2),
    FAMILY_ROOM(4);

    private final int capacity;
}
