import {NgModule} from '@angular/core';
import {CommonModule, NgOptimizedImage} from '@angular/common';
import {SharedModule} from "../../shared/shared.module";
import {DashboardRoutingModule} from "./dashboard-routing.module";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {FoodCardComponent} from './foods/food-card/food-card.component';
import {FoodsComponent} from "./foods/foods.component";
import {NavigationBarComponent} from './navigation-bar/navigation-bar.component';
import {ReservationsComponent} from './reservations/reservations.component';
import {ReservationCardComponent} from './reservations/reservation-card/reservation-card.component';
import {IgxCalendarComponent} from "igniteui-angular";
import { ReservationFlipCardComponent } from './reservations/reservation-flip-card/reservation-flip-card.component';
import { CartComponent } from './cart/cart.component';
import { EmployeesComponent } from './employees/employees.component';
import { EmployeeCardComponent } from './employees/employee-card/employee-card.component';
import { PromotionComponent } from './promotion/promotion.component';
import { OrdersComponent } from './orders/orders.component';
import { OrderCardComponent } from './orders/order-card/order-card.component';
import { ClientsComponent } from './clients/clients.component';
import { ClientCardComponent } from './clients/client-card/client-card.component';
import { NewFoodDishComponent } from './new-food-dish/new-food-dish.component';


@NgModule({
  declarations: [
    FoodCardComponent,
    FoodsComponent,
    NavigationBarComponent,
    ReservationsComponent,
    ReservationCardComponent,
    ReservationFlipCardComponent,
    CartComponent,
    EmployeesComponent,
    EmployeeCardComponent,
    PromotionComponent,
    OrdersComponent,
    OrderCardComponent,
    ClientsComponent,
    ClientCardComponent,
    NewFoodDishComponent,
  ],
    imports: [
        CommonModule,
        SharedModule,
        DashboardRoutingModule,
        FormsModule,
        IgxCalendarComponent,
        ReactiveFormsModule,
        NgOptimizedImage
    ]
})
export class DashboardModule {
}
