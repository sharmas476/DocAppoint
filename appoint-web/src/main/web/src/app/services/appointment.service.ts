import { environment } from './../../environments/environment';
import { Appointment } from '../model/Appointment';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { PatientDescription } from '../model/Patient-Description';

@Injectable({
  providedIn: 'root'
})
export class AppointmentService {

  constructor(private http: HttpClient) { }

  fetchAppointmentByDate(date:Date):Observable<Appointment[]> {
    return this.http.post<Appointment[]>(environment.url+"/api/test/appointmentSchedule", date);
  }

  startTreatment(appointmentId:string):Observable<Boolean> {
    return this.http.get<Boolean>(environment.url+"/api/test/startTreatment/"+appointmentId);
  }

  fetchPatientHistory(patientId:String):Observable<Appointment[]> {
    return this.http.get<Appointment[]>(environment.url+"/api/test/fetchPatientHistory/"+patientId);
  }

  fetchPatientAppointment():Observable<Appointment[]> {
    return this.http.get<Appointment[]>(environment.url+"/api/test/getAllAppointments");
  }

  modifyAppointment(appointment:Appointment):Observable<boolean> {
    return this.http.put<boolean>(environment.url+"/api/test/modifyAppointment", appointment);
  }

  deleteAppointment(appointmentId):Observable<boolean> {
    return this.http.delete<boolean>(environment.url+"/api/test/deletePatientAppointment/"+appointmentId);
  }

  createAppointment(appointment: Appointment){
    return this.http.post(environment.url+"/api/test/createNewAppointment", appointment);
  }

  getTimeSlots(date:string):Observable<string[]> {
    return this.http.get<string[]>(environment.url+"/api/test/getTimeIntervals/"+date);
  }

  getPatientDescription(id:string):Observable<PatientDescription> {
    return this.http.get<PatientDescription>(environment.url+"/api/test/getPatientDescription/"+id);
  }

  savePatientRecord(patient:PatientDescription):Observable<PatientDescription>{
    return this.http.post<PatientDescription>(environment.url+"/api/test/savePatientRecord",patient);
  }
}
