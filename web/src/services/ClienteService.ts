import { Cliente } from "@/models/cliente";

import axios, { AxiosResponse } from "axios";

export class ClienteService {
  url = `${import.meta.env.VITE_BASE_URL}/cliente`;
  config = {
    headers: {
      Authorization: sessionStorage.getItem("basicKey"),
    },
  };

  public async create(cliente: Cliente): Promise<AxiosResponse<Cliente>> {
    return axios.post<Cliente>(`${this.url}/`, cliente, {
      headers: {
        Authorization: sessionStorage.getItem("basicKey"),
      },
    });
  }

  public async getAllByUser(id: number): Promise<AxiosResponse<Cliente>> {
    return axios.get<Cliente>(`${this.url}/all-by-user/${id}`, {
      headers: {
        Authorization: sessionStorage.getItem("basicKey"),
      },
    });
  }
}
