import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {ReportClientgenresComponent} from './report-clientgenres.component';

describe('ReportClientgenresComponent', () => {
  let component: ReportClientgenresComponent;
  let fixture: ComponentFixture<ReportClientgenresComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ReportClientgenresComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReportClientgenresComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
