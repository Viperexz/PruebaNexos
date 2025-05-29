import { Component, OnInit } from '@angular/core';
import {DatePipe, NgForOf} from '@angular/common';
import {RouterLink} from '@angular/router';
import {UsuarioService} from '../../shared/services/usuario.service';
import {UsuarioResponseModel} from '../../shared/models/usuario-response.model';
import {UsuarioRequestModel} from '../../shared/models/usuario-request.model';
import {NotificationComponent} from '../../shared/components/notification/notification.component';
import {MercanciaFormComponent} from '../mercancia/mercancia-form.component';
import {UsuarioFormComponent} from './usuario-form.component';

@Component({
  selector: 'app-usuario-list',
  standalone: true,
  imports: [
    DatePipe,
    RouterLink,
    NgForOf,
    NotificationComponent,
    MercanciaFormComponent,
    UsuarioFormComponent
  ],
  templateUrl: './usuario-list.component.html'
})
export class UsuarioListComponent implements OnInit {
  /*Logica*/
  usuario: UsuarioResponseModel[] = [];
  protected usuarioActual!: UsuarioRequestModel ;

  /*Auxiliares*/


  /*Modal Form*/
  modalFormVisible = false;
  modoForm: 'crear' | 'editar' = 'crear';

  /*Notificaicones*/
  showNotification: boolean = false;
  notificationMessage: string = '';
  notificationType: 'success' | 'info' | 'error' | 'security' = 'info';


  /*Constructor*/
  constructor(private usuarioService: UsuarioService) {}

  ngOnInit(): void {
    this.usuarioService.getAll().subscribe(data => {
      console.log('Datos cargados:', data); // Verifica los datos aquí
      this.usuario = data;
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

  }

  detailsUsuario(idUsuario: any) {

  }


  /* Modificacion de datos*/

  guardarUsuario($event: any) {

  }

  deleteUsuario(idUsuario: any) {

  }


  cerrarModal(): void {
    this.modalFormVisible = false;
  }

  closeNotification(): void {
    this.showNotification = false;
  }



}
