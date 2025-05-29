import { Component, Input, Output, EventEmitter } from '@angular/core';
import {NgClass} from '@angular/common';

@Component({
  selector: 'app-notification',
  templateUrl: './notification.component.html',
  styleUrls: ['./notification.component.css'],
  standalone: true,
  imports: [NgClass]
})
export class NotificationComponent {
  @Input() type: 'success' | 'info' | 'error' | 'security' = 'info';
  @Input() title: string = '';
  @Input() message: string = '';
  @Output() onClose: EventEmitter<void> = new EventEmitter<void>();

  closeNotification(): void {
    this.onClose.emit();
  }
}
