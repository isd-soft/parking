import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {map} from 'rxjs/operators';

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

authenticationServiceLogin(username: string, password: string) {
    return this.http.get(`http://localhost:8080/login`,
      {headers: {authorization: this.createBasicAuthToken(username, password)}}).pipe(map((res) => {
      this.username = username;
      this.password = password;
      this.registerSuccessfulLogin(username);
    }));
  }

authenticationServiceRegistration(username: string, password: string) {

    // TODO: registration post logic here

    return this.http.get(`http://localhost:8080/registration`,
      {headers: {authorization: this.createBasicAuthToken(username, password)}}).pipe(map((res) => {
      this.username = username;
      this.password = password;
      this.registerSuccessfulLogin(username);
    }));
  }

createBasicAuthToken(username: string, password: string) {
    return 'Basic ' + window.btoa(username + ':' + password);
  }

registerSuccessfulLogin(username) {
    sessionStorage.setItem(this.USER_NAME_SESSION_ATTRIBUTE_NAME, username);
  }

logout() {
    sessionStorage.removeItem(this.USER_NAME_SESSION_ATTRIBUTE_NAME);
    this.username = null;
    this.password = null;
  }

isUserLoggedIn() {
    const user = sessionStorage.getItem(this.USER_NAME_SESSION_ATTRIBUTE_NAME);
    if (user === null) {
      return false;
    }
    return true;
  }

isAdminLoggedIn() {
    if (this.isUserLoggedIn() && this.getLoggedInUserName() === this.admin) {
      return true;
    } else {
      return false;
    }
  }

getLoggedInUserName() {
    const user = sessionStorage.getItem(this.USER_NAME_SESSION_ATTRIBUTE_NAME);
    if (user === null) {
      return '';
    }
    return user;
  }
}
