package com.app.twitter_fetch.repository;

import com.app.twitter_fetch.model.tweet_json.Tweet;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TweetRepository extends CrudRepository<Tweet, Long> {
    
}
