import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {ClientSortComponent} from './client-sort.component';

describe('ClientSortComponent', () => {
  let component: ClientSortComponent;
  let fixture: ComponentFixture<ClientSortComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ClientSortComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ClientSortComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
