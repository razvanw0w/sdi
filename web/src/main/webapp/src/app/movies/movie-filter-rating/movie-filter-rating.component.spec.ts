import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {MovieFilterRatingComponent} from './movie-filter-rating.component';

describe('MovieFilterRatingComponent', () => {
  let component: MovieFilterRatingComponent;
  let fixture: ComponentFixture<MovieFilterRatingComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [MovieFilterRatingComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MovieFilterRatingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
