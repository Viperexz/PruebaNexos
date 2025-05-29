import {MercanciaResponseModel} from './mercancia-response.model';

export interface UsuarioResponseModel {
  idUsuario?: number;
  nombreUsuario: string;
  edadUsuario: string;
  cargoUsuario: string;
  fechaIngresoUsuario?: string;
  mercanciasUsuario?: MercanciaResponseModel[];

}
