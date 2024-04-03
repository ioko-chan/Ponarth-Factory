package com.solomennicova.AuthTemplate.Service;

import com.solomennicova.AuthTemplate.Dto.Site.BeerDto;
import com.solomennicova.AuthTemplate.Dto.Site.BeerInfoDto;
import com.solomennicova.AuthTemplate.Dto.Utils.MappingUtilsBeer;
import com.solomennicova.AuthTemplate.Entity.Beer;
import com.solomennicova.AuthTemplate.Repository.BeerRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SiteService {

    private final BeerRepository beerRepository;

    private final MappingUtilsBeer mappingUtilsBeer;

    public SiteService(BeerRepository beerRepository, MappingUtilsBeer mappingUtilsBeer) {
        this.beerRepository = beerRepository;
        this.mappingUtilsBeer = mappingUtilsBeer;
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
}
