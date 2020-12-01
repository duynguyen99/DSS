import { observer } from 'mobx-react';
import React, { Component } from 'react';
import { FileStore, FileStoreInstance } from '../store/FileStore';
import './FileHeader.css';

@observer
class FileHeader extends Component {
    onFileChange = (e: any) => {
        console.log(e.target.files[0])
        FileStoreInstance.uploadFile(e.target.files[0])
    }

    onSearch = (e: any) => {
        const code = (e.keyCode ? e.keyCode : e.which);
        if (code == 13) { //Enter keycode
            FileStoreInstance.getFiles(e.target.value, true);
        }
    }

    render() {
        return (
            <div className="header-wrapper">
                <div className="wrapper-content-header">
                    <input type="file" id="file" onChange={this.onFileChange} multiple={false} />
                    <label htmlFor="file" className="btn-2">upload</label>
                    <div className="search-wrapper">
                        <input type="text" className="textbox" placeholder="Search" onKeyUp={this.onSearch} />
                        <input title="Search" value="Search" type="submit" className="button" onClick={this.onSearch}/>
                    </div>
                </div>
            </div>
        );
    }
}

export default FileHeader;