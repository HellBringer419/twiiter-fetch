import { Component, OnInit } from '@angular/core';
import { Filter, FilterType } from '../filter';
import { Router } from '@angular/router';

@Component({
  selector: 'app-initial-form',
  templateUrl: './initial-form.component.html',
  styleUrls: ['./initial-form.component.css']
})

export class InitialFormComponent implements OnInit {
  filters: Filter[] = [];
  followerInput: string;
  keywordInput: string;

  constructor(private router: Router) {  }

  ngOnInit(): void {
  }

  addFollowerFilter(followerInput: string): void {
    const filter: Filter = {
      name: followerInput,
      type: FilterType.Follower
    }
    this.followerInput = '';
    this.filters.push(filter);
  }

  addKeywordFilter(keywordInput: string): void {
    const filter: Filter = {
      name: keywordInput,
      type: FilterType.Keyword
    }
    this.followerInput = '';
    this.filters.push(filter);
  }

  submitForm():void {
    this.router.navigate(['/start']);
  }
}
