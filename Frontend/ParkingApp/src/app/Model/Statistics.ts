import { ParkingLot } from './ParkingLot';

export class Statistics {
  id: number;
  lot: ParkingLot;
  updatedAt: Date;
  status: string;

  constructor(id?: number, parkingLotNumber?: ParkingLot, status?: string, updatedAt?: Date) {
    if (id) {
      this.id = id;
      this.lot = parkingLotNumber;
      this.status = status;
      this.updatedAt = updatedAt;
    }
  }

  static fromHttp(stats: Statistics): Statistics {
    const newStats = new Statistics();
    newStats.id = stats.id;
    newStats.lot = ParkingLot.fromHttp(stats.lot);
    newStats.status = stats.status;
    newStats.updatedAt = new Date(stats.updatedAt);
    return newStats;
  }
}
