package com.myinterview.aws.service;

import com.myinterview.aws.repository.URLRepository;
import com.myinterview.aws.util.IDConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by QArt on 2019/5/11.
 */
@Service
public class URLConverterService {
    private static final Logger LOGGER = LoggerFactory.getLogger(URLConverterService.class);
    private final URLRepository urlRepository;

    @Autowired
    public URLConverterService(URLRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    public String shortenURL(String localURL, String longUrl) {
        String uniqueID;
        if(false) {
            LOGGER.info("Original URL has been requested {}", longUrl);
            // todo;
        }
        else {
            LOGGER.info("Newly request URL, Shortening {}", longUrl);
            Long id = urlRepository.incrementID();
            uniqueID = IDConverter.INSTANCE.createUniqueID(id);
            urlRepository.saveUrl("url:" + id, longUrl);
        }

        String baseString = formatLocalURLFromShortener(localURL);
        return baseString + uniqueID;
    }

    public String getLongURLFromID(String uniqueID) throws Exception {
        Long dictionaryKey = IDConverter.INSTANCE.getDictionaryKeyFromUniqueID(uniqueID);
        String longUrl = urlRepository.getUrl(dictionaryKey);
        LOGGER.info("Converting shortened URL back to {}", longUrl);
        return longUrl;
    }

    private String formatLocalURLFromShortener(String localURL) {
        int lastSlash = localURL.lastIndexOf("/");
        String localURLStarter = localURL.substring(0,lastSlash+1);
        LOGGER.info("starter: {}", localURLStarter);
        return localURLStarter;
    }

}
