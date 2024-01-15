export class Transacao {
  constructor(
    public uuid: string,
    public codTransacao: number,
    public numValTransferencia: number,
    public numValTaxaPrevista: number,
    public dtaTransacao: Date,
    public contaOrigemUuid: string,
    public contaDestinoUuid: string,
    public clienteResponsavelUuid: string,
    public indTipoOperacao?: TIPOOPERACAO,
    public dtaCreateAt?: Date,
    public dtaUpdateAt?: Date,
    public dtaDeleteAt?: Date,
  ) {}
}

export enum TIPOOPERACAO {
  A = 1,
  B = 2,
  C = 3,
  D = 4,
}
