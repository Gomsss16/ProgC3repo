import { NgModule, provideBrowserGlobalErrorListeners } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing-module';
import { App } from './app';
import { Usuarios } from './usuarios/usuarios';
import { MenuTexto } from './menu-texto/menu-texto';
import { MenuArchivo } from './menu-archivo/menu-archivo';
import { MenuVideo } from './menu-video/menu-video';
import { MenuImagen } from './menu-imagen/menu-imagen';


@NgModule({
  declarations: [
    App,
    Usuarios,
    MenuTexto,
    MenuArchivo,
    MenuVideo,
    MenuImagen,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule
  ],
  providers: [
    provideBrowserGlobalErrorListeners()
  ],
  bootstrap: [App]
})
export class AppModule { }
