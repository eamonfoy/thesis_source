import { element, by, ElementFinder } from 'protractor';

export class DepartureFlightComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-departure-flight div table .btn-danger'));
  title = element.all(by.css('jhi-departure-flight div h2#page-heading span')).first();
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

export class DepartureFlightUpdatePage {
  pageTitle = element(by.id('jhi-departure-flight-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  actualInput = element(by.id('field_actual'));
  estimatedInput = element(by.id('field_estimated'));
  scheduledInput = element(by.id('field_scheduled'));
  cityInput = element(by.id('field_city'));
  aircraftInput = element(by.id('field_aircraft'));
  terminalInput = element(by.id('field_terminal'));
  durationInput = element(by.id('field_duration'));
  tailNumberInput = element(by.id('field_tailNumber'));
  airportCodeInput = element(by.id('field_airportCode'));
  airlineInput = element(by.id('field_airline'));
  flightNumberInput = element(by.id('field_flightNumber'));
  gateInput = element(by.id('field_gate'));
  statusInput = element(by.id('field_status'));
  statusTextInput = element(by.id('field_statusText'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setActualInput(actual: string): Promise<void> {
    await this.actualInput.sendKeys(actual);
  }

  async getActualInput(): Promise<string> {
    return await this.actualInput.getAttribute('value');
  }

  async setEstimatedInput(estimated: string): Promise<void> {
    await this.estimatedInput.sendKeys(estimated);
  }

  async getEstimatedInput(): Promise<string> {
    return await this.estimatedInput.getAttribute('value');
  }

  async setScheduledInput(scheduled: string): Promise<void> {
    await this.scheduledInput.sendKeys(scheduled);
  }

  async getScheduledInput(): Promise<string> {
    return await this.scheduledInput.getAttribute('value');
  }

  async setCityInput(city: string): Promise<void> {
    await this.cityInput.sendKeys(city);
  }

  async getCityInput(): Promise<string> {
    return await this.cityInput.getAttribute('value');
  }

  async setAircraftInput(aircraft: string): Promise<void> {
    await this.aircraftInput.sendKeys(aircraft);
  }

  async getAircraftInput(): Promise<string> {
    return await this.aircraftInput.getAttribute('value');
  }

  async setTerminalInput(terminal: string): Promise<void> {
    await this.terminalInput.sendKeys(terminal);
  }

  async getTerminalInput(): Promise<string> {
    return await this.terminalInput.getAttribute('value');
  }

  async setDurationInput(duration: string): Promise<void> {
    await this.durationInput.sendKeys(duration);
  }

  async getDurationInput(): Promise<string> {
    return await this.durationInput.getAttribute('value');
  }

  async setTailNumberInput(tailNumber: string): Promise<void> {
    await this.tailNumberInput.sendKeys(tailNumber);
  }

  async getTailNumberInput(): Promise<string> {
    return await this.tailNumberInput.getAttribute('value');
  }

  async setAirportCodeInput(airportCode: string): Promise<void> {
    await this.airportCodeInput.sendKeys(airportCode);
  }

  async getAirportCodeInput(): Promise<string> {
    return await this.airportCodeInput.getAttribute('value');
  }

  async setAirlineInput(airline: string): Promise<void> {
    await this.airlineInput.sendKeys(airline);
  }

  async getAirlineInput(): Promise<string> {
    return await this.airlineInput.getAttribute('value');
  }

  async setFlightNumberInput(flightNumber: string): Promise<void> {
    await this.flightNumberInput.sendKeys(flightNumber);
  }

  async getFlightNumberInput(): Promise<string> {
    return await this.flightNumberInput.getAttribute('value');
  }

  async setGateInput(gate: string): Promise<void> {
    await this.gateInput.sendKeys(gate);
  }

  async getGateInput(): Promise<string> {
    return await this.gateInput.getAttribute('value');
  }

  async setStatusInput(status: string): Promise<void> {
    await this.statusInput.sendKeys(status);
  }

  async getStatusInput(): Promise<string> {
    return await this.statusInput.getAttribute('value');
  }

  async setStatusTextInput(statusText: string): Promise<void> {
    await this.statusTextInput.sendKeys(statusText);
  }

  async getStatusTextInput(): Promise<string> {
    return await this.statusTextInput.getAttribute('value');
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

export class DepartureFlightDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-departureFlight-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-departureFlight'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
