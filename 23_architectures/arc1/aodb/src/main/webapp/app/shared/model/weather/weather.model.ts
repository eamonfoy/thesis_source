import { Moment } from 'moment';

export interface IWeather {
  id?: number;
  airportCode?: string;
  forecastDate?: Moment;
  dayName?: string;
  highTemperatureValue?: number;
  lowTemperatureValue?: number;
  feelsLikeHighTemperature?: number;
  feelsLikeLowTemperature?: number;
  phrase?: string;
  probabilityOfPrecip?: number;
  probabilityOfPrecipUnits?: string;
  nightPhrase?: string;
  nightIcon?: number;
  nightProbabilityOfPrecip?: number;
  nightProbabilityOfPrecipUnits?: string;
  icon?: number;
}

export class Weather implements IWeather {
  constructor(
    public id?: number,
    public airportCode?: string,
    public forecastDate?: Moment,
    public dayName?: string,
    public highTemperatureValue?: number,
    public lowTemperatureValue?: number,
    public feelsLikeHighTemperature?: number,
    public feelsLikeLowTemperature?: number,
    public phrase?: string,
    public probabilityOfPrecip?: number,
    public probabilityOfPrecipUnits?: string,
    public nightPhrase?: string,
    public nightIcon?: number,
    public nightProbabilityOfPrecip?: number,
    public nightProbabilityOfPrecipUnits?: string,
    public icon?: number
  ) {}
}
