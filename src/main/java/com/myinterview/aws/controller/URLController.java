package com.myinterview.aws.controller;

import com.myinterview.aws.service.URLConverterService;
import com.myinterview.aws.util.URLValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Created by QArt on 2019/5/11.
 */
@RestController
public class URLController {
    private static final Logger LOGGER = LoggerFactory.getLogger(URLController.class);
    private final URLConverterService urlConverterService;

    public URLController(URLConverterService urlConverterService) {
        this.urlConverterService = urlConverterService;
    }

    @GetMapping(value = {"/", "/shortener"})
    public ModelAndView homePage() throws Exception {
        LOGGER.info("Access to homepage");
        ModelAndView mav = new ModelAndView("index");
        return mav;
    }


    @PostMapping(value = "/shortener")
    public ModelAndView shortenUrl(@RequestParam @Valid final String origUrl, HttpServletRequest request) throws Exception {
        LOGGER.info("-- Received url to shorten: " + origUrl);
        if (URLValidator.validateURL(origUrl)) {
            String localURL = request.getRequestURL().toString();
            String shortenedUrl = urlConverterService.shortenURL(localURL, origUrl);
            LOGGER.info("Shortened url to: " + shortenedUrl);
            ModelAndView mav = new ModelAndView("index");
            mav.addObject("shortenedUrl", shortenedUrl);
            return mav;
        }
        throw new Exception("Please enter a valid URL");
    }

    @ResponseStatus(HttpStatus.MOVED_PERMANENTLY)
    @GetMapping(value = "/{id}")
    public ModelAndView redirectUrl(@PathVariable String id, HttpServletRequest request, HttpServletResponse response) throws Exception {
        LOGGER.info("++ Received shortened url to redirect: " + id);
        String redirectUrlString = urlConverterService.getLongURLFromID(id);
        LOGGER.info("Original URL: " + redirectUrlString);
        RedirectView redirectView = new RedirectView(redirectUrlString);
        return new ModelAndView(redirectView);
    }
}

