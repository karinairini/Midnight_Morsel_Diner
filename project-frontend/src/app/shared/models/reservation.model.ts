import {ClientModel} from "./client.model";

export interface ReservationModel {
  id: string;
  client: ClientModel;
  reservationDate: string;
  status: string;
}
