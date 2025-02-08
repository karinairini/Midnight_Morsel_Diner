import {Component, DestroyRef, OnInit} from '@angular/core';
import {EmployeeModel} from "../../../shared/models/employee.model";
import {takeUntilDestroyed} from "@angular/core/rxjs-interop";
import {EmployeeService} from "../../../core/service/employee/employee.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {EmployeeTypeModel} from "../../../shared/models/employee-type.model";

@Component({
  selector: 'app-employees',
  templateUrl: './employees.component.html',
  styleUrl: './employees.component.scss'
})
export class EmployeesComponent implements OnInit {

  employees: EmployeeModel[] = [];
  showForm: boolean = false;
  saveForm: FormGroup = new FormGroup({});
  errorMessage?: string;
  positionsForEmployeeType: string[] = [];

  constructor(
    private employeeService: EmployeeService,
    private formBuilder: FormBuilder,
    private destroyRef: DestroyRef) {
  }

  ngOnInit(): void {
    this.getEmployees();
    this.positionsForEmployeeType = Object.values(EmployeeTypeModel)
      .filter(value => typeof value === 'string')
      .map(value => value as string);
    this.buildSaveForm();
  }

  private getEmployees(): void {
    this.employeeService.getAll().pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe({
        next: response => this.employees = response,
        error: err => console.log(err)
      });
  }

  deleteEmployee(employeeId: string): void {
    this.employeeService.deleteEmployee(employeeId)
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe({
        next: () => {
          this.employees = this.employees.filter(
            employee => employee.id !== employeeId
          );
        }
      });
  }

  toggleShowForm(): void {
    this.showForm = !this.showForm;
  }

  private buildSaveForm(): void {
    this.saveForm = this.formBuilder.group({
      name: ['', [Validators.required]],
      email: ['', [Validators.required]],
      password: ['', [Validators.required]],
      salary: ['', [Validators.required]],
      hireDate: ['', [Validators.required]],
      position: ['', [Validators.required]]
    });
  }

  saveEmployee(): void {
    let name = this.saveForm?.get('name')?.value;
    let email = this.saveForm?.get('email')?.value;
    let password = this.saveForm?.get('password')?.value;
    let salary = this.saveForm?.get('salary')?.value;
    let hireDate = this.saveForm?.get('hireDate')?.value;
    let position = this.saveForm?.get('position')?.value;

    if (!this.saveForm?.valid) {
      this.errorMessage = 'Invalid form completion!';
      setTimeout(() => this.errorMessage = undefined, 3000);
      return;
    }

    this.employeeService.saveEmployee(name, email, password, salary, hireDate, position)
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe((newEmployee: EmployeeModel) => {
        this.employees.push(newEmployee);
      });
    this.toggleShowForm();
  }

  clearForm(): void {
    this.saveForm.reset();
  }
}
