import { Usuario } from "./usuario";
export class Cliente {
  constructor(
    public desCpf: string,
    public desNome: string,
    public numTelefo: string,
    public idUsuario: number,
    public usuario: Usuario,
    public dtaNascimento: Date,
    public uuid?: string,
    public codCliente?: string,
  ) {}
}

export default Usuario;
