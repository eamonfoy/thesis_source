import { Moment } from 'moment';

export interface IDepartureFlight {
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
  gate?: string;
  status?: string;
  statusText?: string;
}

export class DepartureFlight implements IDepartureFlight {
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
    public gate?: string,
    public status?: string,
    public statusText?: string
  ) {}
}
