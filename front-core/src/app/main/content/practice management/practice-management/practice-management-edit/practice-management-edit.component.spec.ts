import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PracticeManagementEditComponent } from './practice-management-edit.component';

describe('PracticeManagementEditComponent', () => {
  let component: PracticeManagementEditComponent;
  let fixture: ComponentFixture<PracticeManagementEditComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PracticeManagementEditComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PracticeManagementEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
