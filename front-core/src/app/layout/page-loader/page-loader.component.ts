import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
@Component({
  selector: 'app-page-loader',
  templateUrl: './page-loader.component.html',
  styleUrls: ['./page-loader.component.sass'],
})
export class PageLoaderComponent implements OnInit {
  constructor(private router: Router) {}
  ngOnInit() {
    
  }
}
