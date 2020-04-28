import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../../page-objects/jhi-page-objects';

import { ArrivalFlightComponentsPage, ArrivalFlightDeleteDialog, ArrivalFlightUpdatePage } from './arrival-flight.page-object';

const expect = chai.expect;

describe('ArrivalFlight e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let arrivalFlightComponentsPage: ArrivalFlightComponentsPage;
  let arrivalFlightUpdatePage: ArrivalFlightUpdatePage;
  let arrivalFlightDeleteDialog: ArrivalFlightDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load ArrivalFlights', async () => {
    await navBarPage.goToEntity('arrival-flight');
    arrivalFlightComponentsPage = new ArrivalFlightComponentsPage();
    await browser.wait(ec.visibilityOf(arrivalFlightComponentsPage.title), 5000);
    expect(await arrivalFlightComponentsPage.getTitle()).to.eq('aodbApp.flightArrivalFlight.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(arrivalFlightComponentsPage.entities), ec.visibilityOf(arrivalFlightComponentsPage.noResult)),
      1000
    );
  });

  it('should load create ArrivalFlight page', async () => {
    await arrivalFlightComponentsPage.clickOnCreateButton();
    arrivalFlightUpdatePage = new ArrivalFlightUpdatePage();
    expect(await arrivalFlightUpdatePage.getPageTitle()).to.eq('aodbApp.flightArrivalFlight.home.createOrEditLabel');
    await arrivalFlightUpdatePage.cancel();
  });

  it('should create and save ArrivalFlights', async () => {
    const nbButtonsBeforeCreate = await arrivalFlightComponentsPage.countDeleteButtons();

    await arrivalFlightComponentsPage.clickOnCreateButton();

    await promise.all([
      arrivalFlightUpdatePage.setActualInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      arrivalFlightUpdatePage.setEstimatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      arrivalFlightUpdatePage.setScheduledInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      arrivalFlightUpdatePage.setCityInput('city'),
      arrivalFlightUpdatePage.setAircraftInput('aircraft'),
      arrivalFlightUpdatePage.setTerminalInput('terminal'),
      arrivalFlightUpdatePage.setDurationInput('duration'),
      arrivalFlightUpdatePage.setTailNumberInput('tailNumber'),
      arrivalFlightUpdatePage.setAirportCodeInput('airportCode'),
      arrivalFlightUpdatePage.setAirlineInput('airline'),
      arrivalFlightUpdatePage.setFlightNumberInput('flightNumber'),
      arrivalFlightUpdatePage.setClaimInput('claim'),
      arrivalFlightUpdatePage.setStatusInput('status'),
      arrivalFlightUpdatePage.setStatusTextInput('statusText')
    ]);

    expect(await arrivalFlightUpdatePage.getActualInput()).to.contain(
      '2001-01-01T02:30',
      'Expected actual value to be equals to 2000-12-31'
    );
    expect(await arrivalFlightUpdatePage.getEstimatedInput()).to.contain(
      '2001-01-01T02:30',
      'Expected estimated value to be equals to 2000-12-31'
    );
    expect(await arrivalFlightUpdatePage.getScheduledInput()).to.contain(
      '2001-01-01T02:30',
      'Expected scheduled value to be equals to 2000-12-31'
    );
    expect(await arrivalFlightUpdatePage.getCityInput()).to.eq('city', 'Expected City value to be equals to city');
    expect(await arrivalFlightUpdatePage.getAircraftInput()).to.eq('aircraft', 'Expected Aircraft value to be equals to aircraft');
    expect(await arrivalFlightUpdatePage.getTerminalInput()).to.eq('terminal', 'Expected Terminal value to be equals to terminal');
    expect(await arrivalFlightUpdatePage.getDurationInput()).to.eq('duration', 'Expected Duration value to be equals to duration');
    expect(await arrivalFlightUpdatePage.getTailNumberInput()).to.eq('tailNumber', 'Expected TailNumber value to be equals to tailNumber');
    expect(await arrivalFlightUpdatePage.getAirportCodeInput()).to.eq(
      'airportCode',
      'Expected AirportCode value to be equals to airportCode'
    );
    expect(await arrivalFlightUpdatePage.getAirlineInput()).to.eq('airline', 'Expected Airline value to be equals to airline');
    expect(await arrivalFlightUpdatePage.getFlightNumberInput()).to.eq(
      'flightNumber',
      'Expected FlightNumber value to be equals to flightNumber'
    );
    expect(await arrivalFlightUpdatePage.getClaimInput()).to.eq('claim', 'Expected Claim value to be equals to claim');
    expect(await arrivalFlightUpdatePage.getStatusInput()).to.eq('status', 'Expected Status value to be equals to status');
    expect(await arrivalFlightUpdatePage.getStatusTextInput()).to.eq('statusText', 'Expected StatusText value to be equals to statusText');

    await arrivalFlightUpdatePage.save();
    expect(await arrivalFlightUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await arrivalFlightComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last ArrivalFlight', async () => {
    const nbButtonsBeforeDelete = await arrivalFlightComponentsPage.countDeleteButtons();
    await arrivalFlightComponentsPage.clickOnLastDeleteButton();

    arrivalFlightDeleteDialog = new ArrivalFlightDeleteDialog();
    expect(await arrivalFlightDeleteDialog.getDialogTitle()).to.eq('aodbApp.flightArrivalFlight.delete.question');
    await arrivalFlightDeleteDialog.clickOnConfirmButton();

    expect(await arrivalFlightComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
