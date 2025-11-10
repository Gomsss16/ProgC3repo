import {HttpClient, HttpParams} from '@angular/common/http';
import {Injectable} from '@angular/core';

@Injectable({ providedIn: 'root' })
export class UsuarioService {
  private apiUrl = 'http://localhost:8081/usuarios'

  constructor(private http: HttpClient) {}

  verificarCodigo(correo: string, codigo: string) {
    const params = new HttpParams()
      .set('correo', correo)
      .set('codigo', codigo);
    return this.http.post(`${this.apiUrl}/verificar`, null, { params, responseType: 'text' });
  }

  getAll() {
    return this.http.get(`${this.apiUrl}/listarusuarios`);
  }

  obtenerUsuarioPorId(id: number) {
    return this.http.get<any>(`${this.apiUrl}/obtener/${id}`);
  }

  obtenerUsuarioPorCorreo(correo: string) {
    return this.http.get<any>(`${this.apiUrl}/obtenercorreo`,  { params: { correo } });
  }


}
