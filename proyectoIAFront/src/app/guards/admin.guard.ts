import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AdminGuard implements CanActivate {

  constructor(private router: Router) {}

  canActivate(): boolean {
    const token = localStorage.getItem('token');

    if (!token) {
      this.router.navigate(['/']);
      return false;
    }

    try {
      const payload = JSON.parse(atob(token.split('.')[1]));
      const authorities = payload.authorities || [];
      const isAdmin = authorities.some((auth: any) =>
        auth.authority === 'ROLE_ADMIN' || auth.authority === 'ADMIN'
      );

      if (isAdmin) {
        return true;
      } else {
        alert('⛔ Solo los administradores pueden acceder aquí');
        this.router.navigate(['/panel/menu-texto']);
        return false;
      }
    } catch (error) {
      this.router.navigate(['/']);
      return false;
    }
  }
}
