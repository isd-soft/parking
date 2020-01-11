import { TestBed } from '@angular/core/testing';

import { PrefetchParkingLotsService } from './prefetch-parking-lots.service';

describe('PrefectchParkingLotsService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: PrefetchParkingLotsService = TestBed.get(PrefetchParkingLotsService);
    expect(service).toBeTruthy();
  });
});
