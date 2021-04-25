package com.canhlabs.shorten.service;

import com.canhlabs.shorten.share.dto.ShortenDto;

/**
 * Service use to generate the shorten link
 */
public interface ShortenService {
    /**
     * The method use to generate the sort url
     * @param url the origin url
     * @return shorten url with format: domain.com/{generateId}, generate should a string have 6 character
     */
    String shortenLink(String url);

    /**
     * Using to save the hash that generate
     * @param  shortenDto hold the origin url and hash need to store into database
     *
     */
    void saveShortenLink(ShortenDto shortenDto);
}
