import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NewFoodDishComponent } from './new-food-dish.component';

describe('NewFoodDishComponent', () => {
  let component: NewFoodDishComponent;
  let fixture: ComponentFixture<NewFoodDishComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [NewFoodDishComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(NewFoodDishComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
