import { Cliente } from "@/models/cliente";

import axios, { AxiosResponse } from "axios";

export class ClienteService {
  url = "http://localhost:8080/cliente";
  config = {
    headers: {
      Authorization: sessionStorage.getItem("basicKey"),
    },
  };

  public async create(cliente: Cliente): Promise<AxiosResponse<Cliente>> {
    return axios.post<Cliente>(`${this.url}/`, cliente, this.config);
  }

  public async getAllByUser(id: number): Promise<AxiosResponse<Cliente>> {
    return axios.get<Cliente>(`${this.url}/all-by-user/${id}`, this.config);
  }
}
