import {Component, DestroyRef, OnInit} from '@angular/core';
import {takeUntilDestroyed} from "@angular/core/rxjs-interop";
import {ReservationModel} from "../../../shared/models/reservation.model";
import {ReservationService} from "../../../core/service/reservation/reservation.service";
import {getUser} from "../../../core/service/user/user.service";
import {UserModel} from "../../../shared/models/user.model";

@Component({
  selector: 'app-reservations',
  templateUrl: './reservations.component.html',
  styleUrl: './reservations.component.scss'
})
export class ReservationsComponent implements OnInit {

  reservations: ReservationModel[] = [];
  showCalendar: boolean = false;
  loggedUser: UserModel = getUser();

  constructor(
    private reservationService: ReservationService,
    private destroyRef: DestroyRef) {
  }

  ngOnInit(): void {
    if (this.loggedUser.role === "CLIENT") {
      this.getReservationByLoggedClient();
    } else {
      this.getReservations();
    }
  }

  private getReservationByLoggedClient(): void {
    this.reservationService.getAllByClientId(getUser().id)
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe({
        next: response => this.reservations = response,
        error: err => console.log(err)
      });
  }

  private getReservations(): void {
    this.reservationService.getAll()
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe({
        next: response => this.reservations = response,
        error: err => console.log(err)
      });
  }

  toggleShowCalendar(): void {
    this.showCalendar = !this.showCalendar;
  }

  onSelection(date: Date | Date[]): void {
    if (Array.isArray(date)) {
      return;
    }
    const currentDate = new Date();
    if (date.getTime() >= currentDate.getTime()) {
      const formattedDate = `${date.getFullYear()}-${(date.getMonth() + 1).toString().padStart(2, '0')}-${date.getDate().toString().padStart(2, '0')}`;
      this.reservationService.createReservation(getUser().id, formattedDate)
        .pipe(takeUntilDestroyed(this.destroyRef))
        .subscribe((newReservation: ReservationModel) => {
          this.reservations.push(newReservation);
        });
      this.toggleShowCalendar();
    }
  }

  deleteReservation(reservationId: string): void {
    this.reservationService.deleteReservation(reservationId)
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe({
        next: () => {
          this.reservations = this.reservations.filter(
            reservation => reservation.id !== reservationId
          );
        }
      });
  }

  updateReservation(event: { reservationId: string, reservationStatus: string }): void {
    const {reservationId, reservationStatus} = event;
    this.reservationService.updateReservationStatus(reservationId, reservationStatus)
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe({
        next: () => {
          this.reservations = this.reservations.filter(
            reservation => reservation.id !== reservationId
          );
        }
      });
  }
}
