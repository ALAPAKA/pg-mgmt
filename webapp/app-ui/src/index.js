import React from "react";
import ReactDOM from "react-dom";
import App from "./App";
import 'bootstrap/dist/css/bootstrap.css';
import 'bootstrap/dist/css/bootstrap-theme.css';
import "./index.css";

import {BrowserRouter as Router} from "react-router-dom";

import * as firebase from 'firebase';

// Initialize Firebase
var config = {
    apiKey: "AIzaSyBeVaiWFva5a8hfBOvy9KhrGtK7amIsIS4",
    authDomain: "pg-mgmt.firebaseapp.com",
    databaseURL: "https://pg-mgmt.firebaseio.com",
    projectId: "pg-mgmt",
    storageBucket: "pg-mgmt.appspot.com",
    messagingSenderId: "819153797937"
};
firebase.initializeApp(config);

ReactDOM.render(
    <div>
        <Router>
            <App />
        </Router>
    </div>,
    document.getElementById('root')
);
