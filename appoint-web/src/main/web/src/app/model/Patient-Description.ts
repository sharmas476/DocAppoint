import { Prescription } from './Prescription';

export class PatientDescription {
    patientId: string;
    fileId:string;
    name: string;
    age: number;
    gender: string;
    marriedSince: string;
    contactDetails: string;
    occupation: string;
    pC: string;
    prescriptionList:Prescription[];
}