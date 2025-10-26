import {Component, ElementRef} from '@angular/core';

@Component({
  selector: 'app-menu-video',
  standalone: false,
  templateUrl: './menu-video.html',
  styleUrl: './menu-video.css'
})
export class MenuVideo {

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
