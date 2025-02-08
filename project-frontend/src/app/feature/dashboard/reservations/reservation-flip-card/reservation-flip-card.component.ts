import {Component, EventEmitter, Input, Output} from '@angular/core';
import {ReservationModel} from "../../../../shared/models/reservation.model";
import {getUser} from "../../../../core/service/user/user.service";
import {UserModel} from "../../../../shared/models/user.model";

@Component({
  selector: 'app-reservation-flip-card',
  templateUrl: './reservation-flip-card.component.html',
  styleUrl: './reservation-flip-card.component.scss'
})
export class ReservationFlipCardComponent {

  @Input() reservation!: ReservationModel;
  @Output() updateReservationEvent: EventEmitter<{
    reservationId: string,
    reservationStatus: string
  }> = new EventEmitter<{ reservationId: string, reservationStatus: string }>();
  loggedUser: UserModel = getUser();

  constructor() {
  }

  updateReservation(reservationId: string, reservationStatus: string): void {
    this.updateReservationEvent.emit({reservationId, reservationStatus});
  }
}
