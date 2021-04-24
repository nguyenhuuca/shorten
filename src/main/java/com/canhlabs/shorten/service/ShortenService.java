package com.canhlabs.shorten.service;

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
}
