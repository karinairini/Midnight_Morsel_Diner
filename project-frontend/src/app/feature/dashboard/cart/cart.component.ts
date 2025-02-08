import {Component, DestroyRef, OnInit} from '@angular/core';
import {FoodDishService} from "../../../core/service/food-dish/food-dish.service";
import {takeUntilDestroyed} from "@angular/core/rxjs-interop";
import {FoodDishForOrder} from "../../../shared/models/food-dish-for-order.model";
import {OrderService} from "../../../core/service/order/order.service";
import {getUser} from "../../../core/service/user/user.service";
import {OrderModel} from "../../../shared/models/order.model";
import {UserModel} from "../../../shared/models/user.model";

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrl: './cart.component.scss'
})
export class CartComponent implements OnInit {

  cartFoodDishes: FoodDishForOrder[] = [];
  loggedUser: UserModel = getUser();
  totalPrice: number = 0;

  constructor(
    private foodDishService: FoodDishService,
    private orderService: OrderService,
    private destroyRef: DestroyRef) {
  }

  ngOnInit(): void {
    this.populateCart();
  }

  populateCart(): void {
    const cartData = localStorage.getItem("cart");
    if (cartData) {
      const cartIds: string[] = JSON.parse(cartData);
      cartIds.forEach(id => {
        const foodDish = this.foodDishService.getFoodDishOrder(id);
        if (foodDish) {
          foodDish.pipe(takeUntilDestroyed(this.destroyRef))
            .subscribe((response: FoodDishForOrder) => {
              this.cartFoodDishes.push(response);
              this.totalPrice += response.price;
            });
        }
      });
    }
  }

  deleteFoodDishFromCart(foodDishId: string): void {
    const index = this.cartFoodDishes.findIndex(foodDish => foodDish.id === foodDishId);
    if (index !== -1) {
      this.totalPrice -= this.cartFoodDishes[index].price;
      this.cartFoodDishes.splice(index, 1);
      localStorage.setItem("cart", JSON.stringify(this.cartFoodDishes));
    }
  }

  buyCart(): void {
    const cartFoodDishIds: string[] = [];
    this.cartFoodDishes.map(foodDish => {
      cartFoodDishIds.push(foodDish.id);
    });
    this.orderService.createOrder(this.loggedUser.id)
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe((response: OrderModel) => {
        this.orderService.addFoodDishToOrder(response.id, cartFoodDishIds)
          .pipe(takeUntilDestroyed(this.destroyRef))
          .subscribe(() => {
            this.cartFoodDishes = [];
            this.totalPrice = 0;
            localStorage.setItem("cart", JSON.stringify(this.cartFoodDishes));
          });
      });
  }
}
