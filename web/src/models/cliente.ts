import { Usuario } from "./usuario";
export class Cliente {
  constructor(
    public desNomeCompleto: string | null,
    public desCpf: string | null,
    public dtaNascimento: Date | null,
    public usuairo: Usuario,
    public uuid?: string,
    public codCliente?: number,
    public dtaCreateAt?: Date,
    public dtaUpdateAt?: Date,
    public dtaDeleteAt?: Date | null,
    public deleted?: boolean,
  ) {}
}

export default Usuario;
