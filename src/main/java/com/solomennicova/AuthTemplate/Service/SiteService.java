package com.solomennicova.AuthTemplate.Service;

import com.solomennicova.AuthTemplate.Dto.Site.BeerDto;
import com.solomennicova.AuthTemplate.Dto.Site.BeerInfoDto;
import com.solomennicova.AuthTemplate.Dto.Site.VacancyDto;
import com.solomennicova.AuthTemplate.Dto.Site.VacancyInfoDto;
import com.solomennicova.AuthTemplate.Dto.Utils.MappingUtilsBeer;
import com.solomennicova.AuthTemplate.Dto.Utils.MappingUtilsVacancy;
import com.solomennicova.AuthTemplate.Entity.Beer;
import com.solomennicova.AuthTemplate.Entity.Vacancy;
import com.solomennicova.AuthTemplate.Exception.BeerNotFoundException;
import com.solomennicova.AuthTemplate.Exception.VacancyNotFoundException;
import com.solomennicova.AuthTemplate.Repository.BeerRepository;
import com.solomennicova.AuthTemplate.Repository.VacancyRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SiteService {

    private final BeerRepository beerRepository;

    private final VacancyRepository vacancyRepository;

    private final MappingUtilsBeer mappingUtilsBeer;

    private final MappingUtilsVacancy mappingUtilsVacancy;

    public SiteService(BeerRepository beerRepository, VacancyRepository vacancyRepository, MappingUtilsBeer mappingUtilsBeer, MappingUtilsVacancy mappingUtilsVacancy) {
        this.beerRepository = beerRepository;
        this.vacancyRepository = vacancyRepository;
        this.mappingUtilsBeer = mappingUtilsBeer;
        this.mappingUtilsVacancy = mappingUtilsVacancy;
    }

    public List<BeerInfoDto> getAllBeer() {

        List<Beer> beers = beerRepository.findAll();
        List<BeerInfoDto> beersDto = new ArrayList<>();

        for (Beer beer : beers) {
            beersDto.add(mappingUtilsBeer.BeerToBeerInfoDto(beer));
        }

        return beersDto;
    }

    public void addBeer(BeerDto beerDto) {
        Beer beer = mappingUtilsBeer.BeerDtoToBeer(beerDto);
        beerRepository.save(beer);
    }

    public void updateBeer(BeerInfoDto beerInfoDto) throws BeerNotFoundException {
        Beer beer = beerRepository.findById(beerInfoDto.getId()).orElse(null);
        if(beer == null){
            throw new BeerNotFoundException("Beer not found");
        }
        if(beerInfoDto.getName() != null && !beerInfoDto.getName().isEmpty()){
            beer.setName(beerInfoDto.getName());
        }
        if(beerInfoDto.getDescription() != null && !beerInfoDto.getDescription().isEmpty()){
            beer.setName(beerInfoDto.getDescription());
        }
        if(beerInfoDto.getColor() != null && !beerInfoDto.getColor().isEmpty()){
            beer.setName(beerInfoDto.getColor());
        }
        if(beerInfoDto.getImage() != null && !beerInfoDto.getImage().isEmpty()){
            beer.setName(beerInfoDto.getImage());
        }
        beerRepository.save(beer);
    }

    public void deleteBeer(Long idBeer) throws BeerNotFoundException {
        Beer beer = beerRepository.findById(idBeer).orElse(null);
        if(beer == null){
            throw new BeerNotFoundException("Beer not found");
        }
        beerRepository.deleteById(idBeer);
    }

    public List<VacancyInfoDto> getAllVacancy(){
        List<Vacancy> vacancies = vacancyRepository.findAll();
        List<VacancyInfoDto> vacanciesInfoDto = new ArrayList<>();

        for (Vacancy vacancy : vacancies){
            vacanciesInfoDto.add(mappingUtilsVacancy.VacancyToVacancyInfoDto(vacancy));
        }

        return vacanciesInfoDto;
    }

    public void addVacancy(VacancyDto vacancyDto){
        Vacancy vacancy = mappingUtilsVacancy.VacancyDtoToVacancy(vacancyDto);
        vacancyRepository.save(vacancy);
    }

    public void updateVacancy(VacancyInfoDto vacancyInfoDto) throws VacancyNotFoundException {
        Vacancy vacancy = vacancyRepository.findById(vacancyInfoDto.getId()).orElse(null);
        if(vacancy == null){
            throw new VacancyNotFoundException("Vacancy not found");
        }
        if(vacancyInfoDto.getName() != null && !vacancyInfoDto.getName().isEmpty()){
            vacancy.setName(vacancyInfoDto.getName());
        }
        if(vacancyInfoDto.getDescription() != null && !vacancyInfoDto.getDescription().isEmpty()){
            vacancy.setDescription(vacancyInfoDto.getDescription());
        }
        if(vacancyInfoDto.getName() != null && !vacancyInfoDto.getName().isEmpty()){
            vacancy.setName(vacancyInfoDto.getName());
        }
        vacancyRepository.save(vacancy);
    }

    public void deleteVacancy(Long idVacancy) throws VacancyNotFoundException {
        Vacancy vacancy = vacancyRepository.findById(idVacancy).orElse(null);
        if(vacancy == null){
            throw new VacancyNotFoundException("Vacancy not found");
        }
        vacancyRepository.deleteById(idVacancy);
    }
}
