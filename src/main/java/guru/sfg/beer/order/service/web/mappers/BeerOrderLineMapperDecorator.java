package guru.sfg.beer.order.service.web.mappers;

import guru.sfg.beer.order.service.domain.BeerOrderLine;
import guru.sfg.beer.order.service.services.beer.BeerService;
import guru.sfg.beer.order.service.services.beer.model.BeerDto;
import guru.sfg.brewery.model.BeerOrderLineDto;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public abstract class BeerOrderLineMapperDecorator implements  BeerOrderLineMapper{

    private BeerService beerService;
    private BeerOrderLineMapper beerOrderLineMapper;

    @Autowired
    public void setBeerService(BeerService beerService) {
        this.beerService = beerService;
    }

    @Autowired
    public void setBeerOrderLineMapper(BeerOrderLineMapper beerOrderLineMapper) {
        this.beerOrderLineMapper = beerOrderLineMapper;
    }

    @Override
    public BeerOrderLineDto beerOrderLineToDto(BeerOrderLine beerOrderLine) {
        Optional<BeerDto> beerDto = beerService.getBeerByUpc(beerOrderLine.getUpc());
        BeerOrderLineDto beerOrderLineDto = beerOrderLineMapper.beerOrderLineToDto(beerOrderLine);
        beerDto.ifPresent(dto -> {
            beerOrderLineDto.setBeerId(dto.getId());
            beerOrderLineDto.setBeerName(dto.getBeerName());
            beerOrderLineDto.setBeerStyle(dto.getBeerStyle());
            beerOrderLineDto.setPrice(dto.getPrice() );
        });
        return beerOrderLineDto;
    }

    @Override
    public BeerOrderLine dtoToBeerOrderLine(BeerOrderLineDto dto) {
        return beerOrderLineMapper.dtoToBeerOrderLine(dto);
    }
}
