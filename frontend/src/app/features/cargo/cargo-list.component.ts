import { Component, OnInit } from '@angular/core';
import {DatePipe, NgForOf, NgIf} from '@angular/common';
import {RouterLink} from '@angular/router';
import {CargoModel} from '../../shared/models/cargo.model';
import {CargoService} from '../../shared/services/cargo.service';
import {NotificationComponent} from '../../shared/components/notification/notification.component';
import {CargoFormComponent} from './cargo-form.component';

@Component({
  selector: 'app-usuario-list',
  standalone: true,
  imports: [
    NgForOf,
    CargoFormComponent,
    NgIf,
    NotificationComponent
  ],
  templateUrl: './cargo-list.component.html'
})
export class CargoListComponent implements OnInit {
  /*Logica*/
  cargo: CargoModel[] = [];
  protected cargoActual!: CargoModel;

  /*Modal Form*/
  modalFormVisible = false;
  modoForm: 'crear' | 'editar' = 'crear';


  /*Notificaicones*/
  showNotification: boolean = false;
  notificationMessage: string = '';
  notificationTitle: string = '';
  notificationType: 'success' | 'info' | 'error' | 'security' = 'info';


  constructor(private cargoService:CargoService) {}

  ngOnInit(): void {
    this.cargarCargos.call(this);
  }

  cargarCargos(): void {
    this.cargoService.getAll().subscribe(data => {
      this.cargo = data;
    });
  }

  createCargo() {
    this.modoForm = 'crear';
    this.cargoActual = {
      idCargo: 0,
      nombreCargo: '',
    };
    this.modalFormVisible = true;
  }

  deleteCargo(idCargo: any) {
    if (confirm('¿Está seguro de que desea eliminar este cargo?')) {
      this.cargoService.delete(idCargo).subscribe({
        next: () => {
          this.cargarCargos();
          this.showNotificationMessage('success','Realizado' ,'Cargo eliminado exitosamente.');
        },
        error: (error) => {
          console.error('Error al eliminar cargo:', error);
          this.showNotificationMessage('error','Error', error.error || 'Ocurrió un error al eliminar el cargo.');
        }
      });
    }
  }

  private showNotificationMessage(type: 'success' | 'info' | 'error' | 'security', title:string ,message: string): void {
    this.notificationType = type;
    this.notificationMessage = message;
    this.showNotification = true;
    this.notificationTitle= title;
  }

  cerrarModal(): void {
    this.modalFormVisible = false;
  }

  closeNotification(): void {
    this.showNotification = false;
  }


  guardarCargo(cargo: CargoModel) {
      this.cargoService.create(cargo).subscribe({
        next: () => {
          this.cargarCargos();
          this.showNotificationMessage('success','Realizado', 'Cargo creado exitosamente.');
          this.modalFormVisible = false;
        },
        error: (error) => {
          console.error('Error al crear mercancía:', error);
          this.showNotificationMessage('error','Error', error.error || 'Ocurrió un error al crear la mercancía.');
        }
      });
  }
}
