
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { UsuarioResponseModel } from '../models/usuario-response.model';
import { Observable } from 'rxjs';
import {UsuarioRequestModel} from '../models/usuario-request.model';

@Injectable({ providedIn: 'root' })
export class UsuarioService {
  private readonly apiUrl = 'http://localhost:8080/api/usuarios';
  constructor(private http: HttpClient) {}

  getAll(): Observable<UsuarioResponseModel[]> {
    return this.http.get<UsuarioResponseModel[]>(this.apiUrl);
  }

  getById(id: number): Observable<UsuarioResponseModel> {
    return this.http.get<UsuarioResponseModel>(`${this.apiUrl}/${id}`);
  }

  create(usuario: UsuarioRequestModel): Observable<UsuarioRequestModel> {
    return this.http.post<UsuarioRequestModel>(this.apiUrl, usuario);
  }

  update(id: number, usuario: UsuarioRequestModel): Observable<UsuarioRequestModel> {
    return this.http.put<UsuarioRequestModel>(`${this.apiUrl}/${id}`, usuario);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
