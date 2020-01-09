export class ParkingLot {
  id: string;
  number: number;
  status: string;
  updatedAt: Date;

  static fromHttp(pl: ParkingLot): ParkingLot {
    const newParkingLot = new ParkingLot();
    newParkingLot.id = pl.id;
    newParkingLot.number = pl.number;
    newParkingLot.status = pl.status;
    newParkingLot.updatedAt = new Date(pl.updatedAt);
    return newParkingLot;
  }
}
