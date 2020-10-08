import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';

import {Filter} from '../filter-json/filter'

@Component({
  selector: 'app-initial-form',
  templateUrl: './initial-form.component.html',
  styleUrls: ['./initial-form.component.css']
})

export class InitialFormComponent implements OnInit {
  filters: Filter[] = [];
  followerInput: string;
  keywordInput: string;
  
  followerFilters: Filter[] = [];
  keywordFilters: Filter[] = [];

  constructor(private router: Router, private http: HttpClient) {  }

  ngOnInit(): void {
  }

  addFollowerFilter(followerInput: string): void {
    const filter: Filter = {
      id: null,
      value: followerInput,
      tag: "follower"
    }
    this.followerInput = '';
    this.filters.push(filter);
    this.followerFilters.push(filter);
  }

  addKeywordFilter(keywordInput: string): void {
    const filter: Filter = {
      id: null,
      value: keywordInput,
      tag: "keyword"
    }
    this.keywordInput = '';
    this.filters.push(filter);
    this.keywordFilters.push(filter);
  }

  removeFilter(filter: Filter): void {
    this.filters.splice(this.filters.indexOf(filter), 1);
    if (filter.tag === "follower") {
      this.followerFilters.splice(this.followerFilters.indexOf(filter), 1);
    }
    else if (filter.tag === "keyword") {
      this.keywordFilters.splice(this.keywordFilters.indexOf(filter), 1);
    }
    else {
      console.log("This filter enum is undefined: " + filter.tag + " of filter: " + filter.value);
    }
  }

  submitForm(): void {
    // TODO send a /add_filter request
    // this.http.post("/add_filter", )
    this.router.navigate(['/start']);
  }
}
