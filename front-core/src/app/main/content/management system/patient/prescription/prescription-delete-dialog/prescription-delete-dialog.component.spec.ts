import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PrescriptionDeleteDialogComponent } from './prescription-delete-dialog.component';

describe('PrescriptionDeleteDialogComponent', () => {
  let component: PrescriptionDeleteDialogComponent;
  let fixture: ComponentFixture<PrescriptionDeleteDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PrescriptionDeleteDialogComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PrescriptionDeleteDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
