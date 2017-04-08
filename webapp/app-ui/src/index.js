import React from "react";
import ReactDOM from "react-dom";
import App from "./App";
import 'bootstrap/dist/css/bootstrap.css';
import 'bootstrap/dist/css/bootstrap-theme.css';
import "./index.css";

import {BrowserRouter as Router} from "react-router-dom";

ReactDOM.render(
    <div>
        <Router>
            <App />
        </Router>
    </div>,
    document.getElementById('root')
);
