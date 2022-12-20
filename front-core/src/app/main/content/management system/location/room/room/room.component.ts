import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { UtilService } from 'src/app/main/util.service';
import { Location } from '../../location.model';
import { RoomFormDialogComponent } from '../room-form-dialog/room-form-dialog.component';
import { Room } from '../room.model';
import { RoomService } from '../room.service';

@Component({
  selector: 'app-room',
  templateUrl: './room.component.html',
  styleUrls: ['./room.component.sass']
})
export class RoomComponent implements OnInit {

  dataSource: MatTableDataSource<Room>;
  rooms: Room[];
  elements: any[];
  @Input() practiceLocation: Location;
  private paginatorSet: boolean;

  displayedColumns = [
    "name",
    "description",
    "actions"
  ];

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;
  constructor(private roomService: RoomService,
              private utilService: UtilService,
              public dialog: MatDialog,) { }

  ngOnInit(): void {
    this.loadData();
  }

  loadData() {
    this.getAllRooms();
  }

  getAllRooms() {
    this.roomService.getAllRoomsByPracticeLocationId(this.practiceLocation.id).subscribe((response: any) => {
      this.rooms = response;
        this.createTableData();
    });
  }

  createTableData() {
    this.dataSource = new MatTableDataSource<Room>(this.rooms);
    // if (!this.paginatorSet) {
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
      this.paginatorSet = true;
    // }
  }


  addNew() {
    const dialogRef = this.dialog.open(RoomFormDialogComponent, {
      data: {
        action: "create",
        practiceLocation: this.practiceLocation
      }
    });
    dialogRef.afterClosed().subscribe((result) => {
      if (result === 1) {
        this.getAllRooms();
        this.utilService.showNotification( 
          "snackbar-success",
          "Add Employee Successfully!",
          "bottom",
          "center"
        )
      }
    });
  }

  edit(room: Room) {

  }

  deleteItem(roomId: number) {

  }



}
