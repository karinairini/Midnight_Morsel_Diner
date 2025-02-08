import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReservationFlipCardComponent } from './reservation-flip-card.component';

describe('ReservationFlipCardComponent', () => {
  let component: ReservationFlipCardComponent;
  let fixture: ComponentFixture<ReservationFlipCardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ReservationFlipCardComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ReservationFlipCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
