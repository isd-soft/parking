import { TestBed } from '@angular/core/testing';

import { PrefectchParkingLotsService } from './prefectch-parking-lots.service';

describe('PrefectchParkingLotsService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: PrefectchParkingLotsService = TestBed.get(PrefectchParkingLotsService);
    expect(service).toBeTruthy();
  });
});
