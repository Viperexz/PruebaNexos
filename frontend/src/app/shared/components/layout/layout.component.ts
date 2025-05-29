import { Component } from '@angular/core';
import { RouterOutlet, RouterModule } from '@angular/router';
import {SidebarComponent} from '../sidebar/sidebar.component'; // ✅ IMPORTANTE

@Component({
  selector: 'app-main-layout',
  standalone: true,
  imports: [RouterOutlet, RouterModule, SidebarComponent], // ✅ Asegúrate de esto
  templateUrl: './layout.component.html',
  styleUrls: ['./layout.component.css']
})
export class LayoutComponent {}
