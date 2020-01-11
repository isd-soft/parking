import { TestBed } from '@angular/core/testing';

import { PrefectchStatsService } from './prefectch-stats.service';

describe('PrefectchStatsService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: PrefectchStatsService = TestBed.get(PrefectchStatsService);
    expect(service).toBeTruthy();
  });
});
