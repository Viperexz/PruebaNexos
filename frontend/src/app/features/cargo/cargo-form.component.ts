import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import {MercanciaResponseModel} from '../../shared/models/mercancia-response.model';
import {CargoModel} from '../../shared/models/cargo.model';
import {NotificationComponent} from '../../shared/components/notification/notification.component';

@Component({
  selector: 'app-cargo-form',
  standalone: true,
  imports: [CommonModule, FormsModule, NotificationComponent],
  templateUrl: './cargo-form.component.html',
})
export class CargoFormComponent {
  @Input() cargo: CargoModel = {
    idCargo: 0,
    nombreCargo: '',
  };
  @Input() modo: 'crear' | 'editar' = 'crear';
  @Output() onGuardar = new EventEmitter<CargoModel>();
  @Output() onCancelar = new EventEmitter<void>();

  /*Notificaicones*/
  showNotification: boolean = false;
  notificationMessage: string = '';
  notificationType: 'success' | 'info' | 'error' | 'security' = 'info';


  guardar(): void {
    if (!this.cargo.nombreCargo ) {
      this.showNotificationMessage('error', 'Todos los campos son obligatorios.');

      return;
    }

    this.onGuardar.emit(this.cargo);
  }

  cancelar(): void {
    this.onCancelar.emit();
  }

  private showNotificationMessage(type: 'success' | 'info' | 'error' | 'security', message: string): void {
    this.notificationType = type;
    this.notificationMessage = message;
    this.showNotification = true;
  }

  closeNotification(): void {
    this.showNotification = false;
  }



}
