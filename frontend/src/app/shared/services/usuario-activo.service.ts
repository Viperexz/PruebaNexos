import { Injectable } from '@angular/core';
import {UsuarioResponseModel} from '../models/usuario-response.model';

@Injectable({ providedIn: 'root' })
export class UsuarioActivoService {
  private usuarioActivo!: UsuarioResponseModel;

  setUsuario(usuario: UsuarioResponseModel): void {
    this.usuarioActivo = usuario;
    localStorage.setItem('usuarioActivo', JSON.stringify(usuario));
  }

  getUsuario(): UsuarioResponseModel {
    if (!this.usuarioActivo) {
      const local = localStorage.getItem('usuarioActivo');
      if (local) this.usuarioActivo = JSON.parse(local);
    }
    return this.usuarioActivo;
  }

  clearUsuario(): void {
    this.usuarioActivo = undefined as any;
    localStorage.removeItem('usuarioActivo');
  }
}
