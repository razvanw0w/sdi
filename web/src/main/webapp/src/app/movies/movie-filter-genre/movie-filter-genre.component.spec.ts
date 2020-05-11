import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {MovieFilterGenreComponent} from './movie-filter-genre.component';

describe('MovieFilterGenreComponent', () => {
  let component: MovieFilterGenreComponent;
  let fixture: ComponentFixture<MovieFilterGenreComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [MovieFilterGenreComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MovieFilterGenreComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
