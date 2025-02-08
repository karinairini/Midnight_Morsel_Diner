import {Component, Input} from '@angular/core';
import {OrderModel} from "../../../../shared/models/order.model";

@Component({
  selector: 'app-order-card',
  templateUrl: './order-card.component.html',
  styleUrl: './order-card.component.scss'
})
export class OrderCardComponent {

  @Input() order!: OrderModel;
  showDetails: boolean = false;

  constructor() {
  }

  toggleOrderDetails(): void {
    this.showDetails = !this.showDetails;
  }
}
