import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PracticeManagementDeleteDialogComponent } from './practice-management-delete-dialog.component';

describe('PracticeManagementDeleteDialogComponent', () => {
  let component: PracticeManagementDeleteDialogComponent;
  let fixture: ComponentFixture<PracticeManagementDeleteDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PracticeManagementDeleteDialogComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PracticeManagementDeleteDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
