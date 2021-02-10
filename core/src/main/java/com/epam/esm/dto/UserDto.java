package com.epam.esm.dto;

import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

@Data
public class UserDto extends RepresentationModel<UserDto> {
    private int id;
    private String name;
    private String surname;
}
