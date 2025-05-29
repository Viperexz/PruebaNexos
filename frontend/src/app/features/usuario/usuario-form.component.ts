import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import {MercanciaResponseModel} from '../../shared/models/mercancia-response.model';
import {UsuarioRequestModel} from '../../shared/models/usuario-request.model';
import {CargoService} from '../../shared/services/cargo.service';
import {CargoModel} from '../../shared/models/cargo.model';

@Component({
  selector: 'app-usuario-form',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './usuario-form.component.html',
  styleUrls: ['./usuario-form.component.css']
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

  constructor(private cargoService: CargoService) {}
  ngOnInit(): void {
    this.cargoService.getAll().subscribe(data => {
      this.cargos = data;
      console.log('Cargos cargados:', this.cargos);
    });
  }

  guardar(): void {
    if (!this.usuario.nombreUsuario || this.usuario.edadUsuario <= 0 || !this.usuario.fechaIngresoUsuario) {
      alert('Todos los campos son obligatorios.');
      return;
    }

    this.onGuardar.emit(this.usuario);
  }

  cancelar(): void {
    this.onCancelar.emit();
  }


}
