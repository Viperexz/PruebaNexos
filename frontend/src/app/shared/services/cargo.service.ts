
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {CargoModel} from '../models/cargo.model';


@Injectable({ providedIn: 'root' })
export class CargoService {
  private readonly apiUrl = 'http://localhost:8080/api/cargos';

  constructor(private http: HttpClient) {}

  getAll(): Observable<CargoModel[]> {
    return this.http.get<CargoModel[]>(this.apiUrl);
  }

  getById(id: number): Observable<CargoModel> {
    return this.http.get<CargoModel>(`${this.apiUrl}/${id}`);
  }

  create(cargo: CargoModel): Observable<CargoModel> {
    return this.http.post<CargoModel>(this.apiUrl, cargo);
  }

  update(id: number, cargo: CargoModel): Observable<CargoModel> {
    return this.http.put<CargoModel>(`${this.apiUrl}/${id}`, cargo);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
