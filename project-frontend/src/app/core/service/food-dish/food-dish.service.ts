import {DestroyRef, Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {map, Observable} from "rxjs";
import {takeUntilDestroyed} from "@angular/core/rxjs-interop";
import {FoodDishForOrder} from "../../../shared/models/food-dish-for-order.model";
import {PageModel} from "../../../shared/models/page.model";
import {FoodDishModel} from "../../../shared/models/food-dish.model";

@Injectable({
  providedIn: 'root'
})
export class FoodDishService {

  constructor(
    private http: HttpClient,
    private destroyRef: DestroyRef) {
  }

  getAll(pageNumber: number = 0, pageSize: number = 20, name: string = ""): Observable<PageModel> {
    return this.http.get<PageModel>(`foods`, {params: {pageNumber, pageSize, name}})
      .pipe(takeUntilDestroyed(this.destroyRef));
  }

  getFoodDishOrder(foodDishId: string): Observable<FoodDishForOrder> {
    return this.http.get<FoodDishForOrder>(`foods/${foodDishId}`)
      .pipe(takeUntilDestroyed(this.destroyRef),
        map(response => response));
  }

  saveFoodDish(name: string, price: number, rating: number): Observable<FoodDishModel> {
    return this.http.post<FoodDishModel>(`foods`, {name, price, rating})
      .pipe(takeUntilDestroyed(this.destroyRef), map((response: FoodDishModel) => response));
  }

  deleteFoodDish(foodDishId: string): Observable<void> {
    return this.http.delete<void>(`foods/${foodDishId}`)
      .pipe(takeUntilDestroyed(this.destroyRef));
  }
}
