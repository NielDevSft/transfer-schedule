import { Cliente } from "./cliente";

export class Conta {
  constructor(
    public uuid: string,
    public deleted: boolean,
    public dtaCreateAt: Date,
    public dtaUpdateAt: Date,
    public dtaDeleteAt: Date | null,
    public codConta: number,
    public cliente: Cliente,
    public contaLabel?: string,
  ) {}
}
