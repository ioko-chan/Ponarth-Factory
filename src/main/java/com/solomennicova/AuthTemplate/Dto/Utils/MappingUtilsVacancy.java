package com.solomennicova.AuthTemplate.Dto.Utils;

import com.solomennicova.AuthTemplate.Dto.Site.VacancyDto;
import com.solomennicova.AuthTemplate.Dto.Site.VacancyInfoDto;
import com.solomennicova.AuthTemplate.Entity.Vacancy;
import org.springframework.stereotype.Service;

@Service
public class MappingUtilsVacancy {

    public VacancyInfoDto VacancyToVacancyInfoDto(Vacancy vacancy){
        VacancyInfoDto vacancyInfoDto = new VacancyInfoDto();
        vacancyInfoDto.setId(vacancy.getId());
        vacancyInfoDto.setName(vacancy.getName());
        vacancyInfoDto.setDescription(vacancy.getDescription());
        vacancyInfoDto.setImage(vacancy.getImage());
        return vacancyInfoDto;
    }

    public VacancyDto VacancyToVacancyDto(Vacancy vacancy){
        VacancyDto vacancyDto = new VacancyDto();
        vacancyDto.setName(vacancyDto.getName());
        vacancyDto.setDescription(vacancyDto.getDescription());
        vacancyDto.setImage(vacancyDto.getImage());
        return vacancyDto;
    }

    public Vacancy VacancyDtoToVacancy(VacancyDto vacancyDto){
        Vacancy vacancy = new Vacancy();
        vacancy.setName(vacancyDto.getName());
        vacancy.setDescription(vacancyDto.getDescription());
        vacancy.setImage(vacancyDto.getImage());
        return vacancy;
    }

}
