import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {UserModel} from "../../../shared/models/user.model";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) {
  }

  getInfo(): Observable<UserModel> {
    return this.http.get<UserModel>('user/info');
  }
}

export const getUser = (): UserModel => {
  return JSON.parse(localStorage.getItem('loggedUser') || '{}');
};
