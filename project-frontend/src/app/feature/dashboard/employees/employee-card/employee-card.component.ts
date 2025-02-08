import {Component, EventEmitter, Input, Output} from '@angular/core';
import {EmployeeModel} from "../../../../shared/models/employee.model";
import {Router} from "@angular/router";

@Component({
  selector: 'app-employee-card',
  templateUrl: './employee-card.component.html',
  styleUrl: './employee-card.component.scss'
})
export class EmployeeCardComponent {

  @Input() employee!: EmployeeModel;
  @Output() deleteEmployeeEvent: EventEmitter<string> = new EventEmitter<string>();

  constructor(private router: Router) {
  }

  promoteEmployee(): void {
    this.router.navigate(['/dashboard/promotion'], {
      state: {
        id: this.employee.id,
        name: this.employee.name,
        email: this.employee.email,
        position: this.employee.employeeType
      }
    });
  }

  fireEmployee(): void {
    this.deleteEmployeeEvent.emit(this.employee.id);
  }
}
