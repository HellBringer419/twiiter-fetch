import { Component, OnInit, NgZone } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { Tweet } from '../tweet';
import { Filter } from '../filter-json/filter';
import { SenderAddFilter } from '../filter-json/sender-add-filter';

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
  filterType: string;

  filters: Filter[];
  tempFilterIds: Set<string> = new Set<string>();

  constructor(private http: HttpClient, private zone: NgZone) {
    // http.get("/tweets").subscribe(response => this.tweets.push(response['data']));
  }
  
  ngOnInit(): void {
    this.http.get<Filter[]>("/filters").subscribe((filters: Filter[]) => {
      if (filters != null) {
        this.zone.run(() => this.filters = filters);
        for(let filter of filters) {
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
      this.changeFilters();
    });
    
    this.filterText = "";
  }

  changeFilters(): void {
    console.log("started setting filters");
    
    this.http.get<Filter[]>("/filters").subscribe((filters: Filter[]) => {
      if (filters != null) {
        for (let filter of filters) {
          if (!this.tempFilterIds.has(filter.id)) {
            // this.zone.run(() => this.filters.push(filter));
            this.filters.push(filter);
            this.tempFilterIds.add(filter.id);
            console.log("added filters to ngModel on page 2");   
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

  // TODO: delete selected filters

}
