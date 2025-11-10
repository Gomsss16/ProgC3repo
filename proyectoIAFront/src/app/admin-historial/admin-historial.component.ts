import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

interface AccionHistorial {
  tipo: string;
  detalles: string;
  ip: string;
  fecha: string;
}

interface UsuarioConHistorial {
  correo: string;
  nombre: string;
  historial: AccionHistorial[];
}

@Component({
  selector: 'app-admin-historial',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './admin-historial.component.html',
  styleUrl: './admin-historial.component.css'
})
export class AdminHistorialComponent implements OnInit {
  correo: string = '';
  usuarios: UsuarioConHistorial[] = [];
  usuariosFiltrados: UsuarioConHistorial[] = [];
  loading: boolean = false;
  error: string = '';

  constructor(private http: HttpClient) {}

  ngOnInit() {
    this.cargarTodos();
  }

  cargarTodos() {
    this.loading = true;
    this.error = '';

    const token = localStorage.getItem('token');
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });

    this.http.get<UsuarioConHistorial[]>('http://localhost:8081/usuarios/todos-con-historial', { headers })
      .subscribe({
        next: (data) => {
          this.usuarios = data;
          this.usuariosFiltrados = data;
          this.loading = false;
        },
        error: (err) => {
          console.error('Error:', err);
          this.error = 'No se pudo cargar la auditoría';
          this.loading = false;
        }
      });
  }

  buscarPorCorreo() {
    // Si el campo está vacío, recargar todo
    if (!this.correo.trim()) {
      this.cargarTodos();
      return;
    }

    // Buscar en la lista cargada
    this.usuariosFiltrados = this.usuarios.filter(u =>
      u.correo.toLowerCase().includes(this.correo.toLowerCase())
    );
  }

  formatearFecha(fecha: string): string {
    const date = new Date(fecha);
    return date.toLocaleString('es-CO', {
      year: 'numeric',
      month: 'long',
      day: 'numeric',
      hour: '2-digit',
      minute: '2-digit'
    });
  }

  getIcono(tipo: string): string {
    switch(tipo) {
      case 'ANALIZAR_TEXTO': return '📝';
      case 'ANALIZAR_IMAGEN': return '🖼️';
      case 'ANALIZAR_VIDEO': return '🎥';
      case 'ANALIZAR_ARCHIVO': return '📁';
      case 'LOGIN': return '🔐';
      case 'REGISTRO': return '✅';
      default: return '📌';
    }
  }
}
