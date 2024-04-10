package com.solomennicova.AuthTemplate.Controller;

import com.solomennicova.AuthTemplate.Dto.Exception.ErrorDto;
import com.solomennicova.AuthTemplate.Dto.Site.BeerDto;
import com.solomennicova.AuthTemplate.Dto.Site.BeerInfoDto;
import com.solomennicova.AuthTemplate.Dto.Site.VacancyDto;
import com.solomennicova.AuthTemplate.Dto.Site.VacancyInfoDto;
import com.solomennicova.AuthTemplate.Exception.BeerNotFoundException;
import com.solomennicova.AuthTemplate.Exception.VacancyNotFoundException;
import com.solomennicova.AuthTemplate.Service.SiteService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(path = "/api/admin")
public class AdminController {

    private final SiteService siteService;

    public AdminController(SiteService siteService) {
        this.siteService = siteService;
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = {
                    @Content(schema = @Schema(implementation = ErrorDto.class))
            })
    })
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("/beer/add")
    public void loadBeer(@RequestBody BeerDto beerDto) {
        siteService.addBeer(beerDto);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = {
                    @Content(schema = @Schema(implementation = ErrorDto.class))
            })
    })
    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping("/beer/update")
    public void updateBeer(@RequestBody BeerInfoDto beerUpdate) throws BeerNotFoundException {
        siteService.updateBeer(beerUpdate);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = {
                    @Content(schema = @Schema(implementation = ErrorDto.class))
            })
    })
    @SecurityRequirement(name = "Bearer Authentication")
    @DeleteMapping("/beer/delete/{id}")
    public void deleteBeer(@PathVariable Long id) throws BeerNotFoundException {
        siteService.deleteBeer(id);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = {
                    @Content(schema = @Schema(implementation = ErrorDto.class))
            })
    })
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("/vacancy/add")
    public void loadVacancy(@RequestBody VacancyDto vacancyDto) {
        siteService.addVacancy(vacancyDto);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = {
                    @Content(schema = @Schema(implementation = ErrorDto.class))
            })
    })
    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping("/vacancy/update")
    public void updateVacancy(@RequestBody VacancyInfoDto vacancyInfoDto) throws VacancyNotFoundException {
        siteService.updateVacancy(vacancyInfoDto);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = {
                    @Content(schema = @Schema(implementation = ErrorDto.class))
            })
    })
    @SecurityRequirement(name = "Bearer Authentication")
    @DeleteMapping("/vacancy/delete/{id}")
    public void deleteVacancy(@PathVariable Long id) throws VacancyNotFoundException {
        siteService.deleteVacancy(id);
    }
}
