import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import {MercanciaRequestModel} from '../../shared/models/mercancia-request.model';
import {NotificationComponent} from '../../shared/components/notification/notification.component';

@Component({
  selector: 'app-mercancia-form',
  standalone: true,
  imports: [CommonModule, FormsModule, NotificationComponent],
  templateUrl: './mercancia-form.component.html',
})
export class MercanciaFormComponent {
  @Input() mercancia: MercanciaRequestModel = {
    nombreMercancia: '',
    cantidadMercancia: 0,
    fechaIngresoMercancia: '',
    idUsuario: 0
  };
  @Input() modo: 'crear' | 'editar' = 'crear';
  @Output() onGuardar = new EventEmitter<MercanciaRequestModel>();
  @Output() onCancelar = new EventEmitter<void>();

  /*Notificaicones*/
  showNotification: boolean = false;
  notificationMessage: string = '';
  notificationType: 'success' | 'info' | 'error' | 'security' = 'info';

  guardar(): void {
    if (!this.mercancia.nombreMercancia || this.mercancia.cantidadMercancia <= 0) {
      this.showNotificationMessage('error', 'Todos los campos son obligatorios.');
      return;
    }
    if(this.mercancia.cantidadMercancia > 1000000 )
    {
      this.showNotificationMessage('error', 'La cantidad de mercancía no puede ser mayor a 1.000.000.');
      return;
    }
    if (!this.mercancia.fechaIngresoMercancia) {
      this.showNotificationMessage('error', 'La fecha de ingreso es obligatoria.');
      return;
    }

    this.onGuardar.emit(this.mercancia);
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
