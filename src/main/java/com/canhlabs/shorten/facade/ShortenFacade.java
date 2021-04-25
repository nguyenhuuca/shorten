package com.canhlabs.shorten.facade;

/**
 * Using to group complicate business domain
 */
public interface ShortenFacade {
    /**
     * Using to generate the key anh async save data to db.
     * @param url origin url
     * @return link was shorten
     */
    String shortenLink(String url);
}
