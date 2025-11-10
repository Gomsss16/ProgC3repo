import { Component, AfterViewInit, ElementRef } from '@angular/core';
import { RouterLink } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-menu-imagen',
  standalone: true,
  imports: [RouterLink, FormsModule, CommonModule],
  templateUrl: './menu-imagen.html',
  styleUrl: './menu-imagen.css'
})
export class MenuImagen implements AfterViewInit {
  selectedFile: File | null = null;
  imagenBase64: string = "";
  message: string = "";
  messageType: 'error' | 'success' | 'info' | null = null;

  modelos = [
    { nombre: 'ChatGPT', nombreAPI: 'ChatGPT', logo: 'assets/chatgpt-logo.png', porcentaje: 0, texto: '' },
    { nombre: 'Claude', nombreAPI: 'Claude', logo: 'assets/Claude.png', porcentaje: 0, texto: '' },
    { nombre: 'Gemini', nombreAPI: 'Gemini', logo: 'assets/Gem.png', porcentaje: 0, texto: '' },
    { nombre: 'LLaMa', nombreAPI: 'HF-BLIP', logo: 'assets/meta.png', porcentaje: 0, texto: '' },
    { nombre: 'DeepSeek', nombreAPI: 'HF-CLIP', logo: 'assets/deepseek-logo.png', porcentaje: 0, texto: '' },
    { nombre: 'Perplexity', nombreAPI: 'HF-ViT', logo: 'assets/Perp.png', porcentaje: 0, texto: '' }
  ];

  promedioGeneral: number = 0;

  constructor(private el: ElementRef, private http: HttpClient) {}

  onFileSelected(event: any) {
    const file = event.target.files[0];
    if (file) {
      const allowedTypes = ['image/jpeg', 'image/jpg', 'image/png', 'image/gif'];

      if (!allowedTypes.includes(file.type)) {
        this.message = "❌ Solo se permiten imágenes (.jpg, .png, .gif)";
        this.messageType = 'error';
        return;
      }

      this.selectedFile = file;

      const reader = new FileReader();
      reader.onload = () => {
        this.imagenBase64 = reader.result as string;
        this.message = `✅ Imagen seleccionada: ${file.name}`;
        this.messageType = 'success';
      };
      reader.readAsDataURL(file);
    }
  }

  onVerify(event: Event) {
    event.preventDefault();

    if (!this.imagenBase64) {
      this.message = "❌ Debes seleccionar una imagen primero";
      this.messageType = 'error';
      return;
    }

    this.message = "📤 Analizando imagen...";
    this.messageType = 'info';

    this.http.post<any>('http://localhost:8081/api/analizador-ia/analizar-imagen', {
      imagenBase64: this.imagenBase64
    }).subscribe({
      next: (response) => {
        console.log('Respuesta del backend:', response);

        this.modelos.forEach(modelo => {
          modelo.porcentaje = response.porcentajes[modelo.nombreAPI] || 0;

          if (modelo.porcentaje >= 80) {
            modelo.texto = '¡Alto! Esta imagen probablemente fue generada por IA.';
          } else if (modelo.porcentaje >= 50) {
            modelo.texto = 'Posiblemente tiene partes generadas por IA.';
          } else if (modelo.porcentaje >= 20) {
            modelo.texto = 'Probablemente es una foto real con edición de IA.';
          } else {
            modelo.texto = 'Esta imagen parece ser real (no generada por IA).';
          }
        });

        this.promedioGeneral = response.promedioGeneral || 0;

        this.message = `✅ Análisis completado. Promedio: ${this.promedioGeneral.toFixed(1)}%`;
        this.messageType = 'success';

        setTimeout(() => this.clearMessage(), 5000);
      },
      error: (error) => {
        console.error('Error:', error);
        this.message = "❌ Error al analizar imagen. Verifica que el backend esté corriendo.";
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
