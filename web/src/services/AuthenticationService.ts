import { UserWithAuthorities } from "@/models/userWithAuthorities";

import axios, { AxiosResponse } from "axios";

export class AuthenticationService {
  url = `${import.meta.env.VITE_BASE_URL}/authentication`;

  public async login(
    userName: string,
    passWord: string,
  ): Promise<AxiosResponse<UserWithAuthorities>> {
    const basicKey = "Basic " + btoa(userName + ":" + passWord);
    sessionStorage.setItem("basicKey", basicKey);

    return axios
      .post<UserWithAuthorities>(
        `${this.url}/login`,
        {},
        {
          headers: {
            "Content-Type": "application/json",
            Authorization: basicKey,
          },
        },
      )
      .catch((e) => {
        sessionStorage.removeItem("basicKey");
        throw e;
      });
  }
}
