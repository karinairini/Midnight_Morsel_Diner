import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FoodDishModel} from "../../../../shared/models/food-dish.model";
import {FoodDishForOrder} from "../../../../shared/models/food-dish-for-order.model";

@Component({
  selector: 'app-food-card',
  templateUrl: './food-card.component.html',
  styleUrl: './food-card.component.scss'
})
export class FoodCardComponent implements OnInit {

  @Input() food!: FoodDishModel;
  @Input() foodForOrder!: FoodDishForOrder;
  @Input() isCart!: boolean;
  @Input() isOrder!: boolean;
  @Input() isEmployee!: boolean;
  @Output() addFoodDishToCartEvent: EventEmitter<string> = new EventEmitter<string>();
  @Output() removeFromCartEvent: EventEmitter<string> = new EventEmitter<string>();
  @Output() deleteFoodDishEvent: EventEmitter<string> = new EventEmitter<string>();

  constructor() {
  }

  ngOnInit(): void {
    this.initStarRating();
  }

  initStarRating(): void {
    const starsInner = document.querySelector('.stars-inner') as HTMLElement | null;
    if (starsInner) {
      const starPercentage = (this.food.rating / 5) * 100;
      starsInner.style.width = `${Math.round(starPercentage / 10) * 10}%`;
      const element = document.querySelector('.number-rating');
      if (element) {
        element.innerHTML = this.food.rating.toString();
      }
    }
  }

  addToCart(foodDishId: string): void {
    this.addFoodDishToCartEvent.emit(foodDishId);
  }

  removeFromCart(foodDishId: string): void {
    this.removeFromCartEvent.emit(foodDishId);
  }

  deleteFoodDish(foodDishId: string): void {
    this.deleteFoodDishEvent.emit(foodDishId);
  }
}
