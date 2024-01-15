export class Usuario {
  constructor(
    public id: number,
    public username: string,
    public password: string,
    public enabled: boolean,
  ) {}
}

export default Usuario;
