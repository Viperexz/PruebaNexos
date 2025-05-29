import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { MercanciaService } from '../../shared/services/mercancia.service';
import { MercanciaResponseModel } from '../../shared/models/mercancia-response.model';
import {MercanciaFormComponent} from './mercancia-form.component';
import {UsuarioResponseModel} from '../../shared/models/usuario-response.model';
import {MercanciaRequestModel} from '../../shared/models/mercancia-request.model';
import {RouterLink} from '@angular/router';
import {UsuarioActivoService} from '../../shared/services/usuario-activo.service';
import {NotificationComponent} from '../../shared/components/notification/notification.component';

@Component({
  selector: 'app-mercancia-list',
  standalone: true,
  imports: [CommonModule, FormsModule, MercanciaFormComponent, RouterLink, NotificationComponent],
  templateUrl: './mercancia-list.component.html',
  styleUrls: ['./mercancia-list.component.css']
})
export class MercanciaListComponent implements OnInit {
  /*Logica*/
  mercancias: MercanciaResponseModel[] = [];
  protected usuarioActivo!: UsuarioResponseModel;

  /*Auxiliares*/
  mercanciaActual!: MercanciaResponseModel;
  mercanciaAux!: MercanciaRequestModel;

  /*Modal*/
  modalFormVisible = false;
  modoForm: 'crear' | 'editar' = 'crear';

  /*Notificaicones*/
  showNotification: boolean = false;
  notificationMessage: string = '';
  notificationType: 'success' | 'info' | 'error' | 'security' = 'info';

  constructor(private mercanciaService: MercanciaService,private usuarioActivoService: UsuarioActivoService) {}

  ngOnInit(): void {
    this.cargarMercancias();
    this.usuarioActivo = this.usuarioActivoService.getUsuario();
  }

  cargarMercancias(): void {
    this.mercanciaService.getAll().subscribe(data => {
      this.mercancias = data;
    });
  }

  createMercancia(): void {
    this.modoForm = 'crear';
    this.mercanciaActual = {
      idMercancia: 0,
      nombreMercancia: '',
      cantidadMercancia: 1,
      fechaIngresoMercancia: new Date().toISOString().split('T')[0],
      nombreUsuarioRegistro: ''
    };
    this.modalFormVisible = true;
  }

  editMercancia(id: number|undefined): void {
    const mercancia = this.mercancias.find(m => m.idMercancia === id);
    if (mercancia) {
      this.modoForm = 'editar';
      this.mercanciaActual = { ...mercancia };
      this.modalFormVisible = true;
    }
  }

  detailsMercancia(id: number|undefined): void {
   /* const mercancia = this.mercancias.find(m => m.idMercancia === id);
    if (mercancia) {
      this.modo = 'detalle';
      this.mercanciaActual = { ...mercancia };
      this.modalVisible = true;
    }*/
    //TODO
    console.log(`Detalles de la mercancía con ID: ${id}`);
  }

  deleteMercancia(id: number | undefined): void {
    if (id === undefined) {
      console.error('El ID de la mercancía es inválido.');
      this.showNotificationMessage('error', 'No se puede eliminar una mercancía sin un ID válido.');
      return;
    }

    if (confirm('¿Está seguro de que desea eliminar esta mercancía?')) {
      this.mercanciaService.delete(id, this.usuarioActivo.idUsuario).subscribe({
        next: () => {
          this.cargarMercancias();
          this.showNotificationMessage('success', 'Mercancía eliminada exitosamente.');
        },
        error: (error) => {
          console.error('Error al eliminar mercancía:', error);
          this.showNotificationMessage('error', error.error || 'Ocurrió un error al eliminar la mercancía.');
        }
      });
    }
  }

 guardarMercancia(m: MercanciaResponseModel): void {
   console.log('Usuario', this.usuarioActivo);
   this.mercanciaAux = {
     nombreMercancia: m.nombreMercancia,
     cantidadMercancia: m.cantidadMercancia.toString(),
     fechaIngresoMercancia: m.fechaIngresoMercancia,
     idUsuario: this.usuarioActivo.idUsuario
   };

   if (this.modoForm === 'crear') {
     this.mercanciaService.create(this.mercanciaAux).subscribe({
       next: () => {
         this.cargarMercancias();
          this.showNotificationMessage('success', 'Mercancía creada exitosamente.');
         this.modalFormVisible = false;
       },
       error: (error) => {
         console.error('Error al crear mercancía:', error);
         this.showNotificationMessage('error', error.error || 'Ocurrió un error al crear la mercancía.');
       }
     });
   } else if (this.modoForm === 'editar') {
     this.mercanciaService.update(m, m.idMercancia, this.usuarioActivo.idUsuario).subscribe({
       next: () => {
         this.cargarMercancias();
          this.showNotificationMessage('success', 'Mercancía actualizada exitosamente.');
         this.modalFormVisible = false;
       },
       error: (error) => {
         console.error('Error al actualizar mercancía:', error);
         this.showNotificationMessage('error', error.error || 'Ocurrió un error al actualizar la mercancía.');
       }
     });
   }
 }

  private showNotificationMessage(type: 'success' | 'info' | 'error' | 'security', message: string): void {
    this.notificationType = type;
    this.notificationMessage = message;
    this.showNotification = true;
  }

  cerrarModal(): void {
    this.modalFormVisible = false;
  }
  closeNotification(): void {
    this.showNotification = false;
  }


}
