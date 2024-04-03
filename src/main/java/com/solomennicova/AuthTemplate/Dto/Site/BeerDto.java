package com.solomennicova.AuthTemplate.Dto.Site;

import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access= AccessLevel.PUBLIC, force=true)
@AllArgsConstructor
public class BeerDto {

    @NotEmpty(message = "Название не должно быть пустым")
    private String name;

    @NotEmpty(message = "Описание не должно быть пустым")
    private String description;

    @NotEmpty(message = "Цвет не должен быть пустым")
    private String color;

}
