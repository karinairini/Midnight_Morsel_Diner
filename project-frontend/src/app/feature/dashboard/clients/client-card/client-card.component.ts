import {Component, EventEmitter, Input, Output} from '@angular/core';
import {ClientModel} from "../../../../shared/models/client.model";

@Component({
  selector: 'app-client-card',
  templateUrl: './client-card.component.html',
  styleUrl: './client-card.component.scss'
})
export class ClientCardComponent {

  @Input() client!: ClientModel;
  @Output() deleteClientEvent: EventEmitter<string> = new EventEmitter<string>();

  constructor() {
  }

  deleteClient(): void {
    this.deleteClientEvent.emit(this.client.id);
  }
}
