import { ParkingLot } from './ParkingLot';

export class Statistics {
  id: number;
  lotId: number;
  updatedAt: Date;
  status: string;

  constructor(id?: number, parkingLotNumber?: number, status?: string, updatedAt?: Date) {
    if (id) {
      this.id = id;
      this.lotId = parkingLotNumber;
      this.status = status;
      this.updatedAt = updatedAt;
    }
  }

  static fromHttp(stats: Statistics): Statistics {
    const newStats = new Statistics();
    newStats.id = stats.id;
    newStats.lotId = stats.lotId;
    newStats.status = stats.status;
    newStats.updatedAt = new Date(stats.updatedAt);
    return newStats;
  }
}
