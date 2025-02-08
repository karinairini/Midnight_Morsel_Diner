import {NgModule} from '@angular/core';
import {RouterModule, Routes} from "@angular/router";
import {NotFoundComponent} from "../../shared/components/not-found/not-found.component";
import {InvalidAccessComponent} from "../../shared/components/invalid-access/invalid-access.component";
import {FoodsComponent} from "./foods/foods.component";
import {ReservationsComponent} from "./reservations/reservations.component";
import {hasRole} from "../../core/guard/authorization.guard";
import {CartComponent} from "./cart/cart.component";
import {EmployeesComponent} from "./employees/employees.component";
import {PromotionComponent} from "./promotion/promotion.component";
import {OrdersComponent} from "./orders/orders.component";
import {ClientsComponent} from "./clients/clients.component";
import {NewFoodDishComponent} from "./new-food-dish/new-food-dish.component";


export const routes: Routes = [
  {
    path: 'foods',
    component: FoodsComponent,
    canActivate: [hasRole],
    data: {
      requiredRoles: ['CLIENT', 'EMPLOYEE']
    }
  },
  {
    path: 'reservations',
    component: ReservationsComponent,
    canActivate: [hasRole],
    data: {
      requiredRoles: ['CLIENT', 'ADMIN']
    }
  },
  {
    path: 'orders',
    component: OrdersComponent,
    canActivate: [hasRole],
    data: {
      requiredRoles: ['CLIENT']
    }
  },
  {
    path: 'cart',
    component: CartComponent,
    canActivate: [hasRole],
    data: {
      requiredRoles: ['CLIENT']
    }
  },
  {
    path: 'newFoodDish',
    component: NewFoodDishComponent,
    canActivate: [hasRole],
    data: {
      requiredRoles: ['EMPLOYEE']
    }
  },
  {
    path: 'promotion',
    component: PromotionComponent,
    canActivate: [hasRole],
    data: {
      requiredRoles: ['ADMIN']
    }
  },
  {
    path: 'employees',
    component: EmployeesComponent,
    canActivate: [hasRole],
    data: {
      requiredRoles: ['ADMIN']
    }
  },
  {
    path: 'clients',
    component: ClientsComponent,
    canActivate: [hasRole],
    data: {
      requiredRoles: ['EMPLOYEE']
    }
  },
  {
    path: 'invalid-access',
    component: InvalidAccessComponent
  },
  {
    path: '**',
    component: NotFoundComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class DashboardRoutingModule {
}
