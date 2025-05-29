import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UsuarioResponseModel } from '../../shared/models/usuario-response.model';
import { UsuarioService } from '../../shared/services/usuario.service';
import { UsuarioActivoService } from '../../shared/services/usuario-activo.service';
import { FormsModule } from '@angular/forms';
import {NgForOf, NgIf, NgOptimizedImage} from '@angular/common';
import {NotificationComponent} from '../../shared/components/notification/notification.component';

@Component({
  selector: 'app-userselect',
  templateUrl: './userselect.component.html',
  standalone: true,
  imports: [
    FormsModule,
    NgForOf,
    NgOptimizedImage,
    NotificationComponent,
    NgIf
  ],
  styleUrls: ['userselect.component.css']
})
export class UserSelectComponent implements OnInit {
  usuarios: UsuarioResponseModel[] = [];
  idUsuarioSeleccionado?: number;
  showNotification: boolean = false;

  constructor(
    private usuarioService: UsuarioService,
    private usuarioActivoService: UsuarioActivoService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.usuarioService.getAll().subscribe(data => {
      this.usuarios = data;
      console.log('Usuarios cargados:', this.usuarios);
    });
  }

  seleccionarUsuario(): void {
    const usuario = this.usuarios.find(u => u.idUsuario === this.idUsuarioSeleccionado);
    if (usuario) {
      console.log('Usuario seleccionado:', usuario);
      this.usuarioActivoService.setUsuario(usuario);
      this.router.navigate(['/app/mercancias']);
    }
    else {
      console.error('Usuario no encontrado');
      this.showNotification = true;
    }
  }

  closeNotification(): void {
    this.showNotification = false;
  }
}
