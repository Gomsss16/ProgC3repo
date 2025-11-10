import { NgModule, provideBrowserGlobalErrorListeners } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing-module';
import { App } from './app';
import { Usuarios } from './usuarios/usuarios';
import { MenuTexto } from './menu-texto/menu-texto';
import { MenuArchivo } from './menu-archivo/menu-archivo';  // 👈 IMPORTA
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { JwtInterceptorService } from './services/auth/jwt-interceptor.service';
import { ErrorInterceptorService } from './services/auth/error-interceptor.service';

@NgModule({
  declarations: [
    App,
    Usuarios,
    MenuTexto
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    MenuArchivo  // 👈 AGREGA AQUÍ (porque es standalone)
  ],
  providers: [
    provideBrowserGlobalErrorListeners(),
    {provide: HTTP_INTERCEPTORS, useClass: JwtInterceptorService, multi: true},
    {provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptorService, multi: true}
  ],
  bootstrap: [App]
})
export class AppModule { }
