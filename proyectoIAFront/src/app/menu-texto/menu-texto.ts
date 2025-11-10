import { Component, AfterViewInit, ElementRef } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-menu-texto',
  standalone: false,
  templateUrl: './menu-texto.html',
  styleUrl: './menu-texto.css'
})
export class MenuTexto implements AfterViewInit {
  txt: string = "";
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

  onVerify() {
    if (this.txt == "") {
      this.message = "El espacio del texto no puede estar vacío";
      this.messageType = 'error';

      setTimeout(() => {
        const alert = document.querySelector('.alert');
        if (alert) alert.classList.add('fade-out');
        setTimeout(() => this.clearMessage(), 300);
      }, 3000);

      return;
    }

    this.message = "Analizando con 6 modelos de IA...";
    this.messageType = 'info';

    this.http.post<any>('http://localhost:8081/api/analizador-ia/analizar-texto', {
      texto: this.txt
    }).subscribe({
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

        setTimeout(() => {
          const alert = document.querySelector('.alert');
          if (alert) alert.classList.add('fade-out');
          setTimeout(() => this.clearMessage(), 300);
        }, 5000);
      },
      error: (error) => {
        console.error('Error:', error);
        this.message = "❌ Error al analizar. Verifica que el backend esté corriendo en http://localhost:8081";
        this.messageType = 'error';

        setTimeout(() => {
          const alert = document.querySelector('.alert');
          if (alert) alert.classList.add('fade-out');
          setTimeout(() => this.clearMessage(), 300);
        }, 5000);
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
