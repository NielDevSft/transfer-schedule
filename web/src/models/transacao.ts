export class Transacao {
  constructor(
    public id: number,
    public description: string,
    public dueDate: Date,
    public priority: number,
    public dtaCreatedAt: Date,
    public dtaUpdatedAt: Date
  ) {}
}
