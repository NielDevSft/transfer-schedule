import { Cliente } from "@/models/cliente";
import { Conta } from "@/models/conta";

import axios, { AxiosResponse } from "axios";

export class ContaService {
  url = "http://localhost:8080/conta";
  config = {
    headers: {
      Authorization: sessionStorage.getItem("basicKey"),
    },
  };
  public async createPrimeiraConta(
    cliente: Cliente,
  ): Promise<AxiosResponse<Conta>> {
    return axios.post<Conta>(
      `${this.url}/primeira-conta`,
      cliente,
      this.config,
    );
  }

  public async getAllByCliente(uuid: string): Promise<AxiosResponse<Conta[]>> {
    return axios.get<Conta[]>(
      `${this.url}/all-by-cliente/${uuid}`,
      this.config,
    );
  }

  public async getByCodigo(cod: number): Promise<AxiosResponse<Conta>> {
    return axios.get<Conta>(`${this.url}/get-by-cod/${cod}`, this.config);
  }
}
