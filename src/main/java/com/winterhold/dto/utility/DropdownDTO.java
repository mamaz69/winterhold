package com.winterhold.dto.utility;

import lombok.Getter;
import lombok.Setter;

public class DropdownDTO {
    @Getter @Setter private String stringValue;
    @Getter @Setter private Long longValue;
    @Getter @Setter private String text;

    //DropdownDTO(Long, String)
    public DropdownDTO(Long longValue, String text) {
        this.longValue = longValue;
        this.text = text;
    }

    //DropdownDTO(String, String)
    public DropdownDTO(String stringValue, String text) {
        this.stringValue = stringValue;
        this.text = text;
    }
}
