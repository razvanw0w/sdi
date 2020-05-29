import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {ClientFilterFidelityComponent} from './client-filter-fidelity.component';

describe('ClientFilterFidelityComponent', () => {
  let component: ClientFilterFidelityComponent;
  let fixture: ComponentFixture<ClientFilterFidelityComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ClientFilterFidelityComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ClientFilterFidelityComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
