import { Project } from './project';

export interface Lecturer {
    name: String;
    email: String;
    cabinetNumber: String;
    projects: Array<Project>;
}

