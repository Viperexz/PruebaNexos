
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {catchError, Observable,throwError} from 'rxjs';
import {MercanciaResponseModel} from '../models/mercancia-response.model';
import {MercanciaRequestModel} from '../models/mercancia-request.model';

@Injectable({ providedIn: 'root' })
export class MercanciaService {
  private readonly apiUrl = 'http://localhost:8080/api/mercancias';

  constructor(private http: HttpClient) {}

  getAll(): Observable<MercanciaResponseModel[]> {
    return this.http.get<MercanciaResponseModel[]>(this.apiUrl);
  }

  getById(id: number): Observable<MercanciaResponseModel> {
    return this.http.get<MercanciaResponseModel>(`${this.apiUrl}/${id}`);
  }

  create(mercancia: MercanciaRequestModel): Observable<MercanciaRequestModel> {
    return this.http.post<MercanciaRequestModel>(this.apiUrl, mercancia).pipe(
      catchError(error => {
        if (error.status === 400) {
          console.error('Bad Request:', error.error.message || error.message);
        }
        return throwError(() => error);
      })
    );
  }

  update(mercancia: MercanciaResponseModel,idMercancia: number|undefined,idUsuario: number|undefined): Observable<MercanciaResponseModel> {
    console.log(`Updating mercancia with ID: ${idMercancia} for user ID: ${idUsuario}` + mercancia.idMercancia + mercancia.nombreMercancia + mercancia.cantidadMercancia + mercancia.fechaIngresoMercancia + mercancia.nombreUsuarioRegistro);
    return this.http.put<MercanciaResponseModel>(`${this.apiUrl}/${idMercancia}/usuario/${idUsuario}`, mercancia);
  }

  delete(idMercancia: number| undefined , idUsuario: number| undefined): Observable<void> {
    console.log(`Deleting mercancia with ID: ${idMercancia} for user ID: ${idUsuario}`);
    return this.http.delete<void>(`${this.apiUrl}/${idMercancia}/usuario/${idUsuario}`);
  }
}
