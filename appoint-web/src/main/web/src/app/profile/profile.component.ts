import { Patient } from './../model/Patient';
import { ProfileService } from './../services/profile.service';
import { Component, OnInit } from '@angular/core';
import { TokenStorageService } from '../auth/token-storage.service';
import { FormGroup, FormBuilder, FormControl, Validators } from '@angular/forms';
import { MessageService } from 'primeng/components/common/messageservice';
import { SelectItem } from '../common/select-item';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css'],
  providers: [MessageService]
})
export class ProfileComponent implements OnInit {

  constructor(private profileService: ProfileService, private tokenStorage: TokenStorageService, private fb: FormBuilder, private messageService: MessageService) { }

  display = false;
  errorMessage;
  index = 0;
  patientList: Patient;
  genders: SelectItem[];
  userform: FormGroup;
  submitted: boolean;
  description: string;
  isEditing: boolean = false;
  editingPatientId: string;

  ngOnInit() {
    this.profileService.fetchAllPatients(this.tokenStorage.getUsername()).subscribe(response => {
      this.patientList = response;
      console.log(response);
    },
      error => {
        this.errorMessage = `${error.status}: ${JSON.parse(error.error).message}`;
      });

    this.userform = this.fb.group({
      'name': new FormControl('', Validators.compose([Validators.required, Validators.minLength(3), Validators.maxLength(50)])),
      'age': new FormControl('', Validators.compose([Validators.required, Validators.maxLength(2), Validators.minLength(1)])),
      'address': new FormControl(''),
      'email': new FormControl('', Validators.email),
      'gender': new FormControl('', Validators.required)
    });
  }

  showDialog() {
    this.display = true;
  }
  onSubmit(value: string) {
    this.submitted = true;
    this.display = false;

    if (this.isEditing) {
      let patient: Patient = this.convertFormToPatient();
      patient.patientId = this.editingPatientId;
      this.profileService.saveNewPatient(patient).subscribe(response => {
        if (response == true)
          this.messageService.add({ severity: 'info', summary: 'Success', detail: 'Patient Updated Successfully.' });
      },
        error => {
          this.messageService.add({ severity: 'error', summary: 'Error', detail: error.message });
        });
      this.isEditing = false;
    } else {
      let patient: Patient = this.convertFormToPatient();
      this.profileService.saveNewPatient(patient).subscribe(response => {
        if (response == true)
          this.messageService.add({ severity: 'info', summary: 'Success', detail: 'Patient Added Successfully.' });
      },
        error => {
          this.messageService.add({ severity: 'error', summary: 'Error', detail: error.message });
        })
    }
    this.userform.reset();
  }

  deleteUser(patientId: string) {
    this.profileService.deletePatient(patientId).subscribe(response => {
      if (response == true)
        this.messageService.add({ severity: 'info', summary: 'Success', detail: 'Patient Deleted Successfully.' });
      for (let i in this.patientList) {
        if (patientId == this.patientList[i].patientId) {
          delete this.patientList[i];
        }
      }
    },
      error => {
        this.messageService.add({ severity: 'error', summary: 'Error', detail: error.message });
      })
  }

  startEditing(patientId: string) {
    let patient: Patient;
    for (let i in this.patientList) {
      if (patientId == this.patientList[i].patientId) {
        patient = this.patientList[i];
        this.editingPatientId = patient.patientId;
      }
    }
    this.isEditing = true;
    this.userform.controls['name'].setValue(patient.name);
    this.userform.controls['age'].setValue(patient.age);
    this.userform.controls['email'].setValue(patient.email);
    this.userform.controls['address'].setValue(patient.address);
    this.userform.controls['gender'].setValue(patient.gender);
    this.showDialog();
  }

  convertFormToPatient(): Patient {
    let patient = new Patient();
    patient.name = this.userform.value.name;
    patient.gender = this.userform.value.gender;
    patient.email = this.userform.value.email;
    patient.age = this.userform.value.age;
    patient.address = this.userform.value.address;
    return patient;
  }

}
