import {Component, DestroyRef, OnInit} from '@angular/core';
import {OrderService} from "../../../core/service/order/order.service";
import {takeUntilDestroyed} from "@angular/core/rxjs-interop";
import {getUser} from "../../../core/service/user/user.service";
import {OrderModel} from "../../../shared/models/order.model";
import {UserModel} from "../../../shared/models/user.model";

@Component({
  selector: 'app-orders',
  templateUrl: './orders.component.html',
  styleUrl: './orders.component.scss'
})
export class OrdersComponent implements OnInit {

  orders: OrderModel[] = [];
  loggedUser: UserModel = getUser();

  constructor(
    private orderService: OrderService,
    private destroyRef: DestroyRef) {
  }

  ngOnInit(): void {
    this.getOrdersByClientId();
  }

  private getOrdersByClientId(): void {
    this.orderService.getAllByClientId(this.loggedUser.id).pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe({
        next: response =>
          this.orders = response,
        error: err => console.log(err)
      });
  }
}
