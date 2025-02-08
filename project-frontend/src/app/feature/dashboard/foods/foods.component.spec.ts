import {ComponentFixture, TestBed} from '@angular/core/testing';

import {FoodsComponent} from './foods.component';
import {FoodDishService} from "../../../core/service/food-dish/food-dish.service";
import {FoodCardComponent} from "./food-card/food-card.component";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {CommonModule} from "@angular/common";
import {HttpClientModule} from "@angular/common/http";
import {NO_ERRORS_SCHEMA} from "@angular/core";
import {PageModel} from "../../../shared/models/page.model";
import {of} from "rxjs";

describe('FoodsComponent', () => {
  let component: FoodsComponent;
  let fixture: ComponentFixture<FoodsComponent>;
  let foodDishService: FoodDishService;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HttpClientModule, CommonModule, FormsModule, ReactiveFormsModule],
      declarations: [FoodsComponent, FoodCardComponent],
      schemas: [NO_ERRORS_SCHEMA]
    })
      .compileComponents();

    fixture = TestBed.createComponent(FoodsComponent);
    component = fixture.componentInstance;
    foodDishService = TestBed.inject(FoodDishService);
  });

  it('should create', () => {
    //when
    fixture.detectChanges();

    //then
    expect(component).toBeTruthy();
  });

  it('should get all food dishes when button is pressed', () => {
    //given
    fixture.detectChanges();
    const button: HTMLButtonElement = fixture.debugElement.nativeElement.querySelector('#search-button');
    const buttonSpy = spyOn(component as any, 'getAllByName').and.callThrough();
    const serviceSpy = spyOn(foodDishService as any, 'getAll');

    //when
    button.click();

    //then
    expect(buttonSpy).toHaveBeenCalled();
    expect(serviceSpy).toHaveBeenCalled();
  });

  it('should display food dishes', () => {
    //given
    const foodDish = {id: '00000000-0000-0000-0000-000000000000', name: 'Butter chicken', price: 5.0, rating: 4.5};
    const pageModel: PageModel = {
      items: [foodDish],
      total: 1
    };
    const serviceSpy = spyOn(foodDishService as any, 'getAll').and.returnValue(of(pageModel));

    //when
    fixture.detectChanges();

    //then
    expect(serviceSpy).toHaveBeenCalled();
    expect(fixture.debugElement.nativeElement.querySelector('app-food-card')).toBeTruthy();
    expect(fixture.debugElement.nativeElement.querySelector('#food-dishes-empty')).toBeFalsy();
  });

  it('should display no food dishes', () => {
    //given
    const pageModel: PageModel = {
      items: [],
      total: 0
    };
    spyOn(foodDishService as any, 'getAll').and.returnValue(of(pageModel));

    //when
    fixture.detectChanges();

    //then
    expect(fixture.debugElement.nativeElement.querySelector('app-food-card')).toBeFalsy();
    expect(fixture.debugElement.nativeElement.querySelector('#food-dishes-empty')).toBeTruthy();
  });

  it('should add food dish to cart when button is pressed', () => {
    //given
    const foodDish = {id: '00000000-0000-0000-0000-000000000000', name: 'Butter chicken', price: 5.0, rating: 4.5};
    const pageModel: PageModel = {
      items: [foodDish],
      total: 1
    };
    spyOn(foodDishService as any, 'getAll').and.returnValue(of(pageModel));

    fixture.detectChanges();
    const button: HTMLButtonElement = fixture.debugElement.nativeElement.querySelector('#add-to-cart-button');
    const buttonSpy = spyOn(component as any, 'addToCart').and.callThrough();

    //when
    button.click();

    //then
    expect(buttonSpy).toHaveBeenCalled();
  });

  it('should get food dishes paginated when previous button is pressed', () => {
    //given
    fixture.detectChanges();
    const button: HTMLButtonElement = fixture.debugElement.nativeElement.querySelector('#previous');
    const buttonSpy = spyOn(component as any, 'decrementPageNumber').and.callThrough();
    const serviceSpy = spyOn(foodDishService as any, 'getAll');

    //when
    button.click();

    //then
    expect(buttonSpy).toHaveBeenCalled();
    expect(serviceSpy).toHaveBeenCalled();
  });
});
