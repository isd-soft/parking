export class Statistics {
  id: string;
  parkingLotNumber: string;
  status: string;
  updatedAt: Date;

  constructor(id?: string, parkingLotNumber?: string, status?: string, updatedAt?: Date) {
    if (id) {
      this.id = id;
      this.parkingLotNumber = parkingLotNumber;
      this.status = status;
      this.updatedAt = updatedAt;
    }
  }

  static fromHttp(stats: Statistics): Statistics {
    const newStats = new Statistics();
    newStats.id = stats.id;
    newStats.parkingLotNumber = stats.parkingLotNumber;
    newStats.status = stats.status;
    newStats.updatedAt = new Date(stats.updatedAt);
    return newStats;
  }
}
