import React, {Component} from 'react';

import * as firebase from 'firebase';
import {MenuItem} from 'react-bootstrap';
import FirebaseAuthUI from './FirebaseAuthUI';

import renderReactApp from "./../AppRenderer";

import 'whatwg-fetch';

class FirebaseLogin extends Component {

    constructor(props) {
        super(props);
        this.state = {"loggedIn": false};
    }

    componentDidMount() {
        firebase.auth().onAuthStateChanged(firebaseUser => {
                if (firebaseUser) {
                    this.setState({"loggedIn": true});
                    console.log('AuthState Changed - Signed In');
                    this.onAuthStateChanged(firebaseUser);
                } else {
                    this.setState({"loggedIn": false});
                    console.log('AuthState Changed - Signed Out');
                }
            }
        );
        this.setState({"loggedIn": false});
    }

    signOut() {
        firebase.auth().signOut();
        console.log('Signing Out');
    }

    signIn() {
        firebase.auth().signInAnonymously().catch(function (error) {
            //https://firebase.google.com/docs/auth/web/anonymous-auth#authenticate-with-firebase-anonymously
            // Handle Errors here.
            // var errorCode = error.code;
            // var errorMessage = error.message;
        });
    }

    onSignIn() {
        renderReactApp();
    }

    onAuthStateChanged(firebaseUser) {
        if (firebaseUser) {
            firebase.auth().currentUser.getToken(/* forceRefresh */ true).then(function (idToken) {
                // Send token to your backend via HTTPS
                // User is signed in.
                var isAnonymous = firebaseUser.isAnonymous;
                var uid = firebaseUser.uid;
                // ...
                fetch('/authcheck.jsp', {
                    method: 'GET',
                    credentials: 'same-origin',
                    headers: {
                        'Content-Type': 'text/html',
                        'X-firebase-Authorization': idToken,
                        'X-firebase-isAnonymous': isAnonymous,
                        'X-firebase-uid': uid,
                    }
                }).then(function(response) {
                    return response.text();
                }).then(function(text) {
                    console.log('parsed text: ', text);
                }).catch(function(ex) {
                    console.log('parsing failed', ex);
                });
            }).catch(function (error) {
                console.log('Getting id token failed', error);
            });

        }


    }

    render() {
        let loginStatusButton = null;
        if (this.state.loggedIn) {
            // loginStatusButton = (<div onClick={this.signOut.bind(this)}>Sign Out</div>);
            loginStatusButton = <MenuItem eventKey={3.3} onClick={this.signOut.bind(this)}>Sign Out</MenuItem>;
        } else {
            loginStatusButton = <MenuItem><FirebaseAuthUI onSignIn="{this.onSignIn.bind(this)}"/></MenuItem>;
        }
        return loginStatusButton;
    }
}

export default FirebaseLogin;
