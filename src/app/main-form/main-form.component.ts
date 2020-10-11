import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { TWEETS } from '../mock-tweets';
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

  filterTypes : string[] = [
    "follower",
    "keyword",
    "hashtag"
  ];
  filterText: string;
  filterType: string;

  constructor(private http: HttpClient) {
    // http.get("/tweets").subscribe(response => this.tweets.push(response['data']));
  }

  ngOnInit(): void {
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
    this.http.post("/add_filter", senderAddFilter).subscribe();
    this.filterText = "";
  }

}
