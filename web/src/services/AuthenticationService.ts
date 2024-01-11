import Axios from "axios-observable";
import { retry } from "rxjs";

export class DogInfoService {
  getDogFacts(id: number) {
    return Axios.get(`/dog-fact?ID=${id}`)
      .pipe(retry(3))
      .subscribe({
        next: (response) => console.log(response),
        error: (error) => console.log(error),
      });
  }
}
