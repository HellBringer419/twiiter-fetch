package com.app.twitter_fetch.service;

import java.util.ArrayList;
import java.util.List;

import com.app.twitter_fetch.model.Tweet;
import com.app.twitter_fetch.model.filter_json.Filter;
import com.app.twitter_fetch.model.filter_json.FilterData;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TwitterFetchServices {
    
    public Tweet getTweet() {
        Tweet tweet = null;
        try {
            HttpEntity entity = RESTHelper.getHeaders();
            RestTemplate restTemplate = new RestTemplate();

            String url = "https://api.twitter.com/2/tweets/20";
            ResponseEntity<Tweet> response = restTemplate.exchange(url, HttpMethod.GET, entity, Tweet.class, 1);
            return response.getBody();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return tweet;
    }

    public List<Filter> getAllFilters() {
        List<Filter> filters = new ArrayList<>();
        try {
            HttpEntity entity = RESTHelper.getHeaders();
            RestTemplate restTemplate = new RestTemplate();

            String url = "https://api.twitter.com/2/tweets/search/stream/rules";
            ResponseEntity<FilterData> response = restTemplate.exchange(url, HttpMethod.GET, entity, FilterData.class,
                    1);

            if (response.getStatusCode() == HttpStatus.OK) {
                for (Filter filter : response.getBody().getData()) {
                    filters.add(filter);
                }
                return filters;
            }
            else {
                return filters;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
		return filters;
	}

    public List<Tweet> getAllTweetsMatchingFilters() {
        List<Tweet> tweets = null;
        try {
            HttpEntity entity = RESTHelper.getHeaders();
            RestTemplate restTemplate = new RestTemplate();

            String url = "https://api.twitter.com/2/tweets/search/stream";
            ResponseEntity<Tweet[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, Tweet[].class, 1);

            System.out.println(response.getBody());
            // if (response.getStatusCode() == HttpStatus.OK) {
            //     for (Tweet tweet : response.getBody()) {
            //         tweets.add(tweet);
            //     }
            //     return tweets;
            // } else {
            //     return tweets;
            // }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tweets;
    }

    // public FilterData addFilter(FilterData data) {
    //     FilterData filterData = null;
    //     try {
    //         HttpEntity entity = RESTHelper.getHeaders();
    //         RestTemplate restTemplate = new RestTemplate();

    //         String url = "https://api.twitter.com/2/tweets/search/stream/rules";
    //         ResponseEntity<FilterData> response = restTemplate.exchange(url, HttpMethod.GET, entity, FilterData.class,
    //                 1);

    //         if (response.getStatusCode() == HttpStatus.OK) {
    //             for (Filter filter : response.getBody().getData()) {
    //                 filters.add(filter);
    //             }
    //             return filters;
    //         } else {
    //             return filters;
    //         }
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //     }
	// 	return filterData;
	// }
}
