import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { UtilService } from 'src/app/main/util.service';
import { Location } from '../../location.model';
import { Room } from '../room.model';
import { RoomService } from '../room.service';

@Component({
  selector: 'app-room-form-dialog',
  templateUrl: './room-form-dialog.component.html',
  styleUrls: ['./room-form-dialog.component.sass']
})
export class RoomFormDialogComponent implements OnInit {

  action: string;
  room: Room;
  practiceLocation: Location;

  roomForm: FormGroup;

  constructor(public dialogRef: MatDialogRef<RoomFormDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public data: any,
              public roomService: RoomService,
              private formBuilder: FormBuilder,
              private router: Router,
              private snackBar: MatSnackBar,
              private utilService: UtilService) {
    this.action = data.action;
    this.practiceLocation = data.practiceLocation;
    if (this.action === "edit") {
      this.room = data.room;
    } else {
      this.room = new Room({});
    }
  }

  ngOnInit(): void {
    this.createRoomForm();
  }

  createRoomForm() {
    if(this.action === 'edit'){
      this.roomForm = this.formBuilder.group({
        id:[this.room.id],
        name: [this.room.name, Validators.required],
        description: [this.room.description, Validators.required],
        logopedicPracticeId: [this.room.logopedicPracticeId],
        practiceLocationId: [this.room.practiceLocationId],
      });
    } else {
      this.roomForm = this.formBuilder.group({
        id:[],
        name: ['',  Validators.required],
        description: ['',  Validators.required],
        logopedicPracticeId: [this.practiceLocation.practiceId],
        practiceLocationId: [this.practiceLocation.id],
      });
    }
  }

  submit() {
    this.room.id = this.roomForm.controls['id'].value;
    this.room.name = this.roomForm.controls['name'].value;
    this.room.description = this.roomForm.controls['description'].value;
    this.room.logopedicPracticeId = this.roomForm.controls['logopedicPracticeId'].value;
    this.room.practiceLocationId = this.roomForm.controls['practiceLocationId'].value;

    if(this.action === 'edit') {
      // this.roomService.updateRoom(this.room.id, this.room).subscribe((res:any)=>{
      //   this.dialogRef.close(1);   
      // }, (error) => {
      //   this.utilService.showNotification(
      //     "snackbar-error",
      //     error.error.message,
      //     "bottom",
      //     "center"
      //   );
      // })
    } else {
      this.roomService.saveRoom(this.room).subscribe((res:any)=>{
        this.dialogRef.close(1);   
      }, (error) => {
        this.utilService.showNotification(
          "snackbar-error",
          error.error.message,
          "bottom",
          "center"
        );
      })
    }
      
    
  }



}
