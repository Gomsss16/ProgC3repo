import { Component, AfterViewInit, ElementRef } from '@angular/core';

@Component({
  selector: 'app-menu-texto',
  standalone: false,
  templateUrl: './menu-texto.html',
  styleUrl: './menu-texto.css'
})
export class MenuTexto implements AfterViewInit {
  txt: string = "";
  message: string = "";
  messageType: 'error' | 'success' | null = null;

  constructor(private el: ElementRef) {}

  onVerify() {
    if (this.txt == "") {
      this.message = "El espacio del texto no puede estar vacio";
      this.messageType = 'error';
    } else {
      this.message = "bien";
      this.messageType = 'success';
      console.log(this.txt);
    }

    setTimeout(() => {
      const alert = document.querySelector('.alert');
      if (alert) alert.classList.add('fade-out');
      setTimeout(() => this.clearMessage(), 300);
    }, 3000);
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

