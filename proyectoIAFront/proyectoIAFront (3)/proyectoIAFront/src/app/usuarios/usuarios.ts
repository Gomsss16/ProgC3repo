import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-usuarios',
  standalone: false,
  templateUrl: './usuarios.html',
  styleUrl: './usuarios.css'
})
export class Usuarios {
  email: string = "";
  password: string = "";
  user: string = "";
  confirmPassword: string = "";
  verificationCode: string = ""; // Código ingresado por el usuario
  showVerificationDialog: boolean = false; // Control del dialogo
  isLoggedIn: boolean = false;
  isConfirmed: boolean = false;
  message: string = "";
  messageType: 'success' | 'error' | null = null;
  constructor(private router: Router) {}

  onLogin() {
    if (this.email == "" || this.password == ""){
      this.message = "Todos los espacios son obligatorios";
      this.messageType = "error";
    } else {
      this.message = "Se ha iniciado sesion correctamente";
      this.messageType = "success";
      this.isLoggedIn = true;
      setTimeout(() => {
        this.router.navigate(['/menu-texto']).then(() => {
          console.log('Navegación exitosa');
        }).catch(err => {
          console.error('Error al navegar:', err);
        });
      }, 1000);
    }

    /*Cuando se implemente la logica de la verificacion del codigo, aqui ira otra validacion de que si ya hizo el envio del codigo o no*/

    setTimeout(() => {
      const alert = document.querySelector('.alert');
      if (alert) alert.classList.add('fade-out');
      setTimeout(() => this.clearMessage(), 300);
    }, 3000);

  }

  onSignUp(){
    const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    const passwordRegex = /^(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*()_\-+=<>?{}[\]~]).{8,}$/;

    if(this.email == "" || this.user == "" || this.password == "" || this.confirmPassword == ""){
      this.message = "Todos los espacios son obligatorios";
      this.messageType = "error";
    } else if(this.password != "" && this.confirmPassword != "" && this.password != this.confirmPassword){
      this.message = "Las contraseñas no coinciden";
      this.messageType = "error";
    } else if(!emailRegex.test(this.email)){
      this.message = "El correo no tiene un formato valido";
      this.messageType = "error";
    } else if(!passwordRegex.test(this.password)){
      this.message = "La contraseña debe tener al menos 8 caracteres, una mayúscula, un número y un carácter especial";
      this.messageType = "error";
    } else {
      this.showVerificationDialog = true;
      return;
    }

    setTimeout(() => {
      const alert = document.querySelector('.alert');
      if (alert) alert.classList.add('fade-out');
      setTimeout(() => this.clearMessage(), 300);
    }, 3000);
  }

  verifyCode() {
    if (this.verificationCode === "") {
      this.message = "Debes ingresar el código de verificación";
      this.messageType = "error";
      return;
    }

    // Aquí se validará con el backend
    this.message = "Código verificado correctamente";
    this.messageType = "success";
    this.isConfirmed = true;
    this.showVerificationDialog = false;

    this.email = "";
    this.password = "";
    this.confirmPassword = "";
    this.user = "";
    this.verificationCode = "";

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

  closeDialog() {
    this.showVerificationDialog = false;
  }
}
