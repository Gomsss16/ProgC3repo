import { Component, input, output } from '@angular/core';
import { RouterLink, RouterLinkActive } from '@angular/router';
import { NgClass } from '@angular/common';
import { Perfil } from '../perfil/perfil';

@Component({
  selector: 'app-menu',
  standalone: true,
  templateUrl: './menu.html',
  imports: [
    RouterLink,
    NgClass,
    RouterLinkActive,
    Perfil
  ],
  styleUrl: './menu.css'
})
export class Menu {

  isLeftSidebarCollapsed = input.required<boolean>();
  changeIsLeftSidebarCollapsed = output<boolean>();

  items = [
    {
      routeLink: 'menu-texto',
      icon: 'fas fa-pencil-alt',
      label: 'detectar textos'
    },
    {
      routeLink: 'menu-archivo',
      icon: 'fas fa-file-alt',
      label: 'detectar archivos'
    },
    {
      routeLink: 'menu-imagen',
      icon: 'fas fa-images',
      label: 'detectar imagenes'
    },
    {
      routeLink: 'menu-video',
      icon: 'fas fa-film',
      label: 'detectar videos'
    },
    {
      routeLink: 'admin/historial',
      icon: 'fas fa-user-shield',
      label: 'auditoría',
      adminOnly: true  // ✅ Solo visible para admin
    },
    {
      routeLink: 'perfil',
      icon: 'fas fa-user'
    }
  ];

  // ✅ Método para verificar si el usuario es admin
  isAdmin(): boolean {
    const token = localStorage.getItem('token');
    if (!token) return false;

    try {
      const payload = JSON.parse(atob(token.split('.')[1]));
      const authorities = payload.authorities || [];
      return authorities.some((auth: any) =>
        auth.authority === 'ROLE_ADMIN' || auth.authority === 'ADMIN'
      );
    } catch {
      return false;
    }
  }

  toggleCollapse(): void {
    this.changeIsLeftSidebarCollapsed.emit(!this.isLeftSidebarCollapsed());
  }

  closeSidenav(): void {
    this.changeIsLeftSidebarCollapsed.emit(true);
  }
}
