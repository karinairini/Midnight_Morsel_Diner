import {DestroyRef, Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {map, Observable} from "rxjs";
import {takeUntilDestroyed} from "@angular/core/rxjs-interop";
import {ReservationModel} from "../../../shared/models/reservation.model";

@Injectable({
  providedIn: 'root'
})
export class ReservationService {

  constructor(
    private http: HttpClient,
    private destroyRef: DestroyRef) {
  }

  getAll(): Observable<ReservationModel[]> {
    return this.http.get<{ items: ReservationModel[], total: number }>("reservations")
      .pipe(takeUntilDestroyed(this.destroyRef),
        map(response => response.items));
  }

  getAllByClientId(clientId: string): Observable<ReservationModel[]> {
    return this.http.get<ReservationModel[]>(`reservations/${clientId}`)
      .pipe(takeUntilDestroyed(this.destroyRef),
        map((response: ReservationModel[]) => response));
  }

  createReservation(clientId: string, reservationDate: string): Observable<ReservationModel> {
    return this.http.post<ReservationModel>(`reservations/${clientId}`, {clientId, reservationDate})
      .pipe(takeUntilDestroyed(this.destroyRef),
        map((response: ReservationModel) => response));
  }

  deleteReservation(reservationId: string): Observable<void> {
    return this.http.delete<void>(`reservations/${reservationId}`)
      .pipe(takeUntilDestroyed(this.destroyRef));
  }

  updateReservationStatus(reservationId: string, reservationStatus: string): Observable<ReservationModel> {
    return this.http.put<ReservationModel>(`reservations/${reservationId}?status=${reservationStatus}`, {})
      .pipe(takeUntilDestroyed(this.destroyRef),
        map((response: ReservationModel) => response));
  }
}
