import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {MovieFilterNameComponent} from './movie-filter-name.component';

describe('MovieFilterNameComponent', () => {
  let component: MovieFilterNameComponent;
  let fixture: ComponentFixture<MovieFilterNameComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [MovieFilterNameComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MovieFilterNameComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
