import { Cliente } from "@/models/cliente";
import { Conta } from "@/models/conta";

import axios, { AxiosResponse } from "axios";

export class ContaService {
  url = `${import.meta.env.VITE_BASE_URL}/conta`;
  config = {
    headers: {
      Authorization: sessionStorage.getItem("basicKey"),
    },
  };
  public async createPrimeiraConta(
    cliente: Cliente,
  ): Promise<AxiosResponse<Conta>> {
    return axios.post<Conta>(`${this.url}/primeira-conta`, cliente, {
      headers: {
        Authorization: sessionStorage.getItem("basicKey"),
      },
    });
  }
  public async createConta(cliente: Cliente): Promise<AxiosResponse<Conta>> {
    return axios.post<Conta>(`${this.url}/`, cliente, {
      headers: {
        Authorization: sessionStorage.getItem("basicKey"),
      },
    });
  }

  public async getAllByCliente(uuid: string): Promise<AxiosResponse<Conta[]>> {
    return axios.get<Conta[]>(`${this.url}/all-by-cliente/${uuid}`, {
      headers: {
        Authorization: sessionStorage.getItem("basicKey"),
      },
    });
  }

  public async getByCodigo(cod: number): Promise<AxiosResponse<Conta>> {
    return axios.get<Conta>(`${this.url}/get-by-cod/${cod}`, {
      headers: {
        Authorization: sessionStorage.getItem("basicKey"),
      },
    });
  }
}
