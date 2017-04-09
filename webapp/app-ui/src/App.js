import React, {Component} from 'react';


import 'whatwg-fetch';
import './App.css';
import AppNavbar from './navigation/AppNavbar';
import BodyContainer from './layout/BodyContainer';
import PageFooter from './layout/PageFooter';
import {BrowserRouter as Router, Route, Link} from 'react-router-dom'

class App extends Component {
    render() {
        return (
            <div className="App">
                <AppNavbar/>
                <BodyContainer/>
                <PageFooter/>
            </div>
        );
    }
}

export default App;
