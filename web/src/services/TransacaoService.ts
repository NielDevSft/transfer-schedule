import { Transacao } from "@/models/transacao";

import axios, { AxiosResponse } from "axios";

export class TransacaoService {
  url = "http://localhost:8080/transacao";
  config = {
    headers: {
      Authorization: sessionStorage.getItem("basicKey"),
    },
  };

  public async getAll(): Promise<AxiosResponse<Transacao[]>> {
    return axios.get<Transacao[]>(`${this.url}/all`, this.config);
  }
}
