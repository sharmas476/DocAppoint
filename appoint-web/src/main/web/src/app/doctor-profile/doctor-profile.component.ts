import { FormBuilder, FormGroup, FormControl } from '@angular/forms';
import { MessageService } from 'primeng/components/common/messageservice';
import { Appointment } from './../model/Appointment';
import { AppointmentService } from './../services/appointment.service';
import { Component, OnInit } from '@angular/core';
import { RouterModule, Router } from '@angular/router';

@Component({
  selector: 'app-doctor-profile',
  templateUrl: './doctor-profile.component.html',
  styleUrls: ['./doctor-profile.component.css'],
  providers: [MessageService]
})
export class DoctorProfileComponent implements OnInit {

  constructor(private appointmentService:AppointmentService, 
      private fb: FormBuilder, 
      private messageService: MessageService,
      private router: Router) { }

  today:Date;
  appointments:Appointment[];
  minDate:Date;
  display:boolean = false;
  appointmentform:FormGroup;
  editingAppointmentId:string;
  editableTime:string;
  editableDate:Date;
  description:string;

  ngOnInit() {
    this.today=new Date();
    this.fetchAppointmentByDate(this.today);
    this.minDate = new Date();
  }

  fetchAppointmentByDate(date:Date){
    this.appointmentService.fetchAppointmentByDate(date).subscribe(response => {
      this.appointments = response;
      console.log(this.appointments);
    },
    error => {
      console.log(error);
    })
  }

  startTreatment(appointment:Appointment){
    this.appointmentService.startTreatment(appointment.appointmentId).subscribe(response => {
      if(response == true){
        this.router.navigate(['/doctorProfile/patienthistory/'+appointment.appointmentId]);
      }
    })
  }

  getNextDaysAppointment(){
    let date = new Date();
    date.setDate(this.today.getDate() + 1);
    this.today = date;
    this.appointmentService.fetchAppointmentByDate(date).subscribe(response => {
      this.appointments = response;
    },
    error => {
      console.log(error);
    })
  }

  getPreviousDaysAppointment(){
    let date = new Date();
    date.setDate(this.today.getDate() - 1);
    this.today = date;
    this.appointmentService.fetchAppointmentByDate(date).subscribe(response => {
      this.appointments = response;
    },
    error => {
      console.log(error);
    })
  }

  getAppointmentForTheDay(){
    this.appointmentService.fetchAppointmentByDate(this.today).subscribe(response => {
      this.appointments = response;
    },
    error => {
      console.log(error);
    })
  }
  
  showDialog() {
    this.display = true;
  }
  hideDialog() {
    this.display = false;
  }

  modifyAppointment(appointmentId:string){
    
    let appointment:Appointment;
    for (let i in this.appointments) {
      if (appointmentId == this.appointments[i].appointmentId) {
        appointment = this.appointments[i];
        this.editingAppointmentId = appointmentId;
      }
    }
    this.showDialog();
    this.editableTime = appointment.time;
    this.editableDate = new Date(appointment.date);    
  }
  onSubmit(){
    let appointment:Appointment = this.convertFormToAppointment();
    this.appointmentService.modifyAppointment(appointment).subscribe(response => {
      if (response == true){
        this.hideDialog();  
        this.messageService.add({ severity: 'info', summary: 'Success', detail: 'Patient Updated Successfully.' });
      }
    },
    error => {
      this.messageService.add({ severity: 'error', summary: 'Error', detail: error.message });
    })
  }
  convertFormToAppointment(): Appointment{
    let appointment = new Appointment();
    appointment.appointmentId = this.editingAppointmentId;
    appointment.date = this.editableDate;
    appointment.time = this.convertTimeToString(this.editableTime);
    return appointment;
  }

  convertTimeToString(time:string){
    console.log(time);
    let startIndex = String(time).indexOf(':')-2;
    let newDate = String(time).substr(startIndex, 5);
    return newDate;
  }
}
