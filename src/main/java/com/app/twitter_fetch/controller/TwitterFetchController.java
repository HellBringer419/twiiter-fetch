package com.app.twitter_fetch.controller;

import com.app.twitter_fetch.model.filter_json.Filter;
import com.app.twitter_fetch.model.filter_json.SenderAddFilter;
import com.app.twitter_fetch.model.filter_json.SenderDeleteFilter;
import com.app.twitter_fetch.model.tweet_json.Tweet;
import com.app.twitter_fetch.service.TwitterFetchServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TwitterFetchController {
    @Autowired
    private TwitterFetchServices services = null;
    
    // This method returns one tweet at a time but keeps the connection alive
    // to retrieve multiple tweets hence, /tweets
    // use /stop_tweets to close said connection
    @GetMapping("/tweets")
    Tweet allTweetsMatchingFilters() {
        return services.getTweetForMatchingFilter();
    }

    // @GetMapping("/get_old_tweets/${numberOfTweets}")
    // Tweet[] getTweetFromDB(@RequestParam Integer numberOfTweets) {
    //     return services.getTweetFromDB(numberOfTweets);
    // }

    // closes the connection started by /tweets
    @GetMapping("/stop_tweets")
    Boolean stopTweets() {
        return services.stopTweets();
    }
    
    
    // Create (add) filter
    @PostMapping("/add_filter")
    Boolean addFilter(@RequestBody SenderAddFilter data) {
        return services.addFilter(data);
    }

    // Read All (show) filters
    @GetMapping("/filters")
    Filter[] allFilters() {
        return services.getAllFilters();
    }
    
    // Delete filter(s)
    @PostMapping("/delete_filter")
    Boolean removeFilter(@RequestBody SenderDeleteFilter data) {
        Boolean res = services.deleteFilter(data);
        return res;
    }
    
    
    // Only for testing connection
    @GetMapping("/test")
    Tweet check() {
        return services.getTestTweet();
    }
    
    // Only for testing connection
    @PostMapping("capture_filter")
    Filter captureFilter(@RequestBody Filter filter) {
        return filter;
    }
    
}
