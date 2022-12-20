import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LocationDeleteDialogComponent } from './location-delete-dialog.component';

describe('LocationDeleteDialogComponent', () => {
  let component: LocationDeleteDialogComponent;
  let fixture: ComponentFixture<LocationDeleteDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LocationDeleteDialogComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(LocationDeleteDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
