import { Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { LocationDeleteDialogComponent } from '../location-delete-dialog/location-delete-dialog.component';
import { Location } from '../location.model';
import { LocationService } from '../location.service';

@Component({
  selector: 'app-location',
  templateUrl: './location.component.html',
  styleUrls: ['./location.component.sass']
})
export class LocationComponent implements OnInit {

  dataSource: MatTableDataSource<any>;
  locations: Location[] = new Array;
  elements;

  resultsLength = 0;
  pageSize = 10;

  private paginatorSet: boolean;

  displayedColumns = [
    "status",
    "locationType",
    "code",
    "city",
    "zip",
    "street",
    "houseNumber",
    "practice",
    "actions"
  ];

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;
  constructor(private router: Router,
              private locationService: LocationService,
              public dialog: MatDialog,) { }

  ngOnInit(): void {

    this.getAllLocations();

  }

  loadData() {
    this.getAllLocations();
  }

  getAllLocations() {
    this.locationService.getAllLocations().subscribe(
      ( res:[]) => {
         this.locations = res;
         this.resultsLength = +res.length;
         this.createTableData();
       }
      )
  }

  createTableData() {
    this.dataSource = new MatTableDataSource<Location>(this.locations);
    // if (!this.paginatorSet) {
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
      this.paginatorSet = true;
    // }
  }

  addNew() {
    this.router.navigateByUrl("/locations/add-location");
  }

  editLocation(locationId: Number) {
    this.router.navigateByUrl("/locations/edit/" + locationId);
  }

  deleteLocation(location: Location) {
    const dialogRef = this.dialog.open(LocationDeleteDialogComponent, {
      data: {
        location: location,
      },
    });

    dialogRef.afterClosed().subscribe((result) => {
      if(result == 1) {
       this.getAllLocations();
      }
    })
  }

}
