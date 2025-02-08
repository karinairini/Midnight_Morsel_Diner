import {DestroyRef, Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {map, Observable} from "rxjs";
import {takeUntilDestroyed} from "@angular/core/rxjs-interop";
import {OrderModel} from "../../../shared/models/order.model";

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  constructor(
    private http: HttpClient,
    private destroyRef: DestroyRef) {
  }

  getAllByClientId(clientId: string): Observable<OrderModel[]> {
    return this.http.get<OrderModel[]>(`orders/${clientId}`)
      .pipe(takeUntilDestroyed(this.destroyRef),
        map((response: OrderModel[]) => response));
  }

  createOrder(clientId: string): Observable<OrderModel> {
    return this.http.post<OrderModel>(`orders/new/${clientId}`, {})
      .pipe(takeUntilDestroyed(this.destroyRef),
        map((response: OrderModel) => response));
  }

  addFoodDishToOrder(orderId: string, foodDishIds: string[]): Observable<OrderModel> {
    return this.http.post<OrderModel>(`orders/${orderId}`, foodDishIds)
      .pipe(takeUntilDestroyed(this.destroyRef),
        map((response: OrderModel) => response));
  }
}
