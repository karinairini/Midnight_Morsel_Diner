import {Component, EventEmitter, Input, Output} from '@angular/core';
import {ReservationModel} from "../../../../shared/models/reservation.model";
import {getUser} from "../../../../core/service/user/user.service";
import {UserModel} from "../../../../shared/models/user.model";

@Component({
  selector: 'app-reservation-card',
  templateUrl: './reservation-card.component.html',
  styleUrl: './reservation-card.component.scss'
})
export class ReservationCardComponent {

  @Input() reservation!: ReservationModel;
  @Output() deleteReservationEvent: EventEmitter<string> = new EventEmitter<string>();
  loggedUser: UserModel = getUser();

  constructor() {
  }

  deleteReservation(reservationId: string): void {
    this.deleteReservationEvent.emit(reservationId);
  }
}
