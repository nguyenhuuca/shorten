package com.canhlabs.shorten.service.impl;

import com.canhlabs.shorten.domain.URL;
import com.canhlabs.shorten.repo.URLRepo;
import com.canhlabs.shorten.service.KeyGenerateService;
import com.canhlabs.shorten.service.ShortenService;
import com.canhlabs.shorten.share.AppConstant;
import com.canhlabs.shorten.share.dto.ShortenDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public String getOriginLink(String id) {
        String hash = id.substring(1);
        URL url = urlRepo.findAllByHash(hash);
        if(url != null && url.getOriginalURL() != null) {
            return  url.getOriginalURL();
        }
        return "google.com";
    }

    private URL toEntity(String url, String shortLink) {
        return URL.builder()
                .hash(shortLink)
                .originalURL(url)
                .build();
    }
}
