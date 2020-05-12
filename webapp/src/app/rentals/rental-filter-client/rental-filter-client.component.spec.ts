import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {RentalFilterClientComponent} from './rental-filter-client.component';

describe('RentalFilterClientComponent', () => {
  let component: RentalFilterClientComponent;
  let fixture: ComponentFixture<RentalFilterClientComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [RentalFilterClientComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RentalFilterClientComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
