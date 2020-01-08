export class ParkingLot {
  id: string;
  number: string;
  status: string;
  date: Date;

  static fromHttp(pl: ParkingLot): ParkingLot {
    const newParkingLot = new ParkingLot();
    newParkingLot.id = pl.id;
    newParkingLot.number = pl.number;
    newParkingLot.status = pl.status;
    newParkingLot.date = new Date(pl.date);
    return newParkingLot;
  }
}
