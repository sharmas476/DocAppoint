import { Patient } from './../model/Patient';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ProfileService {

  constructor(private http: HttpClient) { }

  fetchAllPatients(phone:string): Observable<Patient>{
    return this.http.post<Patient>(environment.url+environment.port+"/api/test/fetchUser", phone);
  }

  saveNewPatient(patient:Patient): Observable<boolean>{
    return this.http.post<boolean>(environment.url+environment.port+"/api/test/saveUser", patient);
  }

  deletePatient(patientId:string): Observable<boolean>{
    return this.http.delete<boolean>(environment.url+environment.port+"/api/test/deleteUser/"+patientId);
  }

  editPatient(patient:Patient): Observable<boolean>{
    return this.http.put<boolean>(environment.url+environment.port+"/api/test/updateUser/", patient);
  }
}
