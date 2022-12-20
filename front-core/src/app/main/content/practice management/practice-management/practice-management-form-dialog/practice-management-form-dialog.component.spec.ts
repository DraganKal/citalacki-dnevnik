import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PracticeManagementFormDialogComponent } from './practice-management-form-dialog.component';

describe('PracticeManagementFormDialogComponent', () => {
  let component: PracticeManagementFormDialogComponent;
  let fixture: ComponentFixture<PracticeManagementFormDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PracticeManagementFormDialogComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PracticeManagementFormDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
