import {Component, DestroyRef, OnInit} from '@angular/core';
import {FoodDishModel} from "../../../shared/models/food-dish.model";
import {FoodDishService} from "../../../core/service/food-dish/food-dish.service";
import {takeUntilDestroyed} from "@angular/core/rxjs-interop";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {UserModel} from "../../../shared/models/user.model";
import {getUser} from "../../../core/service/user/user.service";

@Component({
  selector: 'app-foods',
  templateUrl: './foods.component.html',
  styleUrl: './foods.component.scss'
})
export class FoodsComponent implements OnInit {

  foodDishes: FoodDishModel[] = [];
  pagesArray: number[] = [];
  pageSize: number = 5;
  pageNumber: number = 0;
  maximumPageNumber: number = 0;
  searchForm: FormGroup = new FormGroup({});
  loggedUser: UserModel = getUser();

  constructor(
    private foodDishService: FoodDishService,
    private formBuilder: FormBuilder,
    private destroyRef: DestroyRef) {
  }

  ngOnInit(): void {
    this.buildSearchForm();
    this.getFoodDishes();
  }

  private buildSearchForm(): void {
    this.searchForm = this.formBuilder.group({
      name: ['', [Validators.required]]
    });
  }

  private getFoodDishes(): void {
    this.foodDishService.getAll(this.pageNumber, this.pageSize).pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe({
        next: response => {
          this.foodDishes = response.items;
          this.maximumPageNumber = Math.ceil(response.total / this.pageSize);
          this.generatePageArray();
        },
        error: err => console.log(err)
      });
  }

  addToCart(foodDishId: string): void {
    const cart = localStorage.getItem("cart");
    if (cart != null) {
      let listFoodDishes = JSON.parse(cart);
      listFoodDishes.push(foodDishId);
      localStorage.setItem("cart", JSON.stringify(listFoodDishes));
    } else {
      let listFoodDishes = [foodDishId];
      localStorage.setItem("cart", JSON.stringify(listFoodDishes));
    }
  }

  generatePageArray(): void {
    this.pagesArray = Array(this.maximumPageNumber).fill(0).map((x, i) => i + 1);
  }

  incrementPageNumber(): void {
    if (this.pageNumber <= this.maximumPageNumber) {
      this.pageNumber++;
    }
    this.getFoodDishes();
  }

  decrementPageNumber(): void {
    if (this.pageNumber > 0) {
      this.pageNumber--;
    }
    this.getFoodDishes();
  }

  goToPage(page: number): void {
    this.pageNumber = page - 1;
    this.getFoodDishes();
  }

  getAllByName(): void {
    let name = this.searchForm?.get('name')?.value;
    this.pageNumber = 0;
    this.foodDishService.getAll(this.pageNumber, this.pageSize, name).pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe({
        next: response => {
          this.foodDishes = response.items;
          this.maximumPageNumber = Math.ceil(response.total / this.pageSize);
          this.generatePageArray();
        },
        error: err => console.log(err)
      });
  }

  deleteFoodDish(foodDishId: string): void {
    this.foodDishService.deleteFoodDish(foodDishId)
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe({
        next: () => {
          this.foodDishes = this.foodDishes.filter(
            foodDish => foodDish.id !== foodDishId
          );
          this.getFoodDishes();
        }
      });
  }
}
