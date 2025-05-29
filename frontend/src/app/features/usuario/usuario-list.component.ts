import { Component, OnInit } from '@angular/core';
import {NgForOf, NgIf} from '@angular/common';
import {UsuarioService} from '../../shared/services/usuario.service';
import {UsuarioResponseModel} from '../../shared/models/usuario-response.model';
import {UsuarioRequestModel} from '../../shared/models/usuario-request.model';
import {NotificationComponent} from '../../shared/components/notification/notification.component';
import {UsuarioFormComponent} from './usuario-form.component';
import {CargoService} from '../../shared/services/cargo.service';
import {CargoModel} from '../../shared/models/cargo.model';

@Component({
  selector: 'app-usuario-list',
  standalone: true,
  imports: [
    NgForOf,
    NotificationComponent,
    UsuarioFormComponent,
    NgIf
  ],
  templateUrl: './usuario-list.component.html'
})
export class UsuarioListComponent implements OnInit {
  /*Logica*/
  usuario: UsuarioResponseModel[] = [];
  protected usuarioActual!: UsuarioRequestModel;

  /*Auxiliares*/
  usuarioAux!: UsuarioRequestModel;
  cargos: CargoModel[] = [];
  idUsuarioEditar: number = 0;

  /*Modal Form*/
  modalFormVisible = false;
  modoForm: 'crear' | 'editar' = 'crear';


  /*Notificaicones*/
  showNotification: boolean = false;
  notificationMessage: string = '';
  notificationType: 'success' | 'info' | 'error' | 'security' = 'info';
  notificationTitle: string = '';


  /*Constructor*/
  constructor(private usuarioService: UsuarioService,private cargoService:CargoService) {}

  ngOnInit(): void {
    this.cargarUsuarios.call(this);
    this.cargarCargos.call(this);

  }

  cargarUsuarios(): void {
    this.usuarioService.getAll().subscribe(data => {
      this.usuario = data;
    });
  }

  cargarCargos(): void {
    this.cargoService.getAll().subscribe(data => {
      this.cargos = data;
    });
  }



  /*Metodos modal form*/
  createUsuario() {
    this.modoForm = 'crear';
    this.usuarioActual = {
      nombreUsuario: '',
      edadUsuario: 0,
      idCargoUsuario: 0,
      fechaIngresoUsuario:new Date().toISOString().split('T')[0],
    };
    this.modalFormVisible = true;
  }

  editUsuario(idUsuario: any) {
    this.idUsuarioEditar = idUsuario;
    const usuario = this.usuario.find(u => u.idUsuario === idUsuario);
   this.usuarioAux = {
      nombreUsuario: usuario?.nombreUsuario || '',
      edadUsuario: usuario?.edadUsuario || 0,
      idCargoUsuario: this.cargos.find(c => c.nombreCargo === usuario?.cargoUsuario)?.idCargo || 0,
      fechaIngresoUsuario: usuario?.fechaIngresoUsuario || new Date().toISOString().split('T')[0]
   }
    if (usuario) {
      this.modoForm = 'editar';
      this.usuarioActual = { ...(this.usuarioAux) };
      this.modalFormVisible = true;
    }

  }


  /* Modificacion de datos*/

  guardarUsuario(usuario: UsuarioRequestModel): void {

    if (this.modoForm === 'crear') {
      console.log('Creando usuario:', usuario);
      this.usuarioService.create(usuario).subscribe({
        next: () => {
          this.cargarUsuarios();
          this.showNotificationMessage('success','Realizado'  ,'Usuario creada exitosamente.');
          this.modalFormVisible = false;
        },
        error: (error) => {
          console.error('Error al crear mercancía:', error);
          this.showNotificationMessage('error','Error', error.error || 'Ocurrió un error al crear la mercancía.');
        }
      });
    } else if (this.modoForm === 'editar') {
      this.usuarioService.update(this.idUsuarioEditar,usuario).subscribe({
        next: () => {
          this.cargarUsuarios();
          this.showNotificationMessage('success', 'Realizado' ,'Mercancía actualizada exitosamente.');
          this.modalFormVisible = false;
        },
        error: (error) => {
          console.error('Error al actualizar mercancía:', error);
          this.showNotificationMessage('error','Error', error.error || 'Ocurrió un error al actualizar la mercancía.');
        }
      });
    }

  }

  deleteUsuario(idUsuario: any) {
    if (confirm('¿Está seguro de que desea eliminar este usuario?')) {
      this.usuarioService.delete(idUsuario).subscribe({
        next: () => {
          this.cargarUsuarios();
          this.showNotificationMessage('success','Realizado' ,'Usuario eliminado exitosamente.');
        },
        error: (error) => {
          console.error('Error al eliminar usuario:', error);
          this.showNotificationMessage('error','Error', error.error || 'Ocurrió un error al eliminar el usuario.');
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
