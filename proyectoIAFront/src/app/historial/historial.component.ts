import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { CommonModule } from '@angular/common';

interface AccionHistorial {
  tipo: string;
  detalles: string;
  ip: string;
  fecha: string;
}

@Component({
  selector: 'app-historial',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './historial.component.html',
  styleUrl: './historial.component.css'
})
export class HistorialComponent implements OnInit {
  historial: AccionHistorial[] = [];
  loading: boolean = false;
  error: string = '';

  constructor(private http: HttpClient) {}

  ngOnInit() {
    this.cargarHistorial();
  }

  cargarHistorial() {
    this.loading = true;
    this.error = '';

    const token = localStorage.getItem('token');
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });

    this.http.get<AccionHistorial[]>('http://localhost:8081/usuarios/mi-historial', { headers })
      .subscribe({
        next: (data) => {
          this.historial = data;
          this.loading = false;
        },
        error: (err) => {
          console.error('Error al cargar historial:', err);
          this.error = 'No se pudo cargar el historial';
          this.loading = false;
        }
      });
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
