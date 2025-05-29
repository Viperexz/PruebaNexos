import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { MercanciaService } from '../../shared/services/mercancia.service';
import { MercanciaResponseModel } from '../../shared/models/mercancia-response.model';
import {MercanciaFormComponent} from './mercancia-form.component';
import {UsuarioResponseModel} from '../../shared/models/usuario-response.model';
import {MercanciaRequestModel} from '../../shared/models/mercancia-request.model';
import {UsuarioActivoService} from '../../shared/services/usuario-activo.service';
import {NotificationComponent} from '../../shared/components/notification/notification.component';
import {UsuarioService} from '../../shared/services/usuario.service';

@Component({
  selector: 'app-mercancia-list',
  standalone: true,
  imports: [CommonModule, FormsModule, MercanciaFormComponent, NotificationComponent],
  templateUrl: './mercancia-list.component.html',
  styleUrls: ['./mercancia-list.component.css'],
})
export class MercanciaListComponent implements OnInit {
  /*Logica*/
  mercancias: MercanciaResponseModel[] = [];
  protected usuarioActivo!: UsuarioResponseModel;
  usuarios : UsuarioResponseModel[] = [];


  /*Auxiliares*/
  mercanciaActual!: MercanciaRequestModel;
  mercanciaAux!: MercanciaRequestModel;
  idMercanciaEditar: number = 0;

  filtroNombre: string = '';
  filtroFechaRegistroDesde: string = '';
  filtroFechaRegistroHasta: string = '';
  filtroUsuarioIdRegistro?: number;

  filtroFechaModificacionDesde: string = '';
  filtroFechaModificacionHasta: string = '';
  filtroUsuarioIdModificacion?: number;


  /*Modal*/
  modalFormVisible = false;
  modoForm: 'crear' | 'editar' = 'crear';

  /*Notificaicones*/
  showNotification: boolean = false;
  notificationMessage: string = '';
  notificationType: 'success' | 'info' | 'error' | 'security' = 'info';
  notificationTitle: string = '';

  constructor(private mercanciaService: MercanciaService,private usuarioActivoService: UsuarioActivoService,private usuarioService: UsuarioService) {}



  ngOnInit(): void {
    this.cargarMercancias();
    this.cargarUsuarios();
    this.usuarioActivo = this.usuarioActivoService.getUsuario();
  }

  cargarMercancias(): void {
    this.mercanciaService.getAll().subscribe(data => {
      this.mercancias = data;
    });
  }

  cargarUsuarios(): void {
    this.usuarioService.getAll().subscribe(data => {
      this.usuarios = data;
    }, error => {
      console.error('Error al cargar usuarios:', error);
      this.showNotificationMessage('error','Error', 'No se pudieron cargar los usuarios.');
    });
  }

  createMercancia(): void {
    this.modoForm = 'crear';
    this.mercanciaActual = {
      nombreMercancia: '',
      cantidadMercancia: 0,
      fechaIngresoMercancia: new Date().toISOString().split('T')[0],
      idUsuario: 0
    };
    this.modalFormVisible = true;
  }

  editMercancia(id: number | undefined): void {
    const mercancia = this.mercancias.find(m => m.idMercancia === id);
    if (mercancia) {
      this.modoForm = 'editar';
      this.mercanciaActual = {
        nombreMercancia: mercancia.nombreMercancia,
        cantidadMercancia: mercancia.cantidadMercancia,
        fechaIngresoMercancia: mercancia.fechaIngresoMercancia,
        idUsuario: this.usuarios.find(c => c.nombreUsuario === mercancia.nombreUsuarioRegistro)?.idUsuario || 0
      };
      this.modalFormVisible = true;
    }
    this.idMercanciaEditar = id || 0;
  }

 private areFiltersEmpty(): boolean {
   return !this.filtroNombre && !this.filtroFechaRegistroDesde && !this.filtroFechaRegistroHasta && !this.filtroUsuarioIdRegistro && !this.filtroFechaModificacionDesde && !this.filtroFechaModificacionHasta && !this.filtroUsuarioIdModificacion;
 }

  filtrarMercancias(): void {
    if (this.areFiltersEmpty()) {
      this.showNotificationMessage('info','Informacion', 'Debe seleccionar al menos un filtro para realizar la búsqueda.');
      return;
    }

    this.mercanciaService.getByFiltros(
      this.filtroNombre,
      this.filtroFechaRegistroDesde,
      this.filtroFechaRegistroHasta,
      this.filtroUsuarioIdRegistro,
      this.filtroFechaModificacionDesde,
      this.filtroFechaModificacionHasta,
      this.filtroUsuarioIdModificacion
    ).subscribe({
      next: (data) => {
        this.mercancias = data;
      },
      error: (error) => {
        console.error('Error al filtrar mercancías:', error);
        this.showNotificationMessage('error','Error', error.error || 'Ocurrió un error al crear la mercancía.');
      }
    });
  }

  limpiarFiltros(): void {
    this.filtroNombre = '';
    this.filtroFechaRegistroDesde = '';
    this.filtroFechaRegistroHasta = '';
    this.filtroUsuarioIdRegistro = undefined;
    this.filtroFechaModificacionDesde = '';
    this.filtroFechaModificacionHasta = '';
    this.filtroUsuarioIdModificacion = undefined;
    this.cargarMercancias(); // Recarga todas las mercancías
    this.showNotificationMessage('info','Informacion' ,'Se han limpiado los filtros.');
  }

  deleteMercancia(id: number | undefined): void {
    if (id === undefined) {
      console.error('El ID de la mercancía es inválido.');
      this.showNotificationMessage('error','Error', 'No se puede eliminar una mercancía sin un ID válido.');
      return;
    }

    if (confirm('¿Está seguro de que desea eliminar esta mercancía?')) {
      this.mercanciaService.delete(id, this.usuarioActivo.idUsuario).subscribe({
        next: () => {
          this.cargarMercancias();
          this.showNotificationMessage('success','Realizado', 'Mercancía eliminada exitosamente.');
        },
        error: (error) => {
          console.error('Error al eliminar mercancía:', error);
          this.showNotificationMessage('error','Error', error.error || 'Ocurrió un error al eliminar la mercancía.');
        }
      });
    }
  }

 guardarMercancia(m: MercanciaRequestModel): void {
   console.log('Creando mercancía:', m);
   if (this.modoForm === 'crear') {
     m.idUsuario = this.usuarioActivo.idUsuario;
     console.log('Creando mercancía:', m);
     this.mercanciaService.create(m).subscribe({
       next: () => {
         this.cargarMercancias();
          this.showNotificationMessage('success', 'Realizado', 'Mercancía creada exitosamente.');
         this.modalFormVisible = false;
       },
       error: (error) => {
         console.error('Error al crear mercancía:', error);
         this.showNotificationMessage('error','Error' ,error.error || 'Ocurrió un error al crear la mercancía.');
       }
     });
   } else if (this.modoForm === 'editar') {
     this.mercanciaService.update(m, this.idMercanciaEditar, this.usuarioActivo.idUsuario).subscribe({
       next: () => {
         this.cargarMercancias();
          this.showNotificationMessage('success', 'Realizado', 'Mercancía actualizada exitosamente.');
         this.notificationTitle = 'Éxito';
         this.modalFormVisible = false;
       },
       error: (error) => {
         console.error('Error al actualizar mercancía:', error);
         this.showNotificationMessage('error','Error', error.error || 'Ocurrió un error al actualizar la mercancía.');
       }
     });
   }
 }

  private showNotificationMessage(type: 'success' | 'info' | 'error' | 'security', title:string ,message: string): void {
    this.notificationType = type;
    this.notificationMessage = message;
    this.notificationTitle = title;
    this.showNotification = true;
  }

  cerrarModal(): void {
    this.modalFormVisible = false;
  }
  closeNotification(): void {
    this.showNotification = false;
  }


}

