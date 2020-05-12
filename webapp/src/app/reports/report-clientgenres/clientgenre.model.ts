import {Client} from "../../clients/shared/client.model";

export class ClientGenre {
  client: Client
  genre: string
}

export class ClientGenres {
  clientGenres: Array<ClientGenre>
}
