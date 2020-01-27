import { TestBed } from '@angular/core/testing';

import { PrefetchStatsService } from './prefetch-stats.service';

describe('PrefectchStatsService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: PrefetchStatsService = TestBed.get(PrefetchStatsService);
    expect(service).toBeTruthy();
  });
});
