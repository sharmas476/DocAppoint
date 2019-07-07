import { environment } from './../../environments/environment';
import { Appointment } from '../model/Appointment';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AppointmentService {

  constructor(private http: HttpClient) { }

  fetchAppointmentByDate(date:Date):Observable<Appointment[]> {
    return this.http.post<Appointment[]>(environment.url+environment.port+"/api/test/appointmentSchedule", date);
  }

  fetchPatientAppointment():Observable<Appointment[]> {
    return this.http.get<Appointment[]>(environment.url+environment.port+"/api/test/getAllAppointments");
  }

  modifyAppointment(appointment:Appointment):Observable<boolean> {
    return this.http.put<boolean>(environment.url+environment.port+"/api/test/modifyAppointment", appointment);
  }

  deleteAppointment(appointmentId):Observable<boolean> {
    return this.http.delete<boolean>(environment.url+environment.port+"/api/test/deletePatientAppointment/"+appointmentId);
  }

  createAppointment(appointment: Appointment){
    return this.http.post(environment.url+environment.port+"/api/test/createNewAppointment", appointment);
  }

  getTimeSlots(date:string):Observable<string[]> {
    return this.http.get<string[]>(environment.url+environment.port+"/api/test/getTimeIntervals/"+date);
  }

}
