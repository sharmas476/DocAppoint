import { environment } from './../../environments/environment';
import { TimeOff } from './../model/TimeOff';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class TimeoffService {

  constructor(private http: HttpClient) { }

  fetchCurrentTimeOff(): Observable<TimeOff[]>{
    return this.http.get<TimeOff[]>(environment.url+environment.port+"/api/test/getTimeoffs");
  }

  modifyTimeOff(timeOff: TimeOff): Observable<boolean>{
    return this.http.put<boolean>(environment.url+environment.port+"/api/test/modifyTimeOffs",timeOff);
  }

  getAllTimeOffs(): Observable<TimeOff[]> {
    return this.http.get<TimeOff[]>(environment.url+environment.port+"/api/test/getAllTimeoffs");
  }

  createTimeOff(timeOff:TimeOff): Observable<boolean>{
    return this.http.post<boolean>(environment.url+environment.port+"/api/test/createTimeoffs",timeOff);
  }

  deleteTimeOff(timeOffId:string): Observable<boolean> {
    return this.http.post<boolean>(environment.url+environment.port+"/api/test/deleteTimeoff", timeOffId);
  }

  getTimeOffByMonth(month:string, year:string): Observable<TimeOff[]> {
    return this.http.get<TimeOff[]>(environment.url+environment.port+"/api/test/getTimeoffByMonth/"+month+"/"+year);
  }
}
