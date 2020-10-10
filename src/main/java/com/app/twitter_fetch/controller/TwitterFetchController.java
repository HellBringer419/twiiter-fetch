package com.app.twitter_fetch.controller;

import com.app.twitter_fetch.model.Tweet;
import com.app.twitter_fetch.model.filter_json.Filter;
import com.app.twitter_fetch.model.filter_json.SenderAddFilter;
import com.app.twitter_fetch.model.filter_json.SenderDeleteFilter;
import com.app.twitter_fetch.service.TwitterFetchServices;
import com.fasterxml.jackson.databind.MappingIterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TwitterFetchController {
    @Autowired
    private TwitterFetchServices services = null;

    MappingIterator<Tweet> tweetIterator = null;
    
    @GetMapping("/filters")
    Filter[] allFilters() {
        return services.getAllFilters();
    }

    @GetMapping("/tweets")
    Tweet allTweetsMatchingFilters() {
        if (tweetIterator == null) {
            tweetIterator = services.getIteratorForTweetsMatchingFilters();
        }
        Tweet tweet = null;
        try {
            tweet = tweetIterator.nextValue();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tweet;
    }

    @GetMapping("/stop_tweets")
    void stopTweets() {
        services.stopTweets();
    }

    @GetMapping("/test")
    Tweet check() {
        return services.getTestTweet();
    }

    // Create (add) filter
    @PostMapping("/add_filter")
    Boolean addFilter(@RequestBody SenderAddFilter data) {
        return services.addFilter(data);
    }

    @PostMapping("/delete_filter")
    Boolean removeFilter(@RequestBody SenderDeleteFilter data) {
        Boolean res = services.deleteFilter(data);
        return res;
    }

    @PostMapping("capture_filter")
    Filter captureFilter(@RequestBody Filter filter) {
        return filter;
    }

}
