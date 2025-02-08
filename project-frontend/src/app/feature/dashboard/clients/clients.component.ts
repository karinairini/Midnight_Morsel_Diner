import {Component, DestroyRef, OnInit} from '@angular/core';
import {takeUntilDestroyed} from "@angular/core/rxjs-interop";
import {ClientModel} from "../../../shared/models/client.model";
import {ClientService} from "../../../core/service/client/client.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {MailService} from "../../../core/service/mail/mail.service";
import {getUser} from "../../../core/service/user/user.service";
import {UserModel} from "../../../shared/models/user.model";

@Component({
  selector: 'app-clients',
  templateUrl: './clients.component.html',
  styleUrl: './clients.component.scss'
})
export class ClientsComponent implements OnInit {

  clients: ClientModel[] = [];
  showForm: boolean = false;
  saveForm: FormGroup = new FormGroup({});
  errorMessage?: string;
  loggedUser: UserModel = getUser();

  constructor(
    private clientService: ClientService,
    private mailService: MailService,
    private formBuilder: FormBuilder,
    private destroyRef: DestroyRef) {
  }

  ngOnInit(): void {
    this.getClients();
    this.buildSaveForm();
  }

  private getClients(): void {
    this.clientService.getAll().pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe({
        next: response => this.clients = response,
        error: err => console.log(err)
      });
  }

  deleteClient(clientId: string): void {
    this.clientService.deleteClient(clientId)
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe({
        next: () => {
          this.clients = this.clients.filter(
            client => client.id !== clientId
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
      password: ['', [Validators.required]]
    });
  }

  saveClient(): void {
    let name = this.saveForm?.get('name')?.value;
    let email = this.saveForm?.get('email')?.value;
    let password = this.saveForm?.get('password')?.value;

    if (!this.saveForm?.valid) {
      this.errorMessage = 'Invalid form completion!';
      setTimeout(() => this.errorMessage = undefined, 3000);
      return;
    }

    this.clientService.saveClient(name, email, password)
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe({
        next: (newClient: ClientModel) => {
          this.clients.push(newClient);

          this.mailService.sendMail(this.loggedUser.email, email)
            .pipe(takeUntilDestroyed(this.destroyRef))
            .subscribe({
              error: () => this.errorMessage = 'Failed to send mail.'
            });

          this.toggleShowForm();
        }
      });
  }

  clearForm(): void {
    this.saveForm.reset();
  }
}
