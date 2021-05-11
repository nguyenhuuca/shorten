package com.canhlabs.shorten.service.impl;

import com.canhlabs.shorten.domain.URL;
import com.canhlabs.shorten.service.KeyGenerateService;
import com.canhlabs.shorten.service.ShortenService;
import com.canhlabs.shorten.service.repo.URLRepo;
import com.canhlabs.shorten.share.AppConstant;
import com.canhlabs.shorten.share.dto.ShortenDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static com.canhlabs.shorten.share.AppConstant.EXPIRED_DURATION;

/**
 * Implement for shorten link
 */
@Service
public class ShortenServiceImpl implements ShortenService {
    private KeyGenerateService kgs;
    private URLRepo urlRepo;

    // setter injection all dependency
    @Autowired
    public void setKSG(KeyGenerateService kgs) {
        this.kgs = kgs;
    }

    @Autowired
    public void setUrlRepo(URLRepo repo) {
        this.urlRepo = repo;
    }
    // end injection

    /**
     * gen the shorten link and save it to database
     *
     * @param url the origin url
     * @return shorten link
     */
    @Override
    public String shortenLink(String url) {
        String genStr = kgs.generate();
        String shortLink = AppConstant.BASE_DOMAIN.concat(genStr);

        URL entity = toEntity(url, genStr);
        urlRepo.save(entity);

        return shortLink;
    }

    /**
     * Create the entity and save it to database with hashGen is key
     */
    @Override
    public void saveShortenLink(ShortenDto shortenDto) {
        URL entity = toEntity(shortenDto.getOriginUrl(), shortenDto.getHash());
        urlRepo.save(entity);
    }

    /**
     * in case url not found, return page not found,
     * in case uri expired, return the expire page.
     * url of page not found and expire page need to load from environment
     * @param id the hash key is generated
     * @return original url
     */
    @Override
    public String getOriginLink(String id) {
        String hash = id.substring(1);
        URL url = urlRepo.findAllByHash(hash);
        if(url != null && url.getOriginalURL() != null) {
            return  url.getOriginalURL();
        }
        return AppConstant.props.getErrorPage();
    }

    private URL toEntity(String url, String shortLink) {
        Instant currentTime = Instant.now();
        Instant expireDate = currentTime.plus(EXPIRED_DURATION, ChronoUnit.DAYS);
        return URL.builder()
                .hash(shortLink)
                .originalURL(url)
                .expirationDate(expireDate)
                .build();
    }
}
