import { Transacao } from "@/models/transacao";

import axios, { AxiosResponse } from "axios";

export class TransacaoService {
  url = `${import.meta.env.VITE_BASE_URL}/transacao`;
  config = {
    headers: {
      Authorization: sessionStorage.getItem("basicKey"),
    },
  };

  public async create(transacao: Transacao): Promise<AxiosResponse<Transacao>> {
    return axios.post(`${this.url}/nova-transacao`, transacao, {
      headers: {
        Authorization: sessionStorage.getItem("basicKey"),
      },
    });
  }

  public async getAllByClienteResponsavelUuid(
    uuid: string,
  ): Promise<AxiosResponse<Transacao[]>> {
    return axios.get<Transacao[]>(
      `${this.url}/all-by-cliente-responsavel/${uuid}`,
      {
        headers: {
          Authorization: sessionStorage.getItem("basicKey"),
        },
      },
    );
  }
}
