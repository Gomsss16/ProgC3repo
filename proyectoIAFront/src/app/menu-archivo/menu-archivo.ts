import { Component, AfterViewInit, ElementRef } from '@angular/core';
import { RouterLink } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-menu-archivo',
  standalone: true,
  imports: [RouterLink, FormsModule, CommonModule],
  templateUrl: './menu-archivo.html',
  styleUrl: './menu-archivo.css'
})
export class MenuArchivo implements AfterViewInit {
  selectedFile: File | null = null;
  message: string = "";
  messageType: 'error' | 'success' | 'info' | null = null;

  modelos = [
    { nombre: 'ChatGPT', nombreAPI: 'ChatGPT', logo: 'assets/chatgpt-logo.png', porcentaje: 0, texto: '' },
    { nombre: 'Claude', nombreAPI: 'Claude', logo: 'assets/Claude.png', porcentaje: 0, texto: '' },
    { nombre: 'Gemini', nombreAPI: 'Gemini', logo: 'assets/Gem.png', porcentaje: 0, texto: '' },
    { nombre: 'LLaMa', nombreAPI: 'LLaMa', logo: 'assets/meta.png', porcentaje: 0, texto: '' },
    { nombre: 'DeepSeek', nombreAPI: 'DeepSeek', logo: 'assets/deepseek-logo.png', porcentaje: 0, texto: '' },
    { nombre: 'Perplexity', nombreAPI: 'Perplexity', logo: 'assets/Perp.png', porcentaje: 0, texto: '' }
  ];

  promedioGeneral: number = 0;

  constructor(private el: ElementRef, private http: HttpClient) {}

  onFileSelected(event: any) {
    const file = event.target.files[0];
    if (file) {
      const allowedTypes = ['.txt', '.pdf', '.docx'];
      const fileExtension = file.name.substring(file.name.lastIndexOf('.')).toLowerCase();

      if (!allowedTypes.includes(fileExtension)) {
        this.message = "❌ Solo se permiten archivos .txt, .pdf o .docx";
        this.messageType = 'error';
        return;
      }

      this.selectedFile = file;
      this.message = `✅ Archivo seleccionado: ${file.name}`;
      this.messageType = 'success';
    }
  }

  onVerify(event: Event) {
    event.preventDefault();

    if (!this.selectedFile) {
      this.message = "❌ Debes seleccionar un archivo primero";
      this.messageType = 'error';
      return;
    }

    this.message = "📤 Subiendo archivo y analizando...";
    this.messageType = 'info';

    const formData = new FormData();
    formData.append('archivo', this.selectedFile);

    this.http.post<any>('http://localhost:8081/api/analizador-ia/analizar-archivo', formData)
      .subscribe({
        next: (response) => {
          console.log('Respuesta del backend:', response);

          this.modelos.forEach(modelo => {
            modelo.porcentaje = response.porcentajes[modelo.nombreAPI] || 0;

            if (modelo.porcentaje >= 80) {
              modelo.texto = '¡Alto! Este texto probablemente fue generado por IA.';
            } else if (modelo.porcentaje >= 50) {
              modelo.texto = 'Posiblemente tiene partes generadas por IA.';
            } else if (modelo.porcentaje >= 20) {
              modelo.texto = 'Probablemente fue escrito por un humano con algunas ayudas.';
            } else {
              modelo.texto = 'Este texto parece ser escrito por un humano.';
            }
          });

          this.promedioGeneral = response.promedioGeneral || 0;

          this.message = `✅ Análisis completado. Promedio: ${this.promedioGeneral.toFixed(1)}%`;
          this.messageType = 'success';

          setTimeout(() => this.clearMessage(), 5000);
        },
        error: (error) => {
          console.error('Error:', error);
          this.message = "❌ Error al analizar archivo. Verifica que el backend esté corriendo.";
          this.messageType = 'error';
          setTimeout(() => this.clearMessage(), 5000);
        }
      });
  }

  clearMessage() {
    this.message = "";
    this.messageType = null;
  }

  ngAfterViewInit() {
    const books = this.el.nativeElement.querySelectorAll('.book');

    const observer = new IntersectionObserver(
      (entries) => {
        entries.forEach((entry) => {
          if (entry.isIntersecting) {
            entry.target.classList.add('visible', 'animate__animated', 'animate__fadeInUp');
            observer.unobserve(entry.target);
          }
        });
      },
      { threshold: 0.2 }
    );

    books.forEach((book: Element) => observer.observe(book));
  }
}
