import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ParkingLayoutComponent } from './parking-layout.component';

describe('ParkingLayoutComponent', () => {
  let component: ParkingLayoutComponent;
  let fixture: ComponentFixture<ParkingLayoutComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ParkingLayoutComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ParkingLayoutComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
