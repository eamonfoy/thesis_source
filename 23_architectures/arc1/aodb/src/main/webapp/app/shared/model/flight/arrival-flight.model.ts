import { Moment } from 'moment';

export interface IArrivalFlight {
  id?: number;
  actual?: Moment;
  estimated?: Moment;
  scheduled?: Moment;
  city?: string;
  aircraft?: string;
  terminal?: string;
  duration?: string;
  tailNumber?: string;
  airportCode?: string;
  airline?: string;
  flightNumber?: string;
  claim?: string;
  status?: string;
  statusText?: string;
}

export class ArrivalFlight implements IArrivalFlight {
  constructor(
    public id?: number,
    public actual?: Moment,
    public estimated?: Moment,
    public scheduled?: Moment,
    public city?: string,
    public aircraft?: string,
    public terminal?: string,
    public duration?: string,
    public tailNumber?: string,
    public airportCode?: string,
    public airline?: string,
    public flightNumber?: string,
    public claim?: string,
    public status?: string,
    public statusText?: string
  ) {}
}
