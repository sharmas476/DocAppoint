import { TestBed } from '@angular/core/testing';

import { TimeoffService } from './timeoff.service';

describe('TimeoffService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: TimeoffService = TestBed.get(TimeoffService);
    expect(service).toBeTruthy();
  });
});
