import {Component, computed, input} from '@angular/core';
import {RouterLink, RouterLinkActive, RouterOutlet} from '@angular/router';
import {CommonModule, NgClass} from '@angular/common';
import {Menu} from '../menu/menu';

@Component({
  selector: 'app-main',
  standalone: true,
  imports: [RouterOutlet, CommonModule, Menu],
  templateUrl: './main.html',
  styleUrl: './main.css'
})
export class Main {
  isLeftSidebarCollapsed = true;

  // Método para alternar el menú (puedes llamarlo desde el menú)
  toggleSidebar() {
    this.isLeftSidebarCollapsed = !this.isLeftSidebarCollapsed;
  }
}
