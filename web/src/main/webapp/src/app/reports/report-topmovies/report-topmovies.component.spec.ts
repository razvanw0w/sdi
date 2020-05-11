import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {ReportTopmoviesComponent} from './report-topmovies.component';

describe('ReportTopmoviesComponent', () => {
  let component: ReportTopmoviesComponent;
  let fixture: ComponentFixture<ReportTopmoviesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ReportTopmoviesComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReportTopmoviesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
