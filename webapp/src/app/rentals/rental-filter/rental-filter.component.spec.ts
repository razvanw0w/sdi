import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {RentalFilterComponent} from './rental-filter.component';

describe('RentalFilterComponent', () => {
  let component: RentalFilterComponent;
  let fixture: ComponentFixture<RentalFilterComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [RentalFilterComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RentalFilterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
