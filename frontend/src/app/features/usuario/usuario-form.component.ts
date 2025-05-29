import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import {MercanciaResponseModel} from '../../shared/models/mercancia-response.model';
import {UsuarioRequestModel} from '../../shared/models/usuario-request.model';
import {CargoService} from '../../shared/services/cargo.service';
import {CargoModel} from '../../shared/models/cargo.model';
import {NotificationComponent} from '../../shared/components/notification/notification.component';

@Component({
  selector: 'app-usuario-form',
  standalone: true,
  imports: [CommonModule, FormsModule, NotificationComponent],
  templateUrl: './usuario-form.component.html',
})
export class UsuarioFormComponent implements OnInit {
  cargos: CargoModel[] = [];
  idCargoSeleccionado?: number;
  @Input() usuario: UsuarioRequestModel = {
    nombreUsuario: '',
    edadUsuario: 0,
    idCargoUsuario: 0,
    fechaIngresoUsuario:'',
  };

  @Input() modo: 'crear' | 'editar' = 'crear';
  @Output() onGuardar = new EventEmitter<UsuarioRequestModel>();
  @Output() onCancelar = new EventEmitter<void>();

  /*Notificaicones*/
  showNotification: boolean = false;
  notificationMessage: string = '';
  notificationType: 'success' | 'info' | 'error' | 'security' = 'info';



  constructor(private cargoService: CargoService) {}
  ngOnInit(): void {

    this.cargoService.getAll().subscribe(data => {
      this.cargos = data;
      console.log('Cargos cargados:', this.cargos);
    });
  }

  guardar(): void {
    console.log('idCargoSeleccionado:', this.idCargoSeleccionado);

    if (!this.usuario.nombreUsuario || this.usuario.edadUsuario <= 0 || !this.usuario.fechaIngresoUsuario) {
      this.showNotificationMessage('error', 'Todos los campos son obligatorios.');

      return;
    }

    if (!this.idCargoSeleccionado || this.idCargoSeleccionado === 0) {
      this.showNotificationMessage('error', 'Debe seleccionar un cargo válido.');

      return;
    }
    this.usuario.idCargoUsuario = this.idCargoSeleccionado;
    this.onGuardar.emit(this.usuario);
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
