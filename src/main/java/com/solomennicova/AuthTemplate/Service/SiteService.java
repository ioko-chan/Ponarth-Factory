package com.solomennicova.AuthTemplate.Service;

import com.solomennicova.AuthTemplate.Dto.Site.BeerDto;
import com.solomennicova.AuthTemplate.Dto.Site.BeerInfoDto;
import com.solomennicova.AuthTemplate.Dto.Utils.MappingUtilsBeer;
import com.solomennicova.AuthTemplate.Entity.Beer;
import com.solomennicova.AuthTemplate.Exception.DontImageException;
import com.solomennicova.AuthTemplate.Exception.ImageDontLoad;
import com.solomennicova.AuthTemplate.Repository.BeerRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class SiteService {

    private final BeerRepository beerRepository;

    private final MappingUtilsBeer mappingUtilsBeer;

    private final StoreService storeService;

    public SiteService(BeerRepository beerRepository, MappingUtilsBeer mappingUtilsBeer, StoreService storeService) {
        this.beerRepository = beerRepository;
        this.mappingUtilsBeer = mappingUtilsBeer;
        this.storeService = storeService;
    }

    public List<BeerInfoDto> getAllBeer(){

        List<Beer> beers = beerRepository.findAll();
        List<BeerInfoDto> beersDto = new ArrayList<>();

        for(Beer beer : beers){
            beersDto.add(mappingUtilsBeer.BeerToBeerInfoDto(beer));
        }

        return beersDto;
    }

    public String addBeer(BeerDto beerDto, MultipartFile file) throws DontImageException, ImageDontLoad {
        Beer beer = mappingUtilsBeer.BeerDtoToBeer(beerDto);
        String filename = storeService.loadImage(file, beerDto.getName());
        beer.setImage(filename);
        return filename;
    }
}
