package com.solomennicova.AuthTemplate.Dto.Site;

import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access= AccessLevel.PUBLIC, force=true)
@AllArgsConstructor
public class VacancyInfoDto {

    @NotEmpty(message = "IDs не должно быть пустым")
    private Long id;

    private String name;

    private String description;

    private String image;
}
