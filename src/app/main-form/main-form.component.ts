import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { TWEETS } from '../mock-tweets';
import { Tweet } from '../tweet';

@Component({
  selector: 'app-main-form',
  templateUrl: './main-form.component.html',
  styleUrls: ['./main-form.component.css']
})
export class MainFormComponent implements OnInit {
  tweets: Tweet[] = [];

  constructor(private http: HttpClient) {
    // http.get("/tweets").subscribe(response => this.tweets.push(response['data']));
  }

  ngOnInit(): void {
  }

}
