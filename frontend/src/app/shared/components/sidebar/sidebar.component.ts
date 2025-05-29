import {Component, OnInit} from '@angular/core';
import {RouterLink} from "@angular/router";
import {NgOptimizedImage} from "@angular/common";
import {MercanciaService} from "../../services/mercancia.service";
import {UsuarioActivoService} from "../../services/usuario-activo.service";
import {UsuarioResponseModel} from "../../models/usuario-response.model";
import {NgIcon} from "@ng-icons/core";

@Component({
  selector: 'app-sidebar',
  imports: [
    RouterLink,
    NgOptimizedImage,
    NgIcon
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
