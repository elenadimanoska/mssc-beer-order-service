package guru.sfg.beer.order.service.services.beer;

import guru.sfg.beer.order.service.services.beer.model.BeerDto;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;
import java.util.UUID;


@Slf4j
@ConfigurationProperties(prefix = "com.brewery", ignoreUnknownFields = false)
@Service
public class BeerServiceRestTemplateImpl implements BeerService {

    public final String BEER_PATH = "/api/v1/beer/upc/{upc}";
    // keep a simple path constant for tests and other consumers that expect the plain upc path
    public static final String BEER_UPC_PATH_V1 = "/api/v1/beer/upc/";
    private final RestTemplate restTemplate;

    @Setter
    private String beerServiceHost;

    public BeerServiceRestTemplateImpl(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Override
    public Optional<BeerDto> getBeerById(UUID id) {
        log.debug("Calling Beer Service with beer id {}", id);

        ResponseEntity<BeerDto> responseEntity = restTemplate.exchange
                (beerServiceHost + BEER_PATH, HttpMethod.GET, null, BeerDto.class, id.toString());

        return Optional.ofNullable(responseEntity.getBody());
    }

    @Override
    public Optional<BeerDto> getBeerByUpc (String upc) {
        log.debug("Calling Beer Service with upc {}", upc);

        ResponseEntity<BeerDto> responseEntity = restTemplate
                .exchange(beerServiceHost + BEER_PATH, HttpMethod.GET, null,
                        BeerDto.class, upc);

        return Optional.ofNullable(responseEntity.getBody());
    }
}
