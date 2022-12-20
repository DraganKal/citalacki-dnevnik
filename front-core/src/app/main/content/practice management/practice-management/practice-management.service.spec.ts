import { TestBed } from '@angular/core/testing';

import { PracticeManagementService } from './practice-management.service';

describe('PracticeManagementService', () => {
  let service: PracticeManagementService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PracticeManagementService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
