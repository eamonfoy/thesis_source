import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../../page-objects/jhi-page-objects';

import { WeatherComponentsPage, WeatherDeleteDialog, WeatherUpdatePage } from './weather.page-object';

const expect = chai.expect;

describe('Weather e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let weatherComponentsPage: WeatherComponentsPage;
  let weatherUpdatePage: WeatherUpdatePage;
  let weatherDeleteDialog: WeatherDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Weathers', async () => {
    await navBarPage.goToEntity('weather');
    weatherComponentsPage = new WeatherComponentsPage();
    await browser.wait(ec.visibilityOf(weatherComponentsPage.title), 5000);
    expect(await weatherComponentsPage.getTitle()).to.eq('aodbApp.weatherWeather.home.title');
    await browser.wait(ec.or(ec.visibilityOf(weatherComponentsPage.entities), ec.visibilityOf(weatherComponentsPage.noResult)), 1000);
  });

  it('should load create Weather page', async () => {
    await weatherComponentsPage.clickOnCreateButton();
    weatherUpdatePage = new WeatherUpdatePage();
    expect(await weatherUpdatePage.getPageTitle()).to.eq('aodbApp.weatherWeather.home.createOrEditLabel');
    await weatherUpdatePage.cancel();
  });

  it('should create and save Weathers', async () => {
    const nbButtonsBeforeCreate = await weatherComponentsPage.countDeleteButtons();

    await weatherComponentsPage.clickOnCreateButton();

    await promise.all([
      weatherUpdatePage.setAirportCodeInput('airportCode'),
      weatherUpdatePage.setForecastDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      weatherUpdatePage.setDayNameInput('dayName'),
      weatherUpdatePage.setHighTemperatureValueInput('5'),
      weatherUpdatePage.setLowTemperatureValueInput('5'),
      weatherUpdatePage.setFeelsLikeHighTemperatureInput('5'),
      weatherUpdatePage.setFeelsLikeLowTemperatureInput('5'),
      weatherUpdatePage.setPhraseInput('phrase'),
      weatherUpdatePage.setProbabilityOfPrecipInput('5'),
      weatherUpdatePage.setProbabilityOfPrecipUnitsInput('probabilityOfPrecipUnits'),
      weatherUpdatePage.setNightPhraseInput('nightPhrase'),
      weatherUpdatePage.setNightIconInput('5'),
      weatherUpdatePage.setNightProbabilityOfPrecipInput('5'),
      weatherUpdatePage.setNightProbabilityOfPrecipUnitsInput('nightProbabilityOfPrecipUnits'),
      weatherUpdatePage.setIconInput('5')
    ]);

    expect(await weatherUpdatePage.getAirportCodeInput()).to.eq('airportCode', 'Expected AirportCode value to be equals to airportCode');
    expect(await weatherUpdatePage.getForecastDateInput()).to.contain(
      '2001-01-01T02:30',
      'Expected forecastDate value to be equals to 2000-12-31'
    );
    expect(await weatherUpdatePage.getDayNameInput()).to.eq('dayName', 'Expected DayName value to be equals to dayName');
    expect(await weatherUpdatePage.getHighTemperatureValueInput()).to.eq('5', 'Expected highTemperatureValue value to be equals to 5');
    expect(await weatherUpdatePage.getLowTemperatureValueInput()).to.eq('5', 'Expected lowTemperatureValue value to be equals to 5');
    expect(await weatherUpdatePage.getFeelsLikeHighTemperatureInput()).to.eq(
      '5',
      'Expected feelsLikeHighTemperature value to be equals to 5'
    );
    expect(await weatherUpdatePage.getFeelsLikeLowTemperatureInput()).to.eq(
      '5',
      'Expected feelsLikeLowTemperature value to be equals to 5'
    );
    expect(await weatherUpdatePage.getPhraseInput()).to.eq('phrase', 'Expected Phrase value to be equals to phrase');
    expect(await weatherUpdatePage.getProbabilityOfPrecipInput()).to.eq('5', 'Expected probabilityOfPrecip value to be equals to 5');
    expect(await weatherUpdatePage.getProbabilityOfPrecipUnitsInput()).to.eq(
      'probabilityOfPrecipUnits',
      'Expected ProbabilityOfPrecipUnits value to be equals to probabilityOfPrecipUnits'
    );
    expect(await weatherUpdatePage.getNightPhraseInput()).to.eq('nightPhrase', 'Expected NightPhrase value to be equals to nightPhrase');
    expect(await weatherUpdatePage.getNightIconInput()).to.eq('5', 'Expected nightIcon value to be equals to 5');
    expect(await weatherUpdatePage.getNightProbabilityOfPrecipInput()).to.eq(
      '5',
      'Expected nightProbabilityOfPrecip value to be equals to 5'
    );
    expect(await weatherUpdatePage.getNightProbabilityOfPrecipUnitsInput()).to.eq(
      'nightProbabilityOfPrecipUnits',
      'Expected NightProbabilityOfPrecipUnits value to be equals to nightProbabilityOfPrecipUnits'
    );
    expect(await weatherUpdatePage.getIconInput()).to.eq('5', 'Expected icon value to be equals to 5');

    await weatherUpdatePage.save();
    expect(await weatherUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await weatherComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Weather', async () => {
    const nbButtonsBeforeDelete = await weatherComponentsPage.countDeleteButtons();
    await weatherComponentsPage.clickOnLastDeleteButton();

    weatherDeleteDialog = new WeatherDeleteDialog();
    expect(await weatherDeleteDialog.getDialogTitle()).to.eq('aodbApp.weatherWeather.delete.question');
    await weatherDeleteDialog.clickOnConfirmButton();

    expect(await weatherComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
