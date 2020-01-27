import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {environment} from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  // BASE_PATH: 'http://localhost:8080'
  USER_NAME_SESSION_ATTRIBUTE_NAME = 'authenticatedUser';
  ADMIN_NAME_SESSION_ATTRIBUTE_NAME = 'authenticatedAdmin';

  public username: string;
  public password: string;

  // local mockup for test admin
  // map = new Map();
  public admin = 'admin';
  public adminPassword = 'aRduin1$';

  // user1 test
  public user = 'user1';
  public userPassword = 'aRduin1$';

  // user2 test
  public user1 = 'user2';
  public userPassword1 = 'aRduin1$';

  constructor(private http: HttpClient) {

  }

  // TODO: alternative login method
  authenticationServiceLogin(username: string, password: string) {

    const url = environment.restUrl + '/login';

    console.log('Auth login');
    console.log('Login ' + username + '  ' + password);

    return this.http.post<Observable<boolean>>(url, {
      headers: {
        Accept: 'application/json',
        Authorization: this.createBasicAuthToken(username, password)
      },

      username,
      password
    });
  }

  authenticationServiceRegistration(username: string, password: string) {

    const url = environment.restUrl + `/registration`;

    console.log('Auth registration');
    console.log('Reg credentials ' + username + '  ' + password);

    return this.http.post<Observable<boolean>>(url, {
      headers: {
        Accept: 'application/json',
      },

      username,
      password
    });
  }

  createBasicAuthToken(username: string, password: string) {
    return 'Basic ' + window.btoa(username + ':' + password);
  }

  registerSuccessfulLogin(username) {
    sessionStorage.setItem(this.USER_NAME_SESSION_ATTRIBUTE_NAME, username);
    sessionStorage.setItem(
      'token',
      btoa(this.username + ':' + this.password)
    );
  }

  authenticationServiceLogout() {
    sessionStorage.removeItem(this.USER_NAME_SESSION_ATTRIBUTE_NAME);
    sessionStorage.removeItem('token');

    this.username = null;
    this.password = null;
  }

  isUserLoggedIn() {
    const user = sessionStorage.getItem(this.USER_NAME_SESSION_ATTRIBUTE_NAME);
    return user !== null;
  }

  isAdminLoggedIn() {
    return this.isUserLoggedIn() && this.getLoggedInUserName() === this.admin;
  }

  getLoggedInUserName() {
    const user = sessionStorage.getItem(this.USER_NAME_SESSION_ATTRIBUTE_NAME);
    if (user === null) {
      return '';
    }
    return user;
  }
}
