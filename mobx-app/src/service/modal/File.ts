import { observable } from "mobx";
import { IFile } from "../interface/FileInterface";

export class File {
    @observable name: string = '';
    @observable data: string = '';

    fromApi = (file: IFile) => {
        this.name = file.name;
        this.data = file.data;
    }
}