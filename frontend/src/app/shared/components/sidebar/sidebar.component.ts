import {Component, OnInit} from '@angular/core';
import {RouterLink} from "@angular/router";
import {NgOptimizedImage} from "@angular/common";
import {UsuarioActivoService} from "../../services/usuario-activo.service";
import {UsuarioResponseModel} from "../../models/usuario-response.model";

@Component({
  selector: 'app-sidebar',
  imports: [
    RouterLink,
    NgOptimizedImage,
  ],
  templateUrl: './sidebar.component.html',
  styleUrl: './sidebar.component.css'
})


export class SidebarComponent implements OnInit{
  protected usuarioActivo!: UsuarioResponseModel;
  constructor(private usuarioActivoService: UsuarioActivoService) {}

  ngOnInit(): void {
    this.usuarioActivo = this.usuarioActivoService.getUsuario();
  }

}
