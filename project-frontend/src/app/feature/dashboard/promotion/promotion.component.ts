import {Component, DestroyRef, OnInit} from '@angular/core';
import {EmployeeService} from "../../../core/service/employee/employee.service";
import {EmployeeTypeModel} from "../../../shared/models/employee-type.model";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {takeUntilDestroyed} from "@angular/core/rxjs-interop";
import {Router} from "@angular/router";

@Component({
  selector: 'app-promotion',
  templateUrl: './promotion.component.html',
  styleUrl: './promotion.component.scss'
})
export class PromotionComponent implements OnInit {

  promotionForm: FormGroup = new FormGroup({});
  errorMessage?: string;

  employeeId: string = "";
  employeeName: string = "";
  employeeEmail: string = "";
  employeeType: string = "";
  positionsAboveEmployeeType: string[] = [];

  constructor(
    private employeeService: EmployeeService,
    private formBuilder: FormBuilder,
    private destroyRef: DestroyRef,
    private router: Router) {
  }

  ngOnInit(): void {
    this.buildPromotionForm();
    this.employeeId = history.state.id;
    this.employeeName = history.state.name;
    this.employeeEmail = history.state.email;
    this.employeeType = history.state.position;
    this.calculatePositionsAbove();
  }

  private buildPromotionForm(): void {
    this.promotionForm = this.formBuilder.group({
      position: ['', [Validators.required]],
      salary: ['', [Validators.required]]
    });
  }

  calculatePositionsAbove(): void {
    const employeeTypeValue = EmployeeTypeModel[this.employeeType as keyof typeof EmployeeTypeModel];
    this.positionsAboveEmployeeType = Object.keys(EmployeeTypeModel)
      .filter(key => {
        const enumValue = EmployeeTypeModel[key as keyof typeof EmployeeTypeModel];
        return enumValue > employeeTypeValue;
      });
  }

  promote(): void {
    let position = this.promotionForm?.get('position')?.value;
    let salary = this.promotionForm?.get('salary')?.value;

    if (!this.promotionForm?.valid) {
      this.errorMessage = 'Invalid form completion!';
      setTimeout(() => this.errorMessage = undefined, 3000);
      return;
    }

    this.employeeService.promoteEmployee(this.employeeId, salary, position)
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe(() => {
        this.router.navigateByUrl("/dashboard/employees");
      });
  }
}
