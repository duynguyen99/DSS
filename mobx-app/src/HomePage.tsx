import React, { Component } from 'react';
import { observable } from 'mobx';
import FileHeader from './components/FileHeader';
import FileList from './components/FileList';
import './assert/Home.css';
interface HomePageProps {

}

interface HomePageState {

}
class HomePage extends Component<HomePageProps, HomePageState> {
    @observable name: string = 'hello';
    render() {
        return (
            <div className="center">
               <FileHeader />
               <FileList />
            </div>
        );
    }
}

export default HomePage;