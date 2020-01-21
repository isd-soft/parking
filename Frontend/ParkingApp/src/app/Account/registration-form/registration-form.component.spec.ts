import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RegFormComponent } from './registration-form.component';

describe('RegFormComponent', () => {
  let component: RegFormComponent;
  let fixture: ComponentFixture<RegFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RegFormComponent ]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RegFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
