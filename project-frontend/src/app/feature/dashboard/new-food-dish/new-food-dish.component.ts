import {Component, DestroyRef, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {takeUntilDestroyed} from "@angular/core/rxjs-interop";
import {FoodDishService} from "../../../core/service/food-dish/food-dish.service";

@Component({
  selector: 'app-new-food-dish',
  templateUrl: './new-food-dish.component.html',
  styleUrl: './new-food-dish.component.scss'
})
export class NewFoodDishComponent implements OnInit {

  newFoodDishForm: FormGroup = new FormGroup({});
  errorMessage?: string;

  constructor(
    private foodDishService: FoodDishService,
    private formBuilder: FormBuilder,
    private destroyRef: DestroyRef,
    private router: Router) {
  }

  ngOnInit(): void {
    this.buildNewFoodDishForm();
  }

  private buildNewFoodDishForm(): void {
    this.newFoodDishForm = this.formBuilder.group({
      name: ['', [Validators.required]],
      price: ['', [Validators.required]],
      rating: ['', [Validators.required]]
    });
  }

  saveFoodDish(): void {
    let name = this.newFoodDishForm?.get('name')?.value;
    let price = this.newFoodDishForm?.get('price')?.value;
    let rating = this.newFoodDishForm?.get('rating')?.value;

    if (!this.newFoodDishForm?.valid) {
      this.errorMessage = 'Invalid form completion!';
      setTimeout(() => this.errorMessage = undefined, 3000);
      return;
    }

    this.foodDishService.saveFoodDish(name, price, rating)
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe(() => {
        this.router.navigateByUrl("/dashboard/foods");
      });
  }
}
