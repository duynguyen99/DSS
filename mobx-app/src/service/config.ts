import Axios from "axios";


export enum METHOD {
    POST = 'POST',
    PUT = 'PUT',
    DELETE = 'DELETE',
    GET = 'GET'
}
export class APIList {
    // TODO: refactor to use requestAPI function
    requestAPI = async (method: any, url: string, data?: any) => {
       const response = await Axios(url, data); 
        return response;
    }

    get = async (url: string) => {
        const response = await Axios.get(url);
        return response;
    }

    post = async(url: string, data: any) => {
        const response = await Axios.post(url, data);
        return response;
    }
}

export const RequestAPI = new APIList();