import { Component } from '@angular/core';
import { Router } from '@angular/router';
import {UsuarioService} from '../services/user/usuario.service';
import {Usuario} from '../models/usuario.model';
import {Role} from '../models/role.enum';
import {authService} from '../services/auth/auth.service';

@Component({
  selector: 'app-usuarios',
  standalone: false,
  templateUrl: './usuarios.html',
  styleUrl: './usuarios.css'
})
export class Usuarios {

  usuario: Usuario = {
    nombre: ' ',
    correo: '',
    contrasenia: '',
    role: Role.USER,  // valor por defecto
    codigoVerificacion: ' ', // Código ingresado por el usuario
    validarCodigo: false

  };
  user: any;
  confirmPassword: string = "";
  showVerificationDialog: boolean = false; // Control del dialogo
  isLoggedIn: boolean = false;
  isConfirmed: boolean = false;
  message: string = "";
  messageType: 'success' | 'error' | null = null;

  constructor(private router: Router , private usuarioService: UsuarioService, private authService:authService) {}


  onLogin() {
    if (this.usuario.correo == "" || this.usuario.contrasenia == ""){
         this.message = "Todos los espacios son obligatorios";
        this.messageType = "error";
         }
    this.authService.login(this.usuario).subscribe({
      next: (response: any) => {
        const token = response;

        //if (!token) {  // Si es null o undefined
        //  this.message = "No se pudo iniciar sesión, vuelva a intentarlo.";
        //  this.messageType = "error";
        //  return; // Salimos sin guardar token ni continuar
        //}

        // Guardamos el token solo si llegó correctamente
        localStorage.setItem('token', token);

        // Llama al backend para obtener el usuario por correo
        this.usuarioService.obtenerUsuarioPorCorreo(this.usuario.correo)
          .subscribe({

            next: (usuario: any) => {

              // Verifica si ya validó su cuenta
              localStorage.setItem('idUsuario', usuario.id.toString());
              if (usuario.validarCodigo) {
                this.message = "Navegacion exitosa";
                this.messageType = "success";
                this.router.navigate(['/panel']);

              } else {
                this.message = "debe ingresar el codigo para validar su cuenta";
                this.messageType = 'error';
                this.showVerificationDialog = true;
                setTimeout(() => {
                  const alert = document.querySelector('.alert');
                  if (alert) alert.classList.add('fade-out');
                  setTimeout(() => this.clearMessage(), 300);
                }, 3000);


              }
            },

              error: () => {
                alert('Correo o contraseña incorrectos.');
            }
          });
      },
      error: (err) => {
        this.message = err.error?.message || 'Error al iniciar sesion';
        this.messageType = 'error';
      }
    });
  }


  onSignUp() {
    const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    const passwordRegex = /^(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*()_\-+=<>?{}[\]~]).{8,}$/;

    if (!this.usuario.nombre || !this.usuario.correo || !this.usuario.contrasenia) {
      this.message = 'Todos los campos son obligatorios';
      this.messageType = 'error';
      return;
    } else if (this.usuario.contrasenia != "" && this.confirmPassword != "" && this.usuario.contrasenia != this.confirmPassword) {
      this.message = "Las contraseñas no coinciden";
      this.messageType = "error";
    } else if (!emailRegex.test(this.usuario.correo)) {
      this.message = "El correo no tiene un formato valido";
      this.messageType = "error";
    } else if (!passwordRegex.test(this.usuario.contrasenia)) {
      this.message = "La contraseña debe tener al menos 8 caracteres, una mayúscula, un número y un carácter especial";
      this.messageType = "error";
    } else {
      // Llama al backend para registrar
      this.authService.registrar(this.usuario).subscribe({
        next: (response: any) => {
          this.message = response;
          this.messageType = 'success';
          this.showVerificationDialog = true; // Abre el diálogo para ingresar código

        },


        error: (err) => {
          this.message = err.error?.message || 'Error al registrar el usuario';
          this.messageType = 'error';
        }
      });

    }

    }

    verifyCode() {
      if (!this.usuario.codigoVerificacion || !this.usuario.correo) {
        this.message = 'Debes ingresar el código de verificación';
        this.messageType = 'error';
        return;
      }

      this.usuarioService.verificarCodigo(this.usuario.correo, this.usuario.codigoVerificacion)
        .subscribe({
          next: (response) => {
            if (this.authService.currentUserLoginOn.value) {
              // Caso: usuario ya logueado
              this.message = 'Código verificado.';
              this.messageType = 'success';

              setTimeout(() => {
                this.router.navigate(['/panel']);
              }, 1000);
            }else {
              this.message = 'Registro exitoso. Tu cuenta ha sido verificada correctamente, prueba iniciar sesión.';
              this.messageType = 'success';
              this.showVerificationDialog = false;

              // Limpia los campos
              this.usuario = {
                nombre: '',
                correo: '',
                contrasenia: '',
                role: Role.USER,
                codigoVerificacion: ''
              };

              setTimeout(() => {
                const alert = document.querySelector('.alert');
                if (alert) alert.classList.add('fade-out');
                setTimeout(() => this.clearMessage(), 300);
              }, 2000);

              setTimeout(() => {
                this.router.navigate(['/']);
              }, 2000);
            }
          },
          error: (err) => {
            this.message = err.error || 'Error al verificar el código';
            this.messageType = 'error';
          }
        });
    }

    clearMessage() {
      this.message = '';
      this.messageType = null;
    }

    closeDialog() {
      this.showVerificationDialog = false;
    }


  }
