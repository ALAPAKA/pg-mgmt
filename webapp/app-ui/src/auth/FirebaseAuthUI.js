import React, {Component} from 'react';

import * as firebase from 'firebase';
import * as firebaseui from 'firebaseui';

import 'whatwg-fetch';
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
var authUi = new firebaseui.auth.AuthUI(firebase.auth());

class FirebaseAuthUI extends Component {

    // constructor(props) {
    //     super(props);
    //     // this.state = {"loggedIn": false};
    // }

    componentDidMount() {
        var uiConfig = {
            'callbacks': {
                'signInSuccess': function(user) {
                    if (this.props.onSignIn) {
                        this.props.onSignIn(user);
                    }
                    return false;
                }
            },
            'signInOptions': [
                firebase.auth.GoogleAuthProvider.PROVIDER_ID,
                // firebase.auth.EmailAuthProvider.PROVIDER_ID
            ],
            'signInFlow': 'popup',
        };
        authUi.start('#firebaseui-auth', uiConfig);
    }

    componentWillUnmount() {
        authUi.reset();
    }

    render() {
        return (
            <span id="firebaseui-auth"></span>
        );
    }
}

export default FirebaseAuthUI;
