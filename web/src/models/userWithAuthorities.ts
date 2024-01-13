import { Role } from "./role";
import Usuario from "./usuario";

export class UserWithAuthorities {
  constructor(
    public user: Usuario,
    public authority: Role[],
  ) {}
}
