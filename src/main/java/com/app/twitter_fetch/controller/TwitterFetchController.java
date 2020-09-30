package com.app.twitter_fetch.controller;

import java.util.List;

import com.app.twitter_fetch.model.Tweet;
import com.app.twitter_fetch.model.filter_json.Filter;
import com.app.twitter_fetch.model.filter_json.FilterData;
import com.app.twitter_fetch.service.TwitterFetchServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TwitterFetchController {
    @Autowired
    private TwitterFetchServices services = null;

    // @RequestMapping("/test")
    // public String check(Model model) {
    //     Tweet tweet = services.getTweet();
    //     model.addAttribute(tweet);

    //     return "test";
    // }

    @GetMapping("/filters")
    List<Filter> allFilters() {
        return services.getAllFilters();
    }

    @GetMapping("/tweets")
    List<Tweet> allTweetsMatchingFilters() {
        return services.getAllTweetsMatchingFilters();
    }
    // @GetMapping("/test")
    // Tweet check() {
    //     return services.getTweet();
    // }

    // Create (add) filter
    // @PostMapping("/add_filter")
    // FilterData addFilter(@RequestBody FilterData data) {
    //     return services.addFilter(data);
    // }

    @PostMapping("capture_filter")
    Filter captureFilter(@RequestBody Filter filter) {
        System.out.println(filter);
        return filter;
    }

}
