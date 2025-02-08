import {DestroyRef, Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {map, Observable} from "rxjs";
import {takeUntilDestroyed} from "@angular/core/rxjs-interop";
import {ClientModel} from "../../../shared/models/client.model";

@Injectable({
  providedIn: 'root'
})
export class ClientService {

  constructor(
    private http: HttpClient,
    private destroyRef: DestroyRef) {
  }

  getAll(): Observable<ClientModel[]> {
    return this.http.get<{ items: ClientModel[], total: number }>("clients")
      .pipe(takeUntilDestroyed(this.destroyRef),
        map(response => response.items));
  }

  deleteClient(clientId: string): Observable<void> {
    return this.http.delete<void>(`clients/${clientId}`)
      .pipe(takeUntilDestroyed(this.destroyRef));
  }

  saveClient(name: string, email: string, password: string): Observable<ClientModel> {
    return this.http.post<ClientModel>(`clients`, {name, email, password})
      .pipe(takeUntilDestroyed(this.destroyRef),
        map((response: ClientModel) => response));
  }
}
