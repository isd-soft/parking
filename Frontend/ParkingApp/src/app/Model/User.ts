export class User {
  id: string;
  name: string;
  password: string;

  constructor(id?: string, name?: string, password?: string) {
    if (id && name) {
      this.id = id;
      this.name = name;
      this.password = password;
    }
  }
}
