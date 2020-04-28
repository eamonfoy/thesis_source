import { element, by, ElementFinder } from 'protractor';

export class WeatherComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-weather div table .btn-danger'));
  title = element.all(by.css('jhi-weather div h2#page-heading span')).first();
  noResult = element(by.id('no-result'));
  entities = element(by.id('entities'));

  async clickOnCreateButton(): Promise<void> {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton(): Promise<void> {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons(): Promise<number> {
    return this.deleteButtons.count();
  }

  async getTitle(): Promise<string> {
    return this.title.getAttribute('jhiTranslate');
  }
}

export class WeatherUpdatePage {
  pageTitle = element(by.id('jhi-weather-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  airportCodeInput = element(by.id('field_airportCode'));
  forecastDateInput = element(by.id('field_forecastDate'));
  dayNameInput = element(by.id('field_dayName'));
  highTemperatureValueInput = element(by.id('field_highTemperatureValue'));
  lowTemperatureValueInput = element(by.id('field_lowTemperatureValue'));
  feelsLikeHighTemperatureInput = element(by.id('field_feelsLikeHighTemperature'));
  feelsLikeLowTemperatureInput = element(by.id('field_feelsLikeLowTemperature'));
  phraseInput = element(by.id('field_phrase'));
  probabilityOfPrecipInput = element(by.id('field_probabilityOfPrecip'));
  probabilityOfPrecipUnitsInput = element(by.id('field_probabilityOfPrecipUnits'));
  nightPhraseInput = element(by.id('field_nightPhrase'));
  nightIconInput = element(by.id('field_nightIcon'));
  nightProbabilityOfPrecipInput = element(by.id('field_nightProbabilityOfPrecip'));
  nightProbabilityOfPrecipUnitsInput = element(by.id('field_nightProbabilityOfPrecipUnits'));
  iconInput = element(by.id('field_icon'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setAirportCodeInput(airportCode: string): Promise<void> {
    await this.airportCodeInput.sendKeys(airportCode);
  }

  async getAirportCodeInput(): Promise<string> {
    return await this.airportCodeInput.getAttribute('value');
  }

  async setForecastDateInput(forecastDate: string): Promise<void> {
    await this.forecastDateInput.sendKeys(forecastDate);
  }

  async getForecastDateInput(): Promise<string> {
    return await this.forecastDateInput.getAttribute('value');
  }

  async setDayNameInput(dayName: string): Promise<void> {
    await this.dayNameInput.sendKeys(dayName);
  }

  async getDayNameInput(): Promise<string> {
    return await this.dayNameInput.getAttribute('value');
  }

  async setHighTemperatureValueInput(highTemperatureValue: string): Promise<void> {
    await this.highTemperatureValueInput.sendKeys(highTemperatureValue);
  }

  async getHighTemperatureValueInput(): Promise<string> {
    return await this.highTemperatureValueInput.getAttribute('value');
  }

  async setLowTemperatureValueInput(lowTemperatureValue: string): Promise<void> {
    await this.lowTemperatureValueInput.sendKeys(lowTemperatureValue);
  }

  async getLowTemperatureValueInput(): Promise<string> {
    return await this.lowTemperatureValueInput.getAttribute('value');
  }

  async setFeelsLikeHighTemperatureInput(feelsLikeHighTemperature: string): Promise<void> {
    await this.feelsLikeHighTemperatureInput.sendKeys(feelsLikeHighTemperature);
  }

  async getFeelsLikeHighTemperatureInput(): Promise<string> {
    return await this.feelsLikeHighTemperatureInput.getAttribute('value');
  }

  async setFeelsLikeLowTemperatureInput(feelsLikeLowTemperature: string): Promise<void> {
    await this.feelsLikeLowTemperatureInput.sendKeys(feelsLikeLowTemperature);
  }

  async getFeelsLikeLowTemperatureInput(): Promise<string> {
    return await this.feelsLikeLowTemperatureInput.getAttribute('value');
  }

  async setPhraseInput(phrase: string): Promise<void> {
    await this.phraseInput.sendKeys(phrase);
  }

  async getPhraseInput(): Promise<string> {
    return await this.phraseInput.getAttribute('value');
  }

  async setProbabilityOfPrecipInput(probabilityOfPrecip: string): Promise<void> {
    await this.probabilityOfPrecipInput.sendKeys(probabilityOfPrecip);
  }

  async getProbabilityOfPrecipInput(): Promise<string> {
    return await this.probabilityOfPrecipInput.getAttribute('value');
  }

  async setProbabilityOfPrecipUnitsInput(probabilityOfPrecipUnits: string): Promise<void> {
    await this.probabilityOfPrecipUnitsInput.sendKeys(probabilityOfPrecipUnits);
  }

  async getProbabilityOfPrecipUnitsInput(): Promise<string> {
    return await this.probabilityOfPrecipUnitsInput.getAttribute('value');
  }

  async setNightPhraseInput(nightPhrase: string): Promise<void> {
    await this.nightPhraseInput.sendKeys(nightPhrase);
  }

  async getNightPhraseInput(): Promise<string> {
    return await this.nightPhraseInput.getAttribute('value');
  }

  async setNightIconInput(nightIcon: string): Promise<void> {
    await this.nightIconInput.sendKeys(nightIcon);
  }

  async getNightIconInput(): Promise<string> {
    return await this.nightIconInput.getAttribute('value');
  }

  async setNightProbabilityOfPrecipInput(nightProbabilityOfPrecip: string): Promise<void> {
    await this.nightProbabilityOfPrecipInput.sendKeys(nightProbabilityOfPrecip);
  }

  async getNightProbabilityOfPrecipInput(): Promise<string> {
    return await this.nightProbabilityOfPrecipInput.getAttribute('value');
  }

  async setNightProbabilityOfPrecipUnitsInput(nightProbabilityOfPrecipUnits: string): Promise<void> {
    await this.nightProbabilityOfPrecipUnitsInput.sendKeys(nightProbabilityOfPrecipUnits);
  }

  async getNightProbabilityOfPrecipUnitsInput(): Promise<string> {
    return await this.nightProbabilityOfPrecipUnitsInput.getAttribute('value');
  }

  async setIconInput(icon: string): Promise<void> {
    await this.iconInput.sendKeys(icon);
  }

  async getIconInput(): Promise<string> {
    return await this.iconInput.getAttribute('value');
  }

  async save(): Promise<void> {
    await this.saveButton.click();
  }

  async cancel(): Promise<void> {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class WeatherDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-weather-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-weather'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
