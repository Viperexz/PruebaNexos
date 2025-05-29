import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import {MercanciaResponseModel} from '../../shared/models/mercancia-response.model';

@Component({
  selector: 'app-mercancia-form',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './cargo-form.component.html',
  styleUrls: ['./cargo-form.component.css']
})
export class CargoFormComponent {
  @Input() mercancia: MercanciaResponseModel = {
    idMercancia: 0,
    nombreMercancia: '',
    cantidadMercancia: 0,
    fechaIngresoMercancia: '',
    nombreUsuarioRegistro: ''
  };
  @Input() modo: 'crear' | 'editar' = 'crear';
  @Output() onGuardar = new EventEmitter<MercanciaResponseModel>();
  @Output() onCancelar = new EventEmitter<void>();

  guardar(): void {
    if (!this.mercancia.nombreMercancia || this.mercancia.cantidadMercancia <= 0) {
      alert('Todos los campos son obligatorios.');
      return;
    }

    this.onGuardar.emit(this.mercancia);
  }

  cancelar(): void {
    this.onCancelar.emit();
  }
}
