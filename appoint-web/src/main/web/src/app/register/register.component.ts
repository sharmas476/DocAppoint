import { Component, OnInit } from '@angular/core';

import { AuthService } from '../auth/auth.service';
import { SignUpInfo } from '../auth/signup-info';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  form: any = {};
  signupInfo: SignUpInfo;
  isSignedUp = false;
  isSignUpFailed = false;
  errorMessage = '';
  msgs=[];
  constructor(private authService: AuthService) { }

  ngOnInit() { }

  onSubmit() {
    this.signupInfo = new SignUpInfo(
      this.form.name,
      this.form.phone,
      this.form.email,
      this.form.password,
      this.form.age,
      this.form.gender);

    this.authService.signUp(this.signupInfo).subscribe(
      data => {
        this.isSignedUp = true;
        this.isSignUpFailed = false;
      },
      error => {
        this.errorMessage = error.error.message;
        this.isSignUpFailed = true;
        this.showError(this.errorMessage);
      }
    );
  }
  showError(err:string) {
    this.msgs = [];
    this.msgs.push({severity:'error', summary:'Error: ', detail:err});
}
}
