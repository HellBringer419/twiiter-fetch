import { Component, OnInit, NgZone } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { pipe, of } from 'rxjs';
import { concatMap, map, switchAll, switchMap } from 'rxjs/operators';

import { Tweet } from '../tweet_json/tweet';
import { Filter } from '../filter-json/filter';
import { SenderAddFilter } from '../filter-json/sender-add-filter';
import { SenderDeleteFilter } from '../filter-json/sender-delete-filter';
import { TWEETS } from '../mock-tweets';

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

  constructor(private http: HttpClient, private zone: NgZone) {
  }
  
  ngOnInit(): void {
    this.initializeFilters();

    this.initializeTweets(20);
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
      (error) => console.log(error)
    );
  }

  // TODO: add pagination: https://stackoverflow.com/a/58967883/9274057
  initializeTweets(numberOfCallsLeft: number): void {
    // this.tweets = TWEETS;

    // Recursive method and this DOES work amazingly fast
    if (numberOfCallsLeft < 1) {
      return;
    }
    else {
      this.http.get<Tweet>("/tweets").subscribe((tweet: Tweet) => {
        tweet.created_at = new Date(tweet.created_at);
        
        this.tweets = [...this.tweets, tweet];
        return this.initializeTweets(numberOfCallsLeft - 1);
      });
    }

    // const arr: { url: string }[] = [{ url: "/tweets" }, { url: "/tweets" }];
    // console.log("working on getting the tweets");

    // // TODO: Get more than one tweet
    // of(arr).pipe(switchMap(e => this.http.get<Tweet>(e[0].url))).subscribe((tweet: Tweet) => {
    //   this.tweets = [...this.tweets, tweet];
    //   console.log("must have got tweet, tweets right now");
    //   // console.log(JSON.stringify(this.tweets, null, 4));
    //   console.log(tweet);
      
    // });

    //   of(arr).pipe(
    //    concatMap(r=> http.get(r.url)), //MAKE EACH REQUEST AND WAIT FOR COMPLETION
    //    toArray(), // COMBINE THEM TO ONE ARRAY
    //    switchMapTo(http.get("FINALURL") // MAKE REQUEST AFTER EVERY THING IS FINISHED
    // )).subscribe()

    // http.get<Tweet>("/tweets").pipe<Tweet>(
      //   // TODO: check an RxJs operator to use at : https://angular.io/guide/rx-library
      //   map(tweet => tweet)
    // ).subscribe(response => this.tweets.push(response['data']));

    // Help for pagination .. more at: https://stackoverflow.com/questions/55266191/angular-repeating-the-same-subscribe-http-request
    // fromEvent(this.buttonRef.nativeElement, 'click')
    //   .pipe(switchMap(() => this.http.get('whatever page number'))
    //     .subscribe(console.log));
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
      // test this filter
    }

    const senderAddFilter: SenderAddFilter = {
      add: [toAddFilter]
    }
    this.http.post("/add_filter", senderAddFilter).subscribe(() => {
      console.log("added this new filter from subs");

      // filters stored in toAddFilter do not have ids, syncing this.filters with backend
      this.fetchNewFiltersAndAddThem();
    });

    this.filterText = "";
    this.filterType = "Type of Filter";
  }

  fetchNewFiltersAndAddThem(): void {
    console.log("started setting filters");
    this.http.get<Filter[]>("/filters").subscribe((filters: Filter[]) => {
      if (filters != null) {
        for (let filter of filters) {
          if (!this.tempFilterIds.has(filter.id)) {
            // this.zone.run(() => this.filters.push(filter));
            // this.filters.push(filter);
            this.filters = [...this.filters, filter]

            this.tempFilterIds.add(filter.id);
            console.log("added filters to ngModel on page 2, Showing filters array: ");
            console.log(this.filters);
            
            this.initializeTweets(20);
          }
        }
      }
      else {
        console.log("null filters");
      }
    },
      (error) => console.log(error)
    );
  }

  removeFilter(toDeleteFilter: Filter): void {
    const senderDeleteFilter: SenderDeleteFilter = {
      delete: {
        ids: [toDeleteFilter.id]
      }
    }
    this.http.post("/delete_filter", senderDeleteFilter).subscribe(() => {
      console.log("deleted filter on page 2 when requested");
      const deletedFilters = this.filters.splice(this.filters.indexOf(toDeleteFilter), 1);
      console.log("these got deleted");
      console.log(deletedFilters);
      console.log("showing filters array: ");
      console.log(this.filters);

      this.initializeTweets(20);
    });
  }
}
