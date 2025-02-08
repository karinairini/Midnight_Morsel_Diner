import {DestroyRef, Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {MailModel} from "../../../shared/models/mail.model";
import {takeUntilDestroyed} from "@angular/core/rxjs-interop";

@Injectable({
  providedIn: 'root'
})
export class MailService {

  constructor(
    private http: HttpClient,
    private destroyRef: DestroyRef) {
  }

  sendMail(from: string, to: string): Observable<MailModel> {
    return this.http.post<MailModel>(`mail/async`, {from, to})
      .pipe(takeUntilDestroyed(this.destroyRef));
  }
}
