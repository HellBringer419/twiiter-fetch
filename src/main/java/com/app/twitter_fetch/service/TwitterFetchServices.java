package com.app.twitter_fetch.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collections;

import com.app.twitter_fetch.model.filter_json.Filter;
import com.app.twitter_fetch.model.filter_json.FilterData;
import com.app.twitter_fetch.model.filter_json.SenderAddFilter;
import com.app.twitter_fetch.model.filter_json.SenderDeleteFilter;
import com.app.twitter_fetch.model.tweet_json.Tweet;
import com.app.twitter_fetch.model.tweet_json.TweetData;

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
    private InputStream inputStream = null;
    private MappingIterator<TweetData> tweetIterator = null;
    private Boolean isOpen = false;

    public Tweet getTestTweet() {
        Tweet tweet = null;
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

            headers.set("Authorization", APIHelper.getBearerToken());

            HttpEntity entity = new HttpEntity(headers);
            RestTemplate restTemplate = new RestTemplate();

            String url = "https://api.twitter.com/2/tweets/20";
            ResponseEntity<TweetData> response = restTemplate.exchange(url, HttpMethod.GET, entity, TweetData.class, 1);
            return response.getBody().getData();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return tweet;
    }

    public Filter[] getAllFilters() {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

            headers.set("Authorization", APIHelper.getBearerToken());

            HttpEntity entity = new HttpEntity(headers);
            RestTemplate restTemplate = new RestTemplate();

            String url = "https://api.twitter.com/2/tweets/search/stream/rules";
            ResponseEntity<FilterData> response = restTemplate.exchange(url, HttpMethod.GET, entity, FilterData.class,
                    1);

            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody().getData();
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public MappingIterator<TweetData> getIteratorForTweetsMatchingFilters() {
        try {
            URLConnection connection = new URL("https://api.twitter.com/2/tweets/search/stream?tweet.fields=created_at").openConnection();
            connection.setRequestProperty("Authorization", APIHelper.getBearerToken());
            connection.setDoOutput(true);

            connection.connect();
            if(this.inputStream == null) {
                this.inputStream = connection.getInputStream();
                ObjectMapper mapper = new ObjectMapper();
    
                isOpen = true;
    
                this.tweetIterator = mapper.readerFor(TweetData.class).readValues(inputStream);
            }
            
            return this.tweetIterator;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Tweet getTweetForMatchingFilter() {
        if (this.tweetIterator == null) {
            this.tweetIterator = getIteratorForTweetsMatchingFilters();
        }
        Tweet tweet = null;
        try {
            tweet = this.tweetIterator.nextValue().getData();
            return tweet;

            // TODO: save this tweet in db

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Tweet[] getTweetFromDB(Integer numberOfTweets) {
        // TODO: Complete DAO
        Tweet[] tweets = new Tweet[20];
        return tweets;
    }

    public Boolean stopTweets() {
        if (this.tweetIterator != null) {
            this.isOpen = false;
            try {
                this.tweetIterator.close();
                this.inputStream.close();

                this.tweetIterator = null;
                this.inputStream = null;
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        else {
            return false;
        }
	}

    public Boolean addFilter(SenderAddFilter data) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

            headers.set("Authorization", APIHelper.getBearerToken());

            
            HttpEntity entity = new HttpEntity<>(data, headers);
            
            RestTemplate restTemplate = new RestTemplate();

            String url = "https://api.twitter.com/2/tweets/search/stream/rules";
            ResponseEntity<FilterData> response = restTemplate.exchange(url, HttpMethod.POST, entity, FilterData.class, 1);
            if (response.getStatusCode() == HttpStatus.CREATED) {
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

            headers.set("Authorization", APIHelper.getBearerToken());

            HttpEntity entity = new HttpEntity<>(data, headers);

            RestTemplate restTemplate = new RestTemplate();

            String url = "https://api.twitter.com/2/tweets/search/stream/rules";
            ResponseEntity<FilterData> response = restTemplate.exchange(url, HttpMethod.POST, entity, FilterData.class, 1);
            if (response.getStatusCode() == HttpStatus.OK) {
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
