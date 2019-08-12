import { Prescription } from './../model/Prescription';
import { MessageService } from 'primeng/components/common/messageservice';
import { AppointmentService } from './../services/appointment.service';
import { PatientDescription } from './../model/Patient-Description';
import { Patient } from './../model/Patient';
import { Component, OnInit } from '@angular/core';
import { AngularEditorConfig } from '@kolkov/angular-editor';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-patient-history',
  templateUrl: './patient-history.component.html',
  styleUrls: ['./patient-history.component.css'],
  providers: [MessageService]
})
export class PatientHistoryComponent implements OnInit {
  config: AngularEditorConfig = {
    editable: true,
    spellcheck: true,
    height: '65rem',
    minHeight: '5rem',
    placeholder: 'Enter text here...',
    translate: 'no',
    toolbarPosition: 'top',
    customClasses: [
      {
        name: "quote",
        class: "quote",
      },
      {
        name: 'redText',
        class: 'redText'
      },
      {
        name: "titleText",
        class: "titleText",
        tag: "h1",
      },
    ]
  };
  config2: AngularEditorConfig = {
    editable: true,
    spellcheck: true,
    height: '15rem',
    minHeight: '5rem',
    placeholder: 'Enter text here...',
    translate: 'no',
    toolbarPosition: 'top',
    customClasses: [
      {
        name: "quote",
        class: "quote",
      },
      {
        name: 'redText',
        class: 'redText'
      },
      {
        name: "titleText",
        class: "titleText",
        tag: "h1",
      },
    ]
  };
  patient:PatientDescription;
  mode='description';
  isNewPrescription=false;
  newPrescription:string;
  appointmentId:string;
  
  display(){
    console.log(this.init);
  }

  constructor(private route: ActivatedRoute, private appointmentService:AppointmentService,
    private messageService: MessageService) { }
 
  ngOnInit() {
    this.route.paramMap.subscribe(paramMap => {
      this.appointmentId = paramMap.get('id');
      this.getData(this.appointmentId);
    });
  }

  getData(id:string){
    this.appointmentService.getPatientDescription(id).subscribe(response => {
      this.patient = response;
      console.log(response);
      if(response.pC == '' || response.pC == undefined || response.pC == null){  
        this.patient.pC = this.init;
      }
      if(response.fileId == '' || response.fileId == undefined || response.fileId == null){
        this.patient.fileId = '0';
      }
      
    });
  }

  showHidePresciption(){
    if(this.mode == 'prescription')
      this.mode = 'description';
    else{
      this.mode = 'prescription';
      if(this.patient.prescriptionList.length == 0)
        this.isNewPrescription = true;
    }
  }

  saveNewPrescription(){
    if(this.newPrescription.trim().length > 5){
    let prescription = new Prescription();
    prescription.prescriptionDetails = this.newPrescription;
    prescription.date = new Date();
    prescription.patientId = this.patient.patientId;
    if(this.patient.prescriptionList.length >= 1){
      this.patient.prescriptionList.push(prescription);
    }else{
      this.patient.prescriptionList = [prescription];
    }
  }
  }

  savePatientRecord(){
    this.saveNewPrescription();
    this.appointmentService.savePatientRecord(this.patient).subscribe(response => {
      this.patient = response;
      this.newPrescription = '';
      this.messageService.add({ severity: 'info', summary: 'Success', detail: 'Patient Record Updated Successfully.' });
      
    });
  }

  init = `<p class="MsoNormal"><br></p>
  <table class="MsoNormalTable" border="1" cellspacing="0" cellpadding="0" width="885">
    <tbody>&#10; 
      <tr>&#10; 
        <td width="885" colspan="4" valign="top">&#10; 
          <p class="MsoNormal"><b><span lang="EN-US">P/C:</span></b></p>&#10; 
          <p class="MsoNormal"><br></p>&#10; <p class="MsoNormal"><b><span lang="EN-US">&#160;</span></b></p>&#10; 
          <p class="MsoNormal"><b><span lang="EN-US">Other&#10; complaints: </span></b></p>&#10; 
        </td>&#10; 
      </tr>&#10; 
      <tr>&#10; 
        <td width="443" valign="top">&#10; 
          <p class="MsoNormal"><b><span lang="EN-US">History:&#10; </span></b></p>&#10; 
          <p class="MsoNormal"><br></p>&#10; 
        </td>&#10; 
        <td width="442" colspan="3" valign="top">&#10; 
          <p class="MsoNormal"><b><span lang="EN-US">History:</span></b></p>&#10; 
        </td>&#10; 
      </tr>&#10; 
      <tr>&#10; 
        <td width="443" valign="top">&#10; 
          <p class="MsoNormal"><b><span lang="EN-US">Menstrual&#10; History:</span></b></p>&#10; 
          <p class="MsoNormal"><b><span lang="EN-US">&#160;</span></b></p>&#10; 
        </td>&#10; 
        <td width="442" colspan="3" valign="top">&#10; 
          <p class="MsoNormal"><b><span lang="EN-US">Obstetric&#10; History: </span></b></p>&#10; 
          <p class="MsoNormal"><b><span lang="EN-US">&#160;</span></b></p>&#10; 
        </td>&#10; 
      </tr>&#10; 
      <tr>&#10; 
        <td width="885" colspan="4" valign="top">&#10; 
          <p class="MsoNormal"><b><span lang="EN-US">F/H:</span></b></p>&#10; 
          <p class="MsoNormal"><b><span lang="EN-US">&#160;</span></b></p>&#10; 
        </td>&#10; 
      </tr>&#10; 
      <tr>&#10; 
        <td width="443" rowspan="2" valign="top">&#10; 
          <p class="MsoNormal"><b><span lang="EN-US">Gen:</span></b></p>&#10; 
          <p class="MsoNormal"><span lang="EN-US">Thirst-&#160;</span></p>&#10; 
          <p class="MsoNormal"><span lang="EN-US">Appetite-&#160;</span></p>&#10; 
          <p class="MsoNormal"><span lang="EN-US">Desires(food)-&#160;</span></p>&#10; 
          <p class="MsoNormal"><span lang="EN-US">Aversion (food)-&#160;</span></p>&#10; 
          <p class="MsoNormal"><span lang="EN-US">Sleep-&#160;</span></p>
          <p class="MsoNormal"><span>Dreams-&#160;</span></p>&#10; 
          <p class="MsoNormal"><span lang="EN-US">Perspiration-&#160;</span></p>&#10; 
          <p class="MsoNormal"><span lang="EN-US">Bowels/Motion-&#160;</span></p>&#10; 
          <p class="MsoNormal"><span lang="EN-US">Urine-&#160;</span></p>&#10; 
          <p class="MsoNormal"><span lang="EN-US">TR-&#160;</span></p>&#10; 
          <p class="MsoNormal"><span lang="EN-US">Activity-&#160;</span></p>&#10; 
          <p class="MsoNormal"><span lang="EN-US">Never well&#10; since-&#160;</span></p>&#10; 
          <p class="MsoNormal"><span lang="EN-US">Weather-&#160;</span></p>&#10; 
          <p class="MsoNormal"><span lang="EN-US">Agg-&#160;</span></p>&#10; 
          <p class="MsoNormal"><span lang="EN-US">Amel-&#160;</span></p>&#10; 
        </td>&#10; 
        <td width="442" colspan="3" valign="top">&#10; 
          <p class="MsoNormal"><b><span lang="EN-US">Physical Exam:</span></b></p>&#10; 
          <p class="MsoNormal"><span lang="EN-US">BP:&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160; Pulse:&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160; Weight:</span></p>&#10; 
          <p class="MsoNormal"><span lang="EN-US">Hair:</span></p>&#10; 
          <p class="MsoNormal"><span lang="EN-US">Nails:</span></p>&#10; 
          <p class="MsoNormal"><span lang="EN-US">Skin:</span></p>&#10; 
          <p class="MsoNormal"><span lang="EN-US">&#160;</span></p>&#10; 
          <p class="MsoNormal"><span lang="EN-US">Others:</span></p>&#10; 
          <p class="MsoNormal"><span lang="EN-US">&#160;</span></p>&#10; 
        </td>&#10; 
      </tr>&#10; 
      <tr>&#10; 
        <td width="442" colspan="3" valign="top">&#10; 
          <p class="MsoNormal"><b><span lang="EN-US">Observations:</span></b></p>&#10; 
        </td>&#10; 
      </tr>&#10; 
      <tr>&#10; 
        <td width="443" valign="top">&#10; 
          <p class="MsoNormal"><b><span lang="EN-US">Emotional History/Personality:</span></b></p>&#10; 
          <p class="MsoNormal"><br></p>&#10; 
        </td>&#10; 
      <td width="442" colspan="3" valign="top">&#10; 
        <p class="MsoNormal"><b><span lang="EN-US">Emotional&#10; History/Personality:</span></b></p>&#10; 
        <p class="MsoNormal"><b><span lang="EN-US">&#160;</span></b></p>
      </td>
    </tr>
  </tbody>
</table>`;

}
