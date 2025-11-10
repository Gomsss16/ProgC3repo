import {Component, OnInit} from '@angular/core';
import {UsuarioService} from '../services/user/usuario.service';
import {Router} from '@angular/router';
import {authService} from '../services/auth/auth.service';

@Component({
  selector: 'app-perfil',
  standalone: true,
  templateUrl: './perfil.html',
  styleUrls: ['./perfil.css']
})
export class Perfil implements OnInit {
  usuario: any = {};

  constructor(private usuarioService: UsuarioService, private authService: authService, private router: Router) {}

  ngOnInit() {
    const id = localStorage.getItem('idUsuario');
    if (id) {
      this.usuarioService.obtenerUsuarioPorId(+id).subscribe({
        next: (data) => {
          this.usuario = data;
          console.log('Usuario cargado:', this.usuario);
        },
        error: (err) => {
          console.error('Error al obtener el usuario:', err);
          alert('No se pudo cargar la información del usuario.');
        }
      });
    } else {
      console.warn('No hay ID de usuario guardado.');
    }
  }

  logout() {
    this.authService.longout()
    this.router.navigate(['/']);
  }

}
