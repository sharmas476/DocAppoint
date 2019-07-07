import { ConfirmationService } from 'primeng/api';
import { TimeOff } from './../model/TimeOff';
import { MessageService } from 'primeng/components/common/messageservice';
import { FormBuilder } from '@angular/forms';
import { Component, OnInit } from '@angular/core';
import { TimeoffService } from '../services/timeoff.service';

@Component({
  selector: 'app-time-off',
  templateUrl: './time-off.component.html',
  styleUrls: ['./time-off.component.css'],
  providers: [MessageService, ConfirmationService]
})
export class TimeOffComponent implements OnInit {

  timeOffs: TimeOff[];
  display: boolean = false;
  editableEndTime: string;
  editableEndDate: Date;
  editableStartTime: string;
  editableStartDate: Date;
  minDate = new Date();
  editingTimeOffId: string;
  editableDescription: string;
  modifying: boolean = false;
  historyDate:Date;
  constructor(private timeOffService: TimeoffService, 
    private fb: FormBuilder, 
    private messageService: MessageService,
    private confirmationService:ConfirmationService) { }

  ngOnInit() {
    this.timeOffService.fetchCurrentTimeOff().subscribe(response => {
      this.timeOffs = response;
    },
      error => {
        this.messageService.add({ severity: 'error', summary: 'Error', detail: error.message });
      })
  }

  isTimeOffGreaterThanToday(timeOffDate: Date): boolean {
    let currentDate = new Date();
    timeOffDate = new Date(timeOffDate);
    if (timeOffDate >= currentDate)
      return true;
    return false;
  }

  modifyTimeOff(timeOffId: string) {
    let timeOff: TimeOff;
    for (let i in this.timeOffs) {
      if (timeOffId == this.timeOffs[i].timeOffId) {
        timeOff = this.timeOffs[i];
        this.editingTimeOffId = timeOffId;
      }
    }
    this.editableStartDate = new Date(timeOff.startDate);
    this.editableEndDate = new Date(timeOff.endDate);
    this.editableStartTime = timeOff.startTime;
    this.editableEndTime = timeOff.endTime;
    this.editableDescription = timeOff.description;
    this.startModifying();
    this.showDialog();
  }

  showDialog() {
    this.display = true;
  }
  hideDialog() {
    this.display = false;
  }
  startModifying() {
    this.modifying = true;
  }
  stopModifying() {
    this.modifying = false;
  }
  getDialogHeader() {
    if (this.modifying)
      return "Modify this time off";
    return "Create new Time off"
  }

  onSubmit() {
    let timeOff: TimeOff = this.convertFormToTimeOff();
    if(this.validate()){
    if (this.modifying) {
      this.timeOffService.modifyTimeOff(timeOff).subscribe(response => {
        if (response == true) {
          this.hideDialog();
          this.stopModifying();
          this.ngOnInit();
          this.messageService.add({ severity: 'info', summary: 'Success', detail: 'Time off updated Successfully.' });
        }
      },
        error => {
          this.hideDialog();
          this.stopModifying();
          this.messageService.add({ severity: 'error', summary: 'Error', detail: error.message });
        });
    }else{
      this.timeOffService.createTimeOff(timeOff).subscribe(response => {
        if (response == true) {
          this.hideDialog();
          this.ngOnInit();
          this.messageService.add({ severity: 'info', summary: 'Success', detail: 'Time off created Successfully.' });
        }
      },
        error => {
          this.hideDialog();
          this.messageService.add({ severity: 'error', summary: 'Error', detail: error.message });
        });
    }
  }
  }

  convertFormToTimeOff(): TimeOff {
    let timeOff = new TimeOff();
    timeOff.timeOffId = this.editingTimeOffId;
    timeOff.startDate = this.editableStartDate;
    timeOff.endDate = this.editableEndDate;
    timeOff.startTime = this.convertTimeToString(this.editableStartTime);
    timeOff.endTime = this.convertTimeToString(this.editableEndTime);
    timeOff.description = this.editableDescription;
    return timeOff;
  }

  convertTimeToString(time: string) {
    let startIndex = String(time).indexOf(':') - 2;
    let newDate = String(time).substr(startIndex, 5);
    return newDate;
  }

  getAllTimeOffs() {
    this.timeOffService.getAllTimeOffs().subscribe(response => {
      this.timeOffs = response;
    },
      error => {
        this.messageService.add({ severity: 'error', summary: 'Error', detail: error.message });
      });
  }

  deleteTimeOff(timeOffId:string){
    this.confirmationService.confirm({
      message: 'Are you sure that you want to delete this timeoff?',
      header: 'Confirmation',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.timeOffService.deleteTimeOff(timeOffId).subscribe(response => {
          if (response == true) {
            this.ngOnInit();
            this.messageService.add({ severity: 'info', summary: 'Success', detail: "Time off deleted successfully" });
          }
        },
          error => {
            this.messageService.add({ severity: 'error', summary: 'Error', detail: error.message });
          });
      },
      reject: () => {
          
      }
  });
  }

  createTimeOff() {
    this.editableStartDate = null;
    this.editableEndDate = null;
    this.editableStartTime = "";
    this.editableEndTime = "";
    this.editableDescription = "";
    this.editingTimeOffId = '0';
    this.stopModifying();
    this.showDialog();
  }

  getTimeoffByMonth(){
    console.log(this.historyDate.getFullYear()+"/"+(this.historyDate.getMonth()+1));
    this.timeOffService.getTimeOffByMonth((this.historyDate.getMonth()+1)+"",this.historyDate.getFullYear()+"").subscribe(
      response =>{
        this.timeOffs = response;
      },
      error => {
        this.messageService.add({ severity: 'error', summary: 'Error', detail: error.message });
      });
  }

  validate():boolean{
    if(this.editableStartDate > this.editableEndDate)
      this.messageService.add({ severity: 'error', summary: 'Error', detail: "Start date cannot be greater than end date." });
    else if(this.editableStartDate.getTime() == this.editableEndDate.getTime() && new Date(this.editableStartTime) > new Date(this.editableEndTime))
      this.messageService.add({ severity: 'error', summary: 'Error', detail: "Start time cannot be greater than end time." });
    else
      return true;
    return false;
  }

}
