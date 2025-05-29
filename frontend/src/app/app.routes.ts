import { Routes } from '@angular/router';

import {UserSelectComponent} from './features/home/userselect.component';
import {MercanciaListComponent} from './features/mercancia/mercancia-list.component';
import {UsuarioListComponent} from './features/usuario/usuario-list.component';
import {LayoutComponent} from './shared/components/layout/layout.component';
import {CargoListComponent} from './features/cargo/cargo-list.component';

export const routes: Routes = [
  { path: '', component: UserSelectComponent }, // home (sin layout)

  {
    path: 'app',
    component: LayoutComponent,
    children: [
      { path: 'usuarios', component: UsuarioListComponent },
      { path: 'mercancias', component: MercanciaListComponent },
      {path: 'cargos', component:CargoListComponent },
    ]
  },
  { path: '**', redirectTo: '', pathMatch: 'full' }
];
