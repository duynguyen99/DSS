import { observer } from 'mobx-react';
import React, { Component } from 'react';
import { File } from '../service/modal/File';
import { FileStoreInstance } from '../store/FileStore';
import './FileList.css';
interface IFileListProps {

}

interface IFileListState {

}

@observer
class FileList extends Component<IFileListProps, IFileListState> {
    componentDidMount() {
        FileStoreInstance.getFiles();
    }

    render() {
        const files = FileStoreInstance.files;
        return (
            <div className="wrapper">
                {
                    files.map((file: File) => {
                        return <div className="image-wrapper">
                            <img src={`data:image/png;base64, ${file.data}`} />
                            <p>{file.name}</p>
                        </div>
                    })
                }
            </div>
        );
    }
}

export default FileList;