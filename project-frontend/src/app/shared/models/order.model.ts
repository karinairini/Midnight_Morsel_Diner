import {ClientModel} from "./client.model";
import {FoodDishForOrder} from "./food-dish-for-order.model";

export interface OrderModel {
  id: string;
  foodDishes: FoodDishForOrder[];
  totalPaycheck: number;
  orderDate: string;
  client: ClientModel;
}
