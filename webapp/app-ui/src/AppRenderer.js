import React from 'react';
import ReactDOM from "react-dom";

// import './App.css';
import App from "./App";
import {BrowserRouter as Router} from 'react-router-dom'

let renderReactApp = () => {
    ReactDOM.render(
        <div>
            <Router>
                <App />
            </Router>
        </div>,
        document.getElementById('root')
    );
}
export default renderReactApp;
