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
    return this.http.get<TimeOff[]>(environment.url+"/api/test/getTimeoffs");
  }

  modifyTimeOff(timeOff: TimeOff): Observable<boolean>{
    return this.http.put<boolean>(environment.url+"/api/test/modifyTimeOffs",timeOff);
  }

  getAllTimeOffs(): Observable<TimeOff[]> {
    return this.http.get<TimeOff[]>(environment.url+"/api/test/getAllTimeoffs");
  }

  createTimeOff(timeOff:TimeOff): Observable<boolean>{
    return this.http.post<boolean>(environment.url+"/api/test/createTimeoffs",timeOff);
  }

  deleteTimeOff(timeOffId:string): Observable<boolean> {
    return this.http.post<boolean>(environment.url+"/api/test/deleteTimeoff", timeOffId);
  }

  getTimeOffByMonth(month:string, year:string): Observable<TimeOff[]> {
    return this.http.get<TimeOff[]>(environment.url+"/api/test/getTimeoffByMonth/"+month+"/"+year);
  }

  getFullDayTimeoff():Observable<string[]> {
    return this.http.get<string[]>(environment.url+"/api/test/getFullDayTimeoff");
  }
}
