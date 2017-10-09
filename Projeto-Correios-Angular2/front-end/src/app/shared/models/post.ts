

export class Post {
  public _id: string;
  public criada_em: Date;
  public atualizado: Date;
  
  constructor(
    public titulo: string,
    public resumo: string,
    public imagen: string,
    public texto: string,
    public autor: string,
    public dev: boolean
  ) { }


}
