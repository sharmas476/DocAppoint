import { TimeoffService } from './../services/timeoff.service';
import { TokenStorageService } from './../auth/token-storage.service';
import { ProfileService } from './../services/profile.service';
import { Patient } from './../model/Patient';
import { ConfirmationService } from 'primeng/api';
import { MessageService } from 'primeng/components/common/messageservice';
import { Appointment } from './../model/Appointment';
import { AppointmentService } from './../services/appointment.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-patient-appointment',
  templateUrl: './patient-appointment.component.html',
  styleUrls: ['./patient-appointment.component.css'],
  providers: [MessageService, ConfirmationService]
})
export class PatientAppointmentComponent implements OnInit {

  constructor(private appointmentService: AppointmentService,
    private confirmationService: ConfirmationService,
    private profileService: ProfileService,
    private timeoffService: TimeoffService,
    private tokenStorage: TokenStorageService,
    private messageService: MessageService) { }
  
    display=false;
    selectedPatient:Patient;
    appointments: Appointment[];
    patients: Patient[];
    selectedDate: Date;
    selectedTime: string;
    appointment: Appointment;
    description:string;
    timeSlots:string[];
    minDate = new Date();
    maxDate:Date = new Date();
    today = new Date();
    invalidDates:Array<Date> = [];
    


  ngOnInit() {
    this.maxDate.setDate(this.today.getDate() + 14);
    this.appointmentService.fetchPatientAppointment().subscribe(response => {
      this.appointments = response;
    },
    error => {
        alert(error);
    });
    this.timeoffService.getFullDayTimeoff().subscribe(response => {
       response.forEach(element => {
         this.invalidDates.push(new Date(element))
       });
       if(response != undefined && response.length > 0)
          this.messageService.add({ severity: 'info', summary: 'Info', detail: "Doctor is on time-off on "+response });
    },
    error => {
      alert(error);
    });
    this.profileService.fetchAllPatients(this.tokenStorage.getUsername()).subscribe(response => {
      this.patients = response;
      this.selectedPatient = this.patients[0];
      this.selectedDate = new Date();
    },
    error => {
      this.messageService.add({ severity: 'error', summary: 'Error', detail: error.message });
    });
  }

  deleteAppointment(appointmentId) {
    this.confirmationService.confirm({
      message: 'Are you sure that you want to cancel this appointment?',
      header: 'Confirmation',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.appointmentService.deleteAppointment(appointmentId).subscribe(response => {
          if (response == true)
            this.messageService.add({ severity: 'info', summary: 'Success', detail: 'Appointment Cancelled Successfully.' });
          this.ngOnInit();
        },
          error => {
            this.messageService.add({ severity: 'error', summary: 'Error', detail: error.message });
          });
      },
      reject: () => {

      }
    });
  }

  isDateInvalid(date){
    let datestr = new Date(date.year+"-"+date.month+"-"+date.day);
    this.invalidDates.forEach(element => {
      if(datestr==element)
        return false;
    });
    return true;
  }

  showDialog() {
    this.display = true;
  }
  hideDialog() {
    this.display = false;
  }

  onSubmit(){
    this.appointment = new Appointment();
    this.appointment.date = this.selectedDate;
    this.appointment.time = this.selectedTime;
    this.appointment.description = this.description;
    this.appointment.patientId = this.selectedPatient.patientId;
    this.appointment.status = "Scheduled";
    this.appointmentService.createAppointment(this.appointment).subscribe(response => {
      this.hideDialog();
      this.ngOnInit();
      this.messageService.add({ severity: 'info', summary: 'Success', detail: "Appointment booked successfully." });
    },
    error => {
      this.messageService.add({ severity: 'info', summary: 'Success', detail: error });
    });

  }
  getTimeSlots(){
    console.log(this.selectedDate);
    this.appointmentService.getTimeSlots(getFormattedDate(this.selectedDate)).subscribe(response => {
      this.timeSlots = response;
    });
  }

  convertTimeToString(time:string){
    let startIndex = String(time).indexOf(':')-2;
    let newDate = String(time).substr(startIndex, 5);
    return newDate;
  }

  isDateBeforeToday(date:Date){
    if(new Date(date) <= this.today)
      return true;
    return false;
  }

}
function getFormattedDate(todayTime) {
  var month = todayTime.getMonth()+1;
  if(month<10)
    month="0"+month;
  var day = todayTime.getDate();
  if(day<10)
    day="0"+day;
  var year = todayTime.getFullYear();
  return year + "-" + month + "-" + day;
}
