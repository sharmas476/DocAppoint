import { AppComponent } from './../app.component';
import { environment } from './../../environments/environment';
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

import { JwtResponse } from './jwt-response';
import { AuthLoginInfo } from './login-info';
import { SignUpInfo } from './signup-info';
import { TokenStorageService } from './token-storage.service';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  env = environment;
  private loginUrl = this.env.url+'/api/auth/signin';
  private signupUrl = this.env.url+'/api/auth/signup';
  constructor(private http: HttpClient, private token: TokenStorageService ) {
  }

  attemptAuth(credentials: AuthLoginInfo): Observable<JwtResponse> {
    return this.http.post<JwtResponse>(this.loginUrl, credentials, httpOptions);
  }

  signUp(info: SignUpInfo): Observable<string> {
    return this.http.post<string>(this.signupUrl, info, httpOptions);
  }

  isAuthenticated(): boolean {
    const token = this.token.getToken();
    if (token != null) {
        return true;
    }
    return false;
  }

  logout() {
    this.token.signOut();
    location.reload(true);
  }

}
