import {DestroyRef, Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {map, Observable} from "rxjs";
import {takeUntilDestroyed} from "@angular/core/rxjs-interop";
import {EmployeeModel} from "../../../shared/models/employee.model";

@Injectable({
  providedIn: 'root'
})
export class EmployeeService {

  constructor(
    private http: HttpClient,
    private destroyRef: DestroyRef) {
  }

  getAll(): Observable<EmployeeModel[]> {
    return this.http.get<{ items: EmployeeModel[], total: number }>("employees")
      .pipe(takeUntilDestroyed(this.destroyRef),
        map(response => response.items));
  }

  promoteEmployee(employeeId: string, salary: number, employeeType: string): Observable<EmployeeModel> {
    return this.http.put<EmployeeModel>(`employees/${employeeId}`, {salary, employeeType})
      .pipe(takeUntilDestroyed(this.destroyRef),
        map((response: EmployeeModel) => response));
  }

  deleteEmployee(employeeId: string): Observable<void> {
    return this.http.delete<void>(`employees/${employeeId}`)
      .pipe(takeUntilDestroyed(this.destroyRef));
  }

  saveEmployee(name: string, email: string, password: string, salary: number, hireDate: string, employeeType: string): Observable<EmployeeModel> {
    return this.http.post<EmployeeModel>(`employees`, {name, email, password, salary, hireDate, employeeType})
      .pipe(takeUntilDestroyed(this.destroyRef), map((response: EmployeeModel) => response));
  }
}
