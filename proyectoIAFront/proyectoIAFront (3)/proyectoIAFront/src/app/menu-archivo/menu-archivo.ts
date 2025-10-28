import { Component, AfterViewInit, ElementRef} from '@angular/core';

@Component({
  selector: 'app-menu-archivo',
  standalone: false,
  templateUrl: './menu-archivo.html',
  styleUrl: './menu-archivo.css'
})
export class MenuArchivo {
  constructor(private el: ElementRef) {}


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
