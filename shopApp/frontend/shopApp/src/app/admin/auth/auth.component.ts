import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule, NgForm, NgModel } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-auth',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './auth.component.html',
  styleUrl: './auth.component.css'
})
export class AuthComponent implements OnInit {

  public username: string = "";
  public password: string = "";
  public errorMessage: string = "";

  constructor(private router: Router, private authService: AuthService) {}

  ngOnInit(): void {}

  login(form: NgForm) {
    if (form.valid) {
      this.authService.login(this.username, this.password).subscribe({
        next: (response) => {
          if (response.success) {
            this.router.navigate(['/admin/main']);
          } else {
            this.errorMessage = response.message;
          }
        },
        error: (err) => {
          console.error(err);  
          this.errorMessage = err.error?.message || "Giriş işlemi sırasında bir hata oluştu.";
        }
        
      });
    } else {
      this.errorMessage = "Lütfen tüm alanları doldurun.";
    }
  }
  
}
