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

  modifyAppointment(appointment:Appointment):Observable<boolean> {
    return this.http.put<boolean>(environment.url+environment.port+"/api/test/modifyAppointment", appointment);
  }
}
