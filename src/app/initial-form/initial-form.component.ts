import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';

import { Filter } from '../filter-json/filter'
import { SenderAddFilter } from '../filter-json/sender-add-filter';
import { SenderDeleteFilter } from '../filter-json/sender-delete-filter';

@Component({
  selector: 'app-initial-form',
  templateUrl: './initial-form.component.html',
  styleUrls: ['./initial-form.component.css']
})

export class InitialFormComponent implements OnInit {
  filters: Filter[] = [];

  followerInput: string;
  keywordInput: string;
  hashtagInput: string;

  followerFilters: Filter[] = [];
  keywordFilters: Filter[] = [];
  hashtagFilters: Filter[] = []

  constructor(private router: Router, private http: HttpClient) { }

  ngOnInit(): void {
    this.http.get<Filter[]>("/filters").subscribe((toDeleteFilters: Filter[]) => this.deleteExistingFilters(toDeleteFilters));
  }

  deleteExistingFilters(toDeleteFilters: Filter[]) {
    if (toDeleteFilters != null) {
      let toDeleteIds: string[] = [];
      
      toDeleteFilters.forEach(filter => {
        toDeleteIds.push(filter.id);
        
      });
      
      const senderDeleteFilter: SenderDeleteFilter = {
        delete: {
          ids: toDeleteIds
        }
      }
      this.http.post("/delete_filter", senderDeleteFilter).subscribe();
    }
    else {
      console.log("Filters already empty");
    }
  }

  addFollowerFilter(followerInput: string): void {
    const filter: Filter = {
      value: "@" + followerInput,
      tag: "follower",
      id: null
    }
    this.followerInput = '';
    this.filters.push(filter);
    this.followerFilters.push(filter);
  }

  addKeywordFilter(keywordInput: string): void {
    const filter: Filter = {
      value: keywordInput,
      tag: "keyword",
      id: null
    }
    this.keywordInput = '';
    this.filters.push(filter);
    this.keywordFilters.push(filter);
  }

  addHashtagFilter(hashtagInput: string): void {
    const filter: Filter = {
      value: "#" + hashtagInput,
      tag: "hashtag",
      id: null
    }
    this.hashtagInput = '';
    this.filters.push(filter);
    this.hashtagFilters.push(filter);
  }

  removeFilter(filter: Filter): void {
    this.filters.splice(this.filters.indexOf(filter), 1);
    if (filter.tag === "follower") {
      this.followerFilters.splice(this.followerFilters.indexOf(filter), 1);
    }
    else if (filter.tag === "keyword") {
      this.keywordFilters.splice(this.keywordFilters.indexOf(filter), 1);
    }
    else if (filter.tag === "hashtag") {
      this.hashtagFilters.splice(this.hashtagFilters.indexOf(filter), 1);
    }
    else {
      console.log("This filter enum is undefined: " + filter.tag + " of filter: " + filter.value);
    }
  }

  submitForm(): void {
    if (this.filters != null && this.filters.length > 0) {
      const senderAddFilter: SenderAddFilter = {
        add: this.filters
      }
      this.http.post("/add_filter", senderAddFilter).subscribe();
      this.router.navigate(['/start']);
    }
    else {
      console.log("No filters ... can't show tweets");
    }
  }
}
