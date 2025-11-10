import {Role} from './role.enum';


export interface Usuario {
  id?: number;
  nombre?: string;
  correo: string;
  contrasenia: string;
  role: Role;
  codigoVerificacion?: string;
  validarCodigo?: boolean;
}
