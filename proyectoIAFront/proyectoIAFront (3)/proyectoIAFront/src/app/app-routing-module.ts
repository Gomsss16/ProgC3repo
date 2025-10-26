import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { Usuarios } from './usuarios/usuarios';
import { MenuTexto } from './menu-texto/menu-texto';
import { MenuArchivo} from './menu-archivo/menu-archivo';
import { MenuVideo} from './menu-video/menu-video';
import {MenuImagen} from './menu-imagen/menu-imagen';


const routes: Routes = [
  { path: '', redirectTo: 'usuarios', pathMatch: 'full' },
  { path: 'usuarios', component: Usuarios },
  { path: 'menu-texto', component: MenuTexto },
  { path: 'menu-archivo', component: MenuArchivo },
  {path: 'menu-video', component: MenuVideo},
  { path: 'menu-imagen', component: MenuImagen },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
