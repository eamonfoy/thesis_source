import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../../page-objects/jhi-page-objects';

import { DepartureFlightComponentsPage, DepartureFlightDeleteDialog, DepartureFlightUpdatePage } from './departure-flight.page-object';

const expect = chai.expect;

describe('DepartureFlight e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let departureFlightComponentsPage: DepartureFlightComponentsPage;
  let departureFlightUpdatePage: DepartureFlightUpdatePage;
  let departureFlightDeleteDialog: DepartureFlightDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load DepartureFlights', async () => {
    await navBarPage.goToEntity('departure-flight');
    departureFlightComponentsPage = new DepartureFlightComponentsPage();
    await browser.wait(ec.visibilityOf(departureFlightComponentsPage.title), 5000);
    expect(await departureFlightComponentsPage.getTitle()).to.eq('aodbApp.flightDepartureFlight.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(departureFlightComponentsPage.entities), ec.visibilityOf(departureFlightComponentsPage.noResult)),
      1000
    );
  });

  it('should load create DepartureFlight page', async () => {
    await departureFlightComponentsPage.clickOnCreateButton();
    departureFlightUpdatePage = new DepartureFlightUpdatePage();
    expect(await departureFlightUpdatePage.getPageTitle()).to.eq('aodbApp.flightDepartureFlight.home.createOrEditLabel');
    await departureFlightUpdatePage.cancel();
  });

  it('should create and save DepartureFlights', async () => {
    const nbButtonsBeforeCreate = await departureFlightComponentsPage.countDeleteButtons();

    await departureFlightComponentsPage.clickOnCreateButton();

    await promise.all([
      departureFlightUpdatePage.setActualInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      departureFlightUpdatePage.setEstimatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      departureFlightUpdatePage.setScheduledInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      departureFlightUpdatePage.setCityInput('city'),
      departureFlightUpdatePage.setAircraftInput('aircraft'),
      departureFlightUpdatePage.setTerminalInput('terminal'),
      departureFlightUpdatePage.setDurationInput('duration'),
      departureFlightUpdatePage.setTailNumberInput('tailNumber'),
      departureFlightUpdatePage.setAirportCodeInput('airportCode'),
      departureFlightUpdatePage.setAirlineInput('airline'),
      departureFlightUpdatePage.setFlightNumberInput('flightNumber'),
      departureFlightUpdatePage.setGateInput('gate'),
      departureFlightUpdatePage.setStatusInput('status'),
      departureFlightUpdatePage.setStatusTextInput('statusText')
    ]);

    expect(await departureFlightUpdatePage.getActualInput()).to.contain(
      '2001-01-01T02:30',
      'Expected actual value to be equals to 2000-12-31'
    );
    expect(await departureFlightUpdatePage.getEstimatedInput()).to.contain(
      '2001-01-01T02:30',
      'Expected estimated value to be equals to 2000-12-31'
    );
    expect(await departureFlightUpdatePage.getScheduledInput()).to.contain(
      '2001-01-01T02:30',
      'Expected scheduled value to be equals to 2000-12-31'
    );
    expect(await departureFlightUpdatePage.getCityInput()).to.eq('city', 'Expected City value to be equals to city');
    expect(await departureFlightUpdatePage.getAircraftInput()).to.eq('aircraft', 'Expected Aircraft value to be equals to aircraft');
    expect(await departureFlightUpdatePage.getTerminalInput()).to.eq('terminal', 'Expected Terminal value to be equals to terminal');
    expect(await departureFlightUpdatePage.getDurationInput()).to.eq('duration', 'Expected Duration value to be equals to duration');
    expect(await departureFlightUpdatePage.getTailNumberInput()).to.eq(
      'tailNumber',
      'Expected TailNumber value to be equals to tailNumber'
    );
    expect(await departureFlightUpdatePage.getAirportCodeInput()).to.eq(
      'airportCode',
      'Expected AirportCode value to be equals to airportCode'
    );
    expect(await departureFlightUpdatePage.getAirlineInput()).to.eq('airline', 'Expected Airline value to be equals to airline');
    expect(await departureFlightUpdatePage.getFlightNumberInput()).to.eq(
      'flightNumber',
      'Expected FlightNumber value to be equals to flightNumber'
    );
    expect(await departureFlightUpdatePage.getGateInput()).to.eq('gate', 'Expected Gate value to be equals to gate');
    expect(await departureFlightUpdatePage.getStatusInput()).to.eq('status', 'Expected Status value to be equals to status');
    expect(await departureFlightUpdatePage.getStatusTextInput()).to.eq(
      'statusText',
      'Expected StatusText value to be equals to statusText'
    );

    await departureFlightUpdatePage.save();
    expect(await departureFlightUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await departureFlightComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last DepartureFlight', async () => {
    const nbButtonsBeforeDelete = await departureFlightComponentsPage.countDeleteButtons();
    await departureFlightComponentsPage.clickOnLastDeleteButton();

    departureFlightDeleteDialog = new DepartureFlightDeleteDialog();
    expect(await departureFlightDeleteDialog.getDialogTitle()).to.eq('aodbApp.flightDepartureFlight.delete.question');
    await departureFlightDeleteDialog.clickOnConfirmButton();

    expect(await departureFlightComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
