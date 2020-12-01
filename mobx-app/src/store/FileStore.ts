import { observable } from "mobx";
import { RequestAPI } from "../service/config";
import { IFile } from "../service/interface/FileInterface";
import { File } from "../service/modal/File";
import { urlConfig } from "../service/urlConfig";

export class FileStore {
    @observable files: File[] = [];

    uploadFile = async (file: any) => {
        const formData = new FormData(); 
     
      // Update the formData object 
      formData.append( "file", file); 
        const response = await RequestAPI.post(urlConfig.uploadFile(), formData);
        
      if(response){
        console.log(response)
        const fileUploaded = new File();
        fileUploaded.fromApi(response.data);
        this.files.push(fileUploaded);
      }
    }

    getFiles = async (name?: string, clear: boolean = false) => {
      const response = await RequestAPI.get(urlConfig.getFiles(name));
      console.log(response);
      if(clear){
        this.files.splice(0, this.files.length);
      }
      response.data.map((data: IFile) => {
        const file = new File();
        file.fromApi(data);
        this.files.push(file);
      })
    }
}

export const FileStoreInstance = new FileStore();