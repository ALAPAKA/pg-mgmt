/**
 * Created by Siva on 4/8/2017.
 */
import React, { Component } from 'react';
import { Glyphicon, Jumbotron  } from 'react-bootstrap';
import logo from './../logo.svg';

class BodyContainer extends Component {
    render() {
        return (
            <div>
                <Jumbotron>
                    <h1><Glyphicon glyph="home" /> Coming Soon</h1>
                    <p>Paying Guest Administration made easy....!!!</p>
                </Jumbotron>
                <div>
                    <img src={logo} className="App-logo" alt="logo" />
                </div>
                <br/><br/>
                <h2>Main Content Area</h2>
                <br/><br/><br/><br/><br/><br/>
            </div>
        );
    }
}

export default BodyContainer;