import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { Usuarios } from './usuarios/usuarios';
import { MenuTexto } from './menu-texto/menu-texto';
import { Main } from './main/main';
import { MenuVideo } from './menu-video/menu-video';
import { MenuArchivo } from './menu-archivo/menu-archivo';
import { MenuImagen } from './menu-imagen/menu-imagen';
import { Perfil } from './perfil/perfil';
import { HistorialComponent } from './historial/historial.component';
import { AdminHistorialComponent } from './admin-historial/admin-historial.component';
import { AdminGuard } from './guards/admin.guard';

const routes: Routes = [

  { path: '', component: Usuarios },
  {
    path: 'panel',
    component: Main,
    children: [
      { path: 'menu-texto', component: MenuTexto },
      { path: 'menu-archivo', component: MenuArchivo },
      { path: 'menu-imagen', component: MenuImagen },
      { path: 'menu-video', component: MenuVideo },
      { path: 'perfil', component: Perfil },
      { path: 'mi-historial', component: HistorialComponent },
      {
        path: 'admin/historial',
        component: AdminHistorialComponent,
        canActivate: [AdminGuard]  // 🔥 PROTEGIDO
      },

      { path: '', redirectTo: 'menu-texto', pathMatch: 'full' }
    ]
  },

];

@NgModule({
  imports: [RouterModule.forRoot(routes, { onSameUrlNavigation: 'reload' })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
