export const BASE_URL = 'http://localhost:8080/files';
export const urlConfig = {
    uploadFile: () => {
        return BASE_URL + '/upload';
    },

    getFiles: (name?: string) => {
        if(name){
            return BASE_URL + "?name=" + name;
        }
        return BASE_URL;
    }
}