import { Component, OnInit, NgZone } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { Tweet } from '../tweet_json/tweet';
import { Filter } from '../filter-json/filter';
import { SenderAddFilter } from '../filter-json/sender-add-filter';
import { SenderDeleteFilter } from '../filter-json/sender-delete-filter';

@Component({
  selector: 'app-main-form',
  templateUrl: './main-form.component.html',
  styleUrls: ['./main-form.component.css']
})
export class MainFormComponent implements OnInit {
  tweets: Tweet[] = [];

  filterTypes: string[] = [
    "follower",
    "keyword",
    "hashtag"
  ];
  filterText: string;
  filterType: string = "Type of Filter";

  filters: Filter[];
  tempFilterIds: Set<string> = new Set<string>();

  currentPage: number = 1;

  constructor(private http: HttpClient, private zone: NgZone) {
  }
  
  ngOnInit(): void {
    this.initializeFilters();

    this.initializeTweets(100);
  }

  initializeFilters(): void {
    this.http.get<Filter[]>("/filters").subscribe((filters: Filter[]) => {
      if (filters != null) {
        this.zone.run(() => this.filters = filters);
        for (let filter of filters) {
          this.tempFilterIds.add(filter.id);
        }
        console.log("added filters to its ngModel page 2");
      }
      else {
        console.log("null filters");
      }
    },
      (error) => console.log(error + "\n at initializeFilters(): main-form ")
    );
  }

  // Recursive method and this DOES work amazingly fast
  initializeTweets(numberOfCallsLeft: number): void {
    if (numberOfCallsLeft < 1) {
      console.log("initialized all tweets");
      
      return;
    }
    else {
      this.http.get<Tweet>("/tweets").subscribe((tweet: Tweet) => {
        if (tweet != null) {
          tweet.created_at = new Date(tweet.created_at);
          
          this.tweets = [...this.tweets, tweet];
          return this.initializeTweets(numberOfCallsLeft - 1);
        }
        else {
          console.log("tweet hasn't arrived yet");
        }
      },
        (error) => console.log("error at initializeTweets(): main-form ")
      );
    }
  }

  selectType(filterType: string): void {
    this.filterType = filterType;
  }

  addFilter(filterText: string, filterType: string): void {
    let toAddFilter: Filter = {
      value: filterText,
      tag: filterType,
      id: null
    }
    if (filterType === "follower") {
      toAddFilter.value = "@" + filterText;
    }
    else if (filterType === "keyword") {
      toAddFilter.value = filterText;
    }
    else if (filterType === "hashtag") {
      toAddFilter.value = "#" + filterText;
    }
    else {
      console.log("Can't add filter without type");
      return;
    }

    const senderAddFilter: SenderAddFilter = {
      add: [toAddFilter]
    }
    this.http.post("/add_filter", senderAddFilter).subscribe(() => {

      // filters stored in toAddFilter do not have ids, syncing this.filters with backend
      this.fetchNewFiltersAndAddThem();
    });

    this.filterText = "";
    this.filterType = "Type of Filter";
  }

  fetchNewFiltersAndAddThem(): void {
    this.http.get<Filter[]>("/filters").subscribe((filters: Filter[]) => {
      if (filters != null) {
        for (let filter of filters) {
          if (!this.tempFilterIds.has(filter.id)) {

            // order of tweets: new at last
            this.filters = [...this.filters, filter]

            this.tempFilterIds.add(filter.id);
            console.log("added new filter from input");
            
            this.stopAndReinitalizeTweets();
          }
        }
      }
      else {
        console.log("null filters");
      }
    },
      (error) => console.log("error at fetchNewFiltersAndAddThem(): main-form ")
    );
  }

  removeFilter(toDeleteFilter: Filter): void {
    const senderDeleteFilter: SenderDeleteFilter = {
      delete: {
        ids: [toDeleteFilter.id]
      }
    }
    this.http.post("/delete_filter", senderDeleteFilter).subscribe(() => {
      const deletedFilters = this.filters.splice(this.filters.indexOf(toDeleteFilter), 1);
      console.log("deleted filter on page 2 when requested");
      
      this.stopAndReinitalizeTweets();
    },
      (error) => console.log("error at removeFilter(): main-form ")
    );
  }
  
  stopAndReinitalizeTweets(): void {
    this.http.get("/stop_tweets").subscribe(() => {
      console.log("trying to re-initialize tweets");
      
      this.clearExistingTweets();
      
      // Added a time-out to avoid reaching api rate limit (retries after 300ms)
      setTimeout(() => this.initializeTweets(100), 300);
      this.currentPage = 1;
    },
      (error) => console.log("error at stopAndReinitalizeTweets(): main-form ")
    );
  }

  clearExistingTweets(): void {
    this.tweets = [];
  }
}
