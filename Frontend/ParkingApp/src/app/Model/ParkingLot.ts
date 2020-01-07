export class ParkingLot {
  id: number;
  name: string;
  isFree: boolean;
  dateTime: Date;

  static fromHttp(pl: ParkingLot): ParkingLot {
    const newParkingLot = new ParkingLot();
    newParkingLot.id = pl.id;
    newParkingLot.name = pl.name;
    newParkingLot.isFree = pl.isFree;
    newParkingLot.dateTime = new Date(pl.dateTime);
    return newParkingLot;
  }
}
