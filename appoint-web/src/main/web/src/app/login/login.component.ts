import { AppComponent } from './../app.component';
import { Component, OnInit } from '@angular/core';

import { AuthService } from '../auth/auth.service';
import { TokenStorageService } from '../auth/token-storage.service';
import { AuthLoginInfo } from '../auth/login-info';
import { Router } from '@angular/router';
import { MessageService } from 'primeng/components/common/messageservice';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html', 
  styleUrls: ['./login.component.css'],
  providers: [MessageService]
})
export class LoginComponent implements OnInit {
  form: any = {};
  isLoggedIn = false;
  isLoginFailed = false;
  errorMessage = '';
  roles: string[] = [];
  private loginInfo: AuthLoginInfo;
  private authority: string;

  constructor(private messageService: MessageService, private authService: AuthService, private tokenStorage: TokenStorageService, private route: Router, private app:AppComponent) { }

  ngOnInit() {
    if (this.tokenStorage.getToken()) {
      this.isLoggedIn = true;
      this.roles = this.tokenStorage.getAuthorities();
    }
  }

  onSubmit() {

    this.loginInfo = new AuthLoginInfo(
      this.form.phone,
      this.form.password);

    this.authService.attemptAuth(this.loginInfo).subscribe(
      data => {
        this.tokenStorage.saveToken(data.accessToken);
        this.tokenStorage.saveUsername(data.username);
        this.tokenStorage.saveAuthorities(data.authorities);

        this.isLoginFailed = false;
        this.isLoggedIn = true;
        this.roles = this.tokenStorage.getAuthorities();
        this.navigate();
      },
      error => {
        this.errorMessage = error.error.message;
        this.showAlert(this.errorMessage);
        this.isLoginFailed = true;
      }
    );
  }

  reloadPage() {
    window.location.reload();
  }
  navigate(){
    if (this.tokenStorage.getToken()) {
      this.roles = this.tokenStorage.getAuthorities();
      this.roles.every(role => {
        if (role === 'ROLE_ADMIN') {
          this.authority = 'doctorProfile';
          return false;
        } else if (role === 'ROLE_PM') {
          this.authority = 'profile';
          return false;
        }
        this.authority = 'user';
        return true;
      });
      this.app.ngOnInit();
      this.route.navigate(['/'+this.authority]);
    }
  }

  showAlert(msg:string){
    this.messageService.add({key: 'tc', severity:"error", summary:'Error', detail:msg});
  }
}
