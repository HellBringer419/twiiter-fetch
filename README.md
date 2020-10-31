# Tweet Fetch

This app is designed to fetch all the tweets from Twitter with the following
conditions:
* By followers (eg: @twitter_handle)
* By keyword/track (eg: #India, Sports, #Formula1 etc)
* Both (followers or keywords).
On submitting, the app should start streaming tweets for the conditions configured.

## Requirements

This app requires the following
* Java (atleast jdk 8)
* Maven (mvn) or spring cli-tool
* Node (would be installed by Maven)
* Angular (would be installed by Node)

## Installation

To install this application locally:
* Clone this repo and get inside the directory
* Configure your app properties (Check out the configuration section of this README)
* Get in the root directory of the folder and clean install dependencies
    ``` bash
    $ mvn clean install
    ```
    (You can also use spring-cli or the include .mvnw to achieve this). This will give a build failure error on wrong configuration.
* Get resources for the frontend
    ``` bash
    $ mvn generate-resources
    ```
* Start the server 
    ``` bash
    $ mvn spring-boot:run
    ```
    The app by default run on port 8080. 
    To view this application, open localhost:8080 in your web browser.
    To close application, just ^C the process.

## Configuration

* Create a ".env" file in the root directory. You can use the ".env.sample" for guidance. Change the value of the variable ``` TWITTER_BEARER_TOKEN ``` inside "./.env" to your own Twitter Bearer Token. 
When you first run the appication, you need to source this file with:
    ``` bash
    $ source ./.env
    ```
* The default java version is 11. To change, open "pom.xml" and change the value of ``` <project> <properties> <java.version>``` to your required version.
* The default node version is 12.8. To change, open "pom.xml" and change the value of ``` <project> <build> <plugins> <plugin> <version> <configuration> <nodeVersion> ``` to your required version.
* Change the database url, username and password in "./src/resource/application.properties" if desired

## Constraints

This app is developed between the following constraints:
* Integrate with twitter API. (Eg. Filter Realtime Tweets API ​ OR​ Filtered Stream API of Twitter)
        a. https://developer.twitter.com/en/docs/twitter-api/v1/tweets/filter-realtime/api-reference/post-statuses-filter      
        b. https://developer.twitter.com/en/docs/twitter-api/tweets/filtered-stream/api-reference
* Store the tweets in the database and show the tweets on a webpage.
* Do NOT use any existing feature/capability libraries for integrating with twitter (eg., twitter4j).
* You can use any HTTP library to fetch the data.
* The twitter API client or consumer should not be restarted to accommodate any change in followers or keywords to track.
* Minimum 80% code coverage using any testing library

## Usage

Using the frontend:
* Go to the live version or localhost:8080 
* Add filters based on type and click on "add this"
* You can add mutilple filters of any of these types
* Click on "Checkout tweets" to see tweets matching these filters
* On this next page, you'll find all tweets.
* You can add more filters in the "add filter" section on top
* You can remove filters by pressing the cross next to the filter in the "filters" side-bar

Using the database:
* Go to the route '/db' (i.e. live_version_url/db or localhost:8080/db)
* Enter the database url, username and password
* Interact with the Tweets table

Using only backend:
* Use these routes with thier respective playloads:
    | Route          | HTTP Method | Payload type       | Description |
    | ---            | ---         | ---                | ---         |
    | /test          | GET         | -                  | For testing |
    | /add_filter    | POST        | SenderAddFilter    | Create (add) filter |
    | /filters       | GET         | -                  | Read All (show) filters |
    | /delete_filter | POST        | SenderDeleteFilter | Delete filter(s) |
    | /tweets        | GET         | -                  | Retrieve on tweet at a time but keep the connection alive, send again to get next tweet |
    | /stop_tweets   | GET         | -                  | closes the connection started by /tweets |


## Live Version
Look at this web app [here](https://twitter-fetch-419.herokuapp.com/)
