package com.app.twitter_fetch.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.app.twitter_fetch.model.Tweet;
import com.app.twitter_fetch.model.filter_json.Filter;
import com.app.twitter_fetch.model.filter_json.FilterData;
import com.app.twitter_fetch.model.filter_json.SenderAddFilter;
import com.app.twitter_fetch.model.filter_json.SenderDeleteFilter;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TwitterFetchServices {
    InputStream inputStream;
    MappingIterator<Tweet> tweetIterator = null;
    Boolean isOpen;

    public Tweet getTestTweet() {
        Tweet tweet = null;
        try {
            HttpEntity entity = RESTHelper.getHeaders();
            RestTemplate restTemplate = new RestTemplate();

            String url = "https://api.twitter.com/2/tweets/20";
            ResponseEntity<Tweet> response = restTemplate.exchange(url, HttpMethod.GET, entity, Tweet.class, 1);
            return response.getBody();
        } catch (Exception e) {
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
            } else {
                return filters;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return filters;
    }

    public MappingIterator<Tweet> getIteratorForTweetsMatchingFilters() {
        try {
            URLConnection connection = new URL("https://api.twitter.com/2/tweets/search/stream").openConnection();
            connection.setRequestProperty("Authorization",
                    "Bearer AAAAAAAAAAAAAAAAAAAAAG9zIAEAAAAAYAX%2BDKaXrLwnPuUrzdAqo9FLY7E%3Dzn7GnSRVgkzDaZZJwjXDMEZ24zPYdlLldUTE8TdWSjLKVcmzWq");
            connection.setDoOutput(true);
            inputStream = connection.getInputStream();
            ObjectMapper mapper = new ObjectMapper();

            isOpen = true;

            tweetIterator = mapper.readerFor(Tweet.class).readValues(inputStream);
            // while (isOpen && i.hasNextValue()) {
            //     Tweet tweet = i.nextValue();
            //     // CAREFUL ... tweets.add(tweet);
            // }

            return tweetIterator;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tweetIterator;
    }

    public void stopTweets() {
        isOpen = false;
        try {
            tweetIterator.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

    public Boolean addFilter(SenderAddFilter data) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

            headers.set("Authorization",
                    "Bearer AAAAAAAAAAAAAAAAAAAAAG9zIAEAAAAAYAX%2BDKaXrLwnPuUrzdAqo9FLY7E%3Dzn7GnSRVgkzDaZZJwjXDMEZ24zPYdlLldUTE8TdWSjLKVcmzWq");

            
            HttpEntity entity = new HttpEntity<>(data, headers);
            
            RestTemplate restTemplate = new RestTemplate();

            String url = "https://api.twitter.com/2/tweets/search/stream/rules";
            ResponseEntity<FilterData> response = restTemplate.exchange(url, HttpMethod.POST, entity, FilterData.class, 1);
            if (response.getStatusCode() == HttpStatus.CREATED) {
                System.out.println(response.getBody());
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
		return false;
	}

    public Boolean deleteFilter(SenderDeleteFilter data) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

            headers.set("Authorization",
                    "Bearer AAAAAAAAAAAAAAAAAAAAAG9zIAEAAAAAYAX%2BDKaXrLwnPuUrzdAqo9FLY7E%3Dzn7GnSRVgkzDaZZJwjXDMEZ24zPYdlLldUTE8TdWSjLKVcmzWq");

            HttpEntity entity = new HttpEntity<>(data, headers);

            RestTemplate restTemplate = new RestTemplate();

            String url = "https://api.twitter.com/2/tweets/search/stream/rules";
            ResponseEntity<FilterData> response = restTemplate.exchange(url, HttpMethod.POST, entity, FilterData.class,
                    1);
            if (response.getStatusCode() == HttpStatus.OK) {
                System.out.println(response.getBody());
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
		return false;
	}
}
