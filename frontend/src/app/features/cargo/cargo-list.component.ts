import { Component, OnInit } from '@angular/core';
import {DatePipe, NgForOf} from '@angular/common';
import {RouterLink} from '@angular/router';
import {CargoModel} from '../../shared/models/cargo.model';
import {CargoService} from '../../shared/services/cargo.service';

@Component({
  selector: 'app-usuario-list',
  standalone: true,
  imports: [
    DatePipe,
    RouterLink,
    NgForOf
  ],
  templateUrl: './cargo-list.component.html'
})
export class CargoListComponent implements OnInit {
  cargo: CargoModel[] = [];

  constructor(private cargoService:CargoService) {}

  ngOnInit(): void {
    this.cargoService.getAll().subscribe(data => {
      console.log('Datos cargados:', data);
      this.cargo = data;
    });
  }
  createCargo() {

  }

  editCargo(idCargo: any) {

  }

  detailsCargo(idCargo: any) {

  }

  deleteCargo(idCargo: any) {

  }


}
